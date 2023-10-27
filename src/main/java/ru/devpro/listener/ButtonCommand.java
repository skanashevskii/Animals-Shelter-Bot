package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;


import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
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

    public void handle(Update update) throws TelegramApiException {

        if (update.callbackQuery() != null) {
            var chatId = update.callbackQuery().message().chat().id();
            var callbackData = update.callbackQuery().data();

            System.out.println("Received callbackData: " + callbackData);
            // Находим команду из enum, используя callbackData
            BotCommand command = Arrays.stream(BotCommand.values())
                    .filter(cmd -> cmd.getCommand().equalsIgnoreCase(callbackData))
                    .findFirst()
                    .orElse(null);
            if (command != null) {
                switch (command) {
                    case START -> sendStartMessage(chatId);
                    case CALL_VOLUNTEER -> sendVolunteerMessage(chatId);
                    case SHELTERS -> sendSheltersMessage(chatId);
                    case CAT_SHELTER -> sendCatShelterMessage(chatId);
                    case DOG_SHELTER -> sendDogShelterMessage(chatId);
                    case SCHEDULE -> sendScheduleMessage(chatId);
                    case ABOUT_SHELTERS -> sendAboutScheduleMessage(chatId);
                    case SECURITY -> sendSecurityMessage(chatId);
                    case SAFETY -> sendSafetyMessage(chatId);
                    case CONTACT -> sendContactMessage(chatId);
                    default -> bot.execute(new SendMessage(chatId, "Извините, не могу обработать данную команду."));
                }
            }

            // Обработка callback-запросов
        } else if (update.message() != null) {
            var chatId = update.message().chat().id();
            var text = update.message().text();
            System.out.println("Received text: " + text);

            BotCommand botCommand = Arrays.stream(BotCommand.values())
                    .filter(command -> command.getCommand().equalsIgnoreCase(text))
                    .findFirst()
                    .orElse(null);
            System.out.println("Received text: " + botCommand);
            if (botCommand != null) {
                // Вызываем соответствующий метод на основе команды
                switch (botCommand) {
                    case START -> sendStartMessage(chatId);
                    case CALL_VOLUNTEER -> sendVolunteerMessage(chatId);
                    case SHELTERS -> sendSheltersMessage(chatId);
                    case CAT_SHELTER -> sendCatShelterMessage(chatId);
                    // Обработка нажатия кнопки "Приют для кошек"
                    case DOG_SHELTER -> sendDogShelterMessage(chatId);
                    case SCHEDULE -> sendScheduleMessage(chatId);
                    case ABOUT_SHELTERS -> sendAboutScheduleMessage(chatId);
                    case SECURITY -> sendSecurityMessage(chatId);
                    case SAFETY -> sendSafetyMessage(chatId);
                    case CONTACT -> sendContactMessage(chatId);

                    // Обработка нажатия кнопки "Приют для собак"
                    // Другие команды
                    default -> bot.execute(new SendMessage(chatId, "Извините, не могу обработать данную команду."));
                }
            }
        }
    }

    void sendSheltersMessage(Long chatId) {
        String downArrow = "👇";
        // If the user sent "/shelters," send a message with buttons
        InlineKeyboardMarkup sheltersKeyboard = new InlineKeyboardMarkup(new InlineKeyboardButton[][]{
                {new InlineKeyboardButton(BotCommand.CAT_SHELTER.getDescription()).callbackData(BotCommand.CAT_SHELTER.getCommand())},
                {new InlineKeyboardButton(BotCommand.DOG_SHELTER.getDescription()).callbackData(BotCommand.DOG_SHELTER.getCommand())}
        });
        SendMessage message = new SendMessage(chatId, "Выберите приют:" + "👇");
        message.replyMarkup(sheltersKeyboard);
        bot.execute(message);
    }


    private void sendContactMessage(Long chatId) {
    }

    private void sendSafetyMessage(Long chatId) {
    }

    private void sendSecurityMessage(Long chatId) {
    }

    private void sendVolunteerMessage(Long chatId) {
    }

    private void sendCatShelterMessage(Long chatId) {
    }

    private void sendDogShelterMessage(Long chatId) {
    }

    private void sendScheduleMessage(Long chatId) {

    }

    private void sendAboutScheduleMessage(Long chatId) {
    }

    private void sendStartMessage (Long chatId){
        String downArrow = "👇";
        // If the user sent "/start," send a message with buttons

        InlineKeyboardMarkup inlineKeyboard =
                new InlineKeyboardMarkup(BotCommand.SHELTER_INFO.getButton());
        inlineKeyboard.addRow(BotCommand.ADDRESS.getButton());
        inlineKeyboard.addRow(BotCommand.PASS.getButton());
        inlineKeyboard.addRow(BotCommand.SAFETY.getButton());
        inlineKeyboard.addRow(BotCommand.PHONE.getButton());
        inlineKeyboard.addRow(BotCommand.SHELTERS.getButton());

        SendMessage message = new SendMessage(chatId, "Выберите действие" + downArrow);
        message.replyMarkup(inlineKeyboard);
        bot.execute(message);
    }

}
  /*  public SendMessage getMainMenuMessage( long chatId,  String textMessage) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();

        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }*/


      /*  private SendMessage createMessageWithKeyboard(
                final long chatId, String textMessage,
                final ReplyKeyboardMarkup replyKeyboardMarkup) {

            final SendMessage sendMessage = new SendMessage(chatId,"КУ");
            //sendMessage.enableMarkdown(true);
            //sendMessage.setChatId(chatId);
            //sendMessage.setText(textMessage);
            if (replyKeyboardMarkup != null) {
                //sendMessage.setReplyMarkup(replyKeyboardMarkup);
            }
            return sendMessage;
        }*/


  /*  private ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Ghbdf"),
                new KeyboardButton("Re RE"),
                new KeyboardButton("Re RE")
        );
        //replyKeyboardMarkup.setSelective(true);
        //replyKeyboardMarkup.setResizeKeyboard(true);
        //replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(new KeyboardButton("Получить ").toString());
        row2.add(new KeyboardButton("Моя ").toString());
        row3.add(new KeyboardButton("Помощь").toString());
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        //replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }*/






