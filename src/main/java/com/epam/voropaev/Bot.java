package com.epam.voropaev;

import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

public class Bot extends TelegramLongPollingBot {

	private void sendResponceMessage(Update update, String messageText){
		SendMessage request = new SendMessage();
		request.setChatId(update.getMessage().getChatId())
				.setText(messageText);
		try {
			execute(request);
		}
		catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
    public void onUpdateReceived(Update update) {
        SendMessage request = new SendMessage();
        if(update.hasMessage() && update.getMessage().hasText()) {
            System.out.println("Message recieved : \"" + update.getMessage().getText() + "\"");
            String updateMessageText = update.getMessage().getText();
            switch (updateMessageText){
                case "/build":
					JobWithDetails jenkinsJob = null;
					try {
						 jenkinsJob = (JobWithDetails) JenkinsAPIController.getDefaultJob();
						 jenkinsJob.build(true);
						 if(jenkinsJob.isInQueue()){
							 sendResponceMessage(update,"Waiting...(in Queue)");
						 }
						while (jenkinsJob.isInQueue()) {
							Thread.sleep(100);
						}
						sendResponceMessage(update,"Building project: " + jenkinsJob.getDisplayName() + "...");
						Build buildByNumber = jenkinsJob.getLastBuild();
						while (buildByNumber.details().isBuilding()) {
							Thread.sleep(100);
						}
						sendResponceMessage(update,"Build № " + buildByNumber.getNumber() + " has finished.");
						BuildWithDetails details = buildByNumber.details();
						sendResponceMessage(update,"BuiltOn: " + details.getBuiltOn());
						sendResponceMessage(update,"Duration: " + details.getDuration());
						sendResponceMessage(update,"Result: " + details.getResult());
						sendResponceMessage(update,"For more details go to: " + details.getUrl());
					}
					catch (URISyntaxException e) {
						e.printStackTrace();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
                    break;
                case "/help":
                	sendResponceMessage(update,"Hello. Avaliable commands: \n /build");
                    break;
                default:
					sendResponceMessage(update,"Invalid  request. try  again or  see /help");
                    break;
            }
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
