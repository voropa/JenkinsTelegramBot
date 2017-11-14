package com.epam.voropaev;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage request = new SendMessage();
        if(update.hasMessage() && update.getMessage().hasText()) {
            System.out.println("Message recieved : \"" + update.getMessage().getText() + "\"");
            String updateMessageText = update.getMessage().getText();
            switch (updateMessageText){
                case "/build":
                    request.setChatId(update.getMessage().getChatId())
                            .setText("Building project...");
                    break;
                case "/help":
                    request.setChatId(update.getMessage().getChatId())
                            .setText("Hello. Avaliable commands: \n /build");
                    break;
                default:
                    request.setChatId(update.getMessage().getChatId())
                            .setText("Invalid  request. try  again or  see /help");
                    break;
            }
        }
        try {
            execute(request);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return "456046724:AAFMBmoKQnKKpa77I6tb3w6lhwqNcJXbT-M";
    }

}
