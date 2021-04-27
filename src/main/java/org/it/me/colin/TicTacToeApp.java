package org.it.me.colin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.log4j.Log4j2;
import org.it.me.colin.engine.GameInstance;
import org.it.me.colin.module.TicTacToeModule;

@Log4j2
public class TicTacToeApp {
    public static void main( String[] args ) {
        log.info("Starting app.");
        Injector injector = Guice.createInjector(new TicTacToeModule());
        GameInstance game = injector.getInstance(GameInstance.class);

        log.info("Running a game.");
        game.run();
    }
}
