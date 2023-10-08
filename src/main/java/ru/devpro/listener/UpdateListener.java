package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(UpdateListener.class);
    private final TelegramBot bot;
    private final List<StartCommand> commands;

    public UpdateListener(TelegramBot bot, List<StartCommand> commands) {
        this.bot = bot;
        this.commands = commands;
    }

    @PostConstruct
    void init() {
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            logger.debug("Handle update: {}", update);
            commands.stream()
                    .filter(command -> command.ifSuitable(update))
                    .forEach(command -> command.handle(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}