package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.Optional;

public class StartCommand implements Command{
    private final TelegramBot bot;

    public StartCommand(TelegramBot bot) {
        this.bot = bot;
    }

    public boolean ifSuitable(Update update) {
        return Optional.of(update)
                .map(Update::message)
                .map(Message::text)
                .map(text ->text.equals("/start"))
                .orElse(false);

    }


    public void handle(Update update) {
        var chatId = update.message().chat().id();

        bot.execute(new SendMessage(chatId,"Привет, я бот! Что изволите?"));
    }
}
