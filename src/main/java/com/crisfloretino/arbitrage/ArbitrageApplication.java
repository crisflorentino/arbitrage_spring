package com.crisfloretino.arbitrage;

import com.crisfloretino.arbitrage.client.SGOAPIClient;
import com.crisfloretino.arbitrage.client.SGOURLBuilder;
import com.crisfloretino.arbitrage.model.APIResponse;
import com.crisfloretino.arbitrage.model.Event;
import com.crisfloretino.arbitrage.util.Dates;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class ArbitrageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArbitrageApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(SGOAPIClient client){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                Map<String, Object> params = Map.of(
                        "leagueID", "NBA",
                        "oddID", "points-home-game-ml-home,points-away-game-ml-away",
                        "startsAfter", Dates.todayStart(),
                        "startsBefore", Dates.todayEnd()
                );

                String url = String.valueOf(SGOURLBuilder.buildURL(params));
                Optional<APIResponse> response = client.fetchOdds(url);

                if (response.isPresent()) {
                    System.out.println("Events found: " + response.get().data().size());
                } else {
                    System.out.println("No response received.");
                }
            }
        };
    }

}
