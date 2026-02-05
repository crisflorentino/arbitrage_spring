package com.crisfloretino.arbitrage.client;

import com.crisfloretino.arbitrage.model.APIResponse;
import com.crisfloretino.arbitrage.model.Event;
import com.crisfloretino.arbitrage.model.Leagues;
import com.crisfloretino.arbitrage.service.ArbitrageService;
import com.crisfloretino.arbitrage.util.Dates;
import org.springframework.beans.factory.annotation.Value; // Import for injecting config
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final ArbitrageService service;
    private final SGOAPIClient client;

    // 1. Inject API Key from application.properties so you don't have to type it!
    @Value("${odds.api.key:}") // Default to empty string if missing
    private String configuredApiKey;

    public ConsoleRunner(ArbitrageService service, SGOAPIClient client) {
        this.service = service;
        this.client = client;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        String apiKey = getApiKey(scanner); // Ask once (or use config)

        boolean running = true;

        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                running = false;
                System.out.println("Goodbye!");
                continue;
            }

            try {
                // 2. Parse ID safely
                int leagueId = Integer.parseInt(input);
                Leagues league = Leagues.fromId(leagueId);

                // 3. Run the search logic
                processLeagueSearch(league, apiKey, scanner);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'exit'.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.exit(0);
    }

    // --- Helper Methods to keep 'run' clean ---

    private String getApiKey(Scanner scanner) {
        // If we set it in application.properties, skip asking!
        if (configuredApiKey != null && !configuredApiKey.isBlank()) {
            return configuredApiKey;
        }
        System.out.println("Please enter your API Key:");
        return scanner.nextLine().trim();
    }

    private void printMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("Select a league (Enter ID):");
        for (Leagues l : Leagues.values()) {
            // Assuming Leagues has a getter for ID, or we use ordinal + 1
            System.out.println(l.getId() + ". " + l.getName());
        }
        System.out.println("Type 'exit' to quit.");
        System.out.print("> ");
    }

    private void processLeagueSearch(Leagues league, String apiKey, Scanner scanner) {
        System.out.println("Fetching " + league.getName() + " data...");

        Map<String, Object> params = Map.of(
                "leagueID", league.getName(),
                "oddID", "points-home-game-ml-home,points-away-game-ml-away",
                "startsAfter", Dates.todayStart(),
                "startsBefore", Dates.todayEnd()
        );

        String url = SGOURLBuilder.buildURL(params).toString();
        Optional<APIResponse> response = client.fetchOdds(url, apiKey);

        if (response.isEmpty()) {
            System.err.println("Error: Could not fetch data. Check your API key.");
            return;
        }

        List<Event> events = client.collectEvents(response.get());
        if (events.isEmpty()) {
            System.out.println("No games found for today.");
            return;
        }

        selectAndScanGame(events, scanner);
    }

    private void selectAndScanGame(List<Event> events, Scanner scanner) {
        System.out.println("\n--- GAMES FOUND ---");
        System.out.println("0. Scan ALL Games");

        int index = 1;
        for (Event event : events) {
            System.out.printf("%d. %s @ %s | %s%n",
                    index++,
                    event.getAwayTeam().getMedium(),
                    event.getHomeTeam().getMedium(),
                    Dates.format(event.status().startsAt())
            );
        }

        System.out.print("Select a game ID: ");
        try {
            int selection = Integer.parseInt(scanner.nextLine().trim());

            if (selection == 0) {
                events.forEach(service::findArbitrage);
            } else if (selection > 0 && selection <= events.size()) {
                service.findArbitrage(events.get(selection - 1));
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}