package com.epam.voropaev;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi tBotsApi = new TelegramBotsApi();
        Bot bot = new Bot();
        try {
            tBotsApi.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
