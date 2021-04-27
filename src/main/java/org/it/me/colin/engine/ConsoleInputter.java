package org.it.me.colin.engine;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.it.me.colin.util.GameUtils;

import java.util.Scanner;

@Log4j2
public class ConsoleInputter implements GameInputter {
    private final Scanner inputScanner = new Scanner(System.in);

    public Pair<Integer, Integer> getPlayerMove() {
        log.debug("Getting player's next move.");
        String input = inputScanner.nextLine();
        return GameUtils.parseCoordinates(input);
    }

    public String getGameId() {
        log.debug("Getting game id.");
        return inputScanner.nextLine();
    }
}
