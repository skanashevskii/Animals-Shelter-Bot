package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import ru.devpro.enums.BotCommand;

import java.util.*;

@Component
public class ButtonCommand implements Command {
    private final TelegramBot bot;

    public ButtonCommand(TelegramBot bot) {
        this.bot = bot;
    }

    public boolean ifSuitable(Update update) {
        if (update != null && update.message() != null) {
            String text = update.message().text();
            return Arrays.stream(BotCommand.values())
                    .anyMatch(botCommand -> botCommand.getCommand().equalsIgnoreCase(text));
        }
        return false;
    }

    public void handle(Update update) {
        if (update.message() != null) {
            var chatId = update.message().chat().id();
            String text = update.message().text();

            BotCommand command = Arrays.stream(BotCommand.values())
                    .filter(cmd -> cmd.getCommand().equalsIgnoreCase(text))
                    .findFirst()
                    .orElse(null);

            if (command != null) {
                String description = command.getDescription();
                bot.execute(new SendMessage(chatId, description));
            } else {
                bot.execute(new SendMessage(chatId, "Команда не найдена"));
            }
        }

        if (update.callbackQuery() != null) {
            var chatId = update.callbackQuery().message().chat().id();
            var callbackData = update.callbackQuery().data();

            if (callbackData.equalsIgnoreCase("/start")) {
                sendStartMessage(chatId);
            } else if (callbackData.equalsIgnoreCase("/shelters")) {
                sendSheltersMessage(chatId);
            } else if (callbackData.equalsIgnoreCase("/cat_shelter")) {
                // Handle the "Приют для кошек" button click
            } else if (callbackData.equalsIgnoreCase("/dog_shelter")) {
                // Handle the "Приют для собак" button click
            } else {
                bot.execute(new SendMessage(chatId, "Извините, не могу обработать данную команду."));
            }
        }
    }

    private void sendStartMessage(Long chatId) {
        String downArrow = "👇";
        // If the user sent "/start," send a message with buttons
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(new InlineKeyboardButton[][]{
                {new InlineKeyboardButton("О приюте").callbackData("about")},
                {new InlineKeyboardButton("Расписание и адрес").callbackData("schedule")},
                {new InlineKeyboardButton("Контакты охраны").callbackData("security")},
                {new InlineKeyboardButton("Рекомендации по безопасности").callbackData("safety")},
                {new InlineKeyboardButton("Оставить контактные данные").callbackData("contact")},
                {new InlineKeyboardButton("Позвать волонтера").callbackData("volunteer")},
                {new InlineKeyboardButton("Приюты").callbackData("/shelters")}
        });

        SendMessage message = new SendMessage(chatId, "Выберите действие" + downArrow);
        message.replyMarkup(keyboard);
        bot.execute(message);
    }

    private void sendSheltersMessage(Long chatId) {
        String downArrow = "👇";
        // If the user sent "/shelters," send a message with buttons
        InlineKeyboardMarkup sheltersKeyboard = new InlineKeyboardMarkup(new InlineKeyboardButton[][]{
                {new InlineKeyboardButton("Приют для кошек").callbackData("/cat_shelter")},
                {new InlineKeyboardButton("Приют для собак").callbackData("/dog_shelter")}
        });
        SendMessage message = new SendMessage(chatId, "Выберите приют:" + "👇");
        message.replyMarkup(sheltersKeyboard);
        bot.execute(message);
    }
}



