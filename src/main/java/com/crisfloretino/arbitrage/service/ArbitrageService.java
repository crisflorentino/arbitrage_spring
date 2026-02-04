package com.crisfloretino.arbitrage.service;



import com.crisfloretino.arbitrage.model.BookmakerOdd;
import com.crisfloretino.arbitrage.model.Event;
import com.crisfloretino.arbitrage.model.Odd;
import com.crisfloretino.arbitrage.util.OddsConverter;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ArbitrageService {

    public ArbitrageService() {}

    public void findArbitrage(Event event) {
        Odd homeOdds = event.getHomeOdds();
        Odd awayOdds = event.getAwayOdds();

        BestOdd bestHome = findBestOdd(homeOdds);
        BestOdd bestAway = findBestOdd(awayOdds);

        double arbPercent = (1.0 / bestHome.decimalOdds()) + (1.0 / bestAway.decimalOdds());

        if (arbPercent < 1.0) {
            double profitPercent = (1.0 - arbPercent) * 100;

            printArbitrage(event, bestHome, bestAway, profitPercent);
        }
    }

    private BestOdd findBestOdd(Odd odds) {
        BestOdd best = new BestOdd("None", 0.0);

        for (Map.Entry<String, BookmakerOdd> entry : odds.byBookmaker().entrySet()) {
            double decimal = OddsConverter.toDecimal(entry.getValue().odds());
            if (decimal > best.decimalOdds()) {
                best = new BestOdd(entry.getKey(), decimal);
            }
        }

        return best;
    }

    private void printArbitrage(Event event, BestOdd home, BestOdd away, double profitPercent) {
        System.out.println("--------------------------------------------------");
        System.out.println("🚀 ARBITRAGE OPPORTUNITY FOUND!");
        System.out.println(event.getAwayTeam().getMedium() + " @ " + event.getHomeTeam().getMedium());
        System.out.println("Bet 1: " + event.getHomeTeam().getMedium() + " @ " +
                home.bookieName() + " (" + home.decimalOdds() + ")");
        System.out.println("Bet 2: " + event.getAwayTeam().getMedium() + " @ " +
                away.bookieName() + " (" + away.decimalOdds() + ")");
        System.out.printf("Guaranteed Profit: %.2f%%\n", profitPercent);
        System.out.println("--------------------------------------------------");
    }
}

record BestOdd(String bookieName, double decimalOdds) { }