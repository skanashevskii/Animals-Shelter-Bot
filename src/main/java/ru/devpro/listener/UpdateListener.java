package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.enums.BotCommand;

import java.util.Arrays;
import java.util.List;

@Service
public class UpdateListener implements UpdatesListener {
    private static final Logger logger = LoggerFactory.getLogger(UpdateListener.class);
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
            logger.debug("Handle update: {}", update);
            commands.stream()
                    .filter(command -> command.ifSuitable(update))
                    .forEach(command -> command.handle(update));
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}


