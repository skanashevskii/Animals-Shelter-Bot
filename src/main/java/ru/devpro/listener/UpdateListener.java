package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@Log4j2
public class UpdateListener implements UpdatesListener {

    private final TelegramBot bot;
    private final List<Command> commands;

    public UpdateListener(TelegramBot bot, List<Command> commands) {
        this.bot = bot;
        this.commands = commands;
    }

    @PostConstruct
    void init() {
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            log.debug("Handle update: {}", update);
            commands.stream()
                    .filter(command -> command.ifSuitable(update))
                    .forEach(command -> {
                        try {
                            command.handle(update);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    });
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}



