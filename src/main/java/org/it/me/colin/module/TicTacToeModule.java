package org.it.me.colin.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import org.it.me.colin.client.TicTacToeClient;
import org.it.me.colin.client.TicTacToeHttpClient;
import org.it.me.colin.engine.ConsoleInputter;
import org.it.me.colin.engine.ConsoleRenderer;
import org.it.me.colin.engine.GameInputter;
import org.it.me.colin.engine.GameRenderer;

import java.net.http.HttpClient;

public class TicTacToeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HttpClient.class).toProvider(HttpClientProvider.class);
        bind(TicTacToeClient.class).to(TicTacToeHttpClient.class);
        bind(GameRenderer.class).to(ConsoleRenderer.class);
        bind(GameInputter.class).to(ConsoleInputter.class);
    }

    private static class HttpClientProvider implements Provider<HttpClient> {
        @Override
        public HttpClient get() {
            return HttpClient.newHttpClient();
        }
    }
}
