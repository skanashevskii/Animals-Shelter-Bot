package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;

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

        if (text.equals("/start")) {
            String downArrow = "👇";  // Символ смайлика
            // Если пользователь отправил /start, отправьте сообщение с кнопками
            InlineKeyboardButton button1 = new InlineKeyboardButton("Приют для кошек")
                    .callbackData("Приют для кошек");
            InlineKeyboardButton button2 = new InlineKeyboardButton("Приют для собак")
                    .callbackData("Приют для собак");
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(button1, button2);
            SendMessage message = new SendMessage(chatId, "Выберите команду" + downArrow);
            message.replyMarkup(markupInline);
            bot.execute(message);
        }else if (update.callbackQuery() != null) {
            // Пользователь нажал на кнопку, обработайте это событие
            String callbackData = update.callbackQuery().data();
            if (callbackData.equals("Приют для кошек")) {
                // Пользователь выбрал приют для кошек, обработайте это событие
                sendCatShelterOptions(chatId);
            } else if (callbackData.equals("Приют для собак")) {
                // Пользователь выбрал приют для собак, обработайте это событие
                sendDogShelterOptions(chatId);
            }
        }
    }

        private void sendCatShelterOptions(Long chatId) {
            InlineKeyboardButton button3 = new InlineKeyboardButton("Опция 1")
                    .callbackData("option1");
            InlineKeyboardButton button4 = new InlineKeyboardButton("Опция 2")
                    .callbackData("option2");

            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(button3, button4);

            SendMessage message = new SendMessage(chatId, "Выберите опцию:");
            message.replyMarkup(markupInline);

            bot.execute(message);
        }
    private void sendDogShelterOptions(Long chatId) {
        // Создайте кнопки для опций приюта для собак и отправьте их
    }







    }



