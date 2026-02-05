package com.crisfloretino.arbitrage.client;

import com.crisfloretino.arbitrage.model.APIResponse;
import com.crisfloretino.arbitrage.model.Event;
import com.crisfloretino.arbitrage.model.Leagues;
import com.crisfloretino.arbitrage.service.ArbitrageService;
import com.crisfloretino.arbitrage.util.Dates;
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

    public ConsoleRunner(ArbitrageService service, SGOAPIClient client) {
        this.service = service;
        this.client = client;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Please select a league from 1 - " + Leagues.values().length + " (NBA,NFL,NHL,MLB)");
            int idInput = scanner.nextInt();

            try {
                Leagues league = Leagues.fromId(idInput);
                System.out.println("League selected: " + league.getName());

                Map<String, Object> params = Map.of(
                        "leagueID", league.getName(),
                        "oddID", "points-home-game-ml-home,points-away-game-ml-away",
                        "startsAfter", Dates.todayStart(),
                        "startsBefore", Dates.todayEnd()
                );

                String url = String.valueOf(SGOURLBuilder.buildURL(params));
                Optional<APIResponse> response = client.fetchOdds(url);

                if (response.isPresent()) {
                    System.out.println("Events found: " + response.get().data().size());

                    List<Event> events = client.collectEvents(response.get());

                    System.out.println("Please select a game you would like to find arbitrage opportunities from, if you want to scan all, type 0.");

                    int index = 1;
                    System.out.println("0. Search all games");
                    for (Event event : events) {
                        System.out.println(index + ". " + event.getAwayTeam().getMedium() + " @ " + event.getHomeTeam().getMedium() + " | " + Dates.format(event.status().startsAt()));
                        index++;
                    }

                    int selectedEvent = scanner.nextInt();

                    if (selectedEvent == 0) {
                        for (Event event : events) {
                            service.findArbitrage(event);
                        }
                        running = false;
                    } else {
                      try {
                          Event event = events.get(selectedEvent - 1);
                          service.findArbitrage(event);
                          running = false;
                      } catch (IndexOutOfBoundsException e) {
                          System.out.println("Invalid event number inputted.");
                          running = false;
                      }
                    }
                } else {
                    System.err.println("There seems to have been a problem on fetching a response from the API. Please check if your key is correct.");
                    running = false;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }
}
