package ru.devpro.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.devpro.enums.BotCommand;

import java.util.Arrays;


public class BottonComand {

        private final TelegramBot bot;

    public BottonComand(TelegramBot bot) {
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
            String DOG = "/dog";
            String CAT = "/cat";
            InlineKeyboardButton dogButton = new InlineKeyboardButton("Приют собак").callbackData(DOG);
            InlineKeyboardButton catButton = new InlineKeyboardButton("Приют кошек").callbackData(CAT);
            InlineKeyboardButton backButton = new InlineKeyboardButton("Назад").callbackData("back");
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup(dogButton, catButton, backButton);

            if (update.message() != null) {
                long chatId = update.message().chat().id();
                SendMessage sendMessage = new SendMessage(chatId,
                        "Добро пожаловать в приложение")
                        .replyMarkup(markup);
                bot.execute(sendMessage);
            }

            if (update.callbackQuery() != null) {
                var chatId = update.callbackQuery().message().chat().id();
                var callbackData = update.callbackQuery().data();

                String INFO = "/infoDog";
                String TAKE = "/takeDog";
                String REPORT = "/reportDog";
                String INFOCAT = "/infoCat";
                String TAKECAT = "/takeCat";
                String REPORTCAT = "/reportCat";
                String VOLUNTEER = "/volunteer";
                if (callbackData.equals(DOG)) {
                    InlineKeyboardButton infoButton = new InlineKeyboardButton("Инфо").callbackData(INFO);

                    InlineKeyboardButton takeButton = new InlineKeyboardButton("Взять").callbackData(TAKE);

                    InlineKeyboardButton reportButton = new InlineKeyboardButton("Отчет").callbackData(REPORT);

                    InlineKeyboardButton volunteerButton = new InlineKeyboardButton("Позвать волонтера").callbackData(VOLUNTEER);

                    var markup1 = new InlineKeyboardMarkup(infoButton, takeButton, reportButton, volunteerButton);
                    bot.execute(new SendMessage(chatId, "Выберите действие ").replyMarkup(markup1));
                    return;
                } else if (callbackData.equals(CAT)) {
                    InlineKeyboardButton infoButton1 = new InlineKeyboardButton("Инфо").callbackData(INFOCAT);

                    InlineKeyboardButton takeButton1 = new InlineKeyboardButton("Взять").callbackData(TAKECAT);

                    InlineKeyboardButton reportButton1 = new InlineKeyboardButton("Отчет").callbackData(REPORTCAT);

                    InlineKeyboardButton volunteerButton1 = new InlineKeyboardButton("Позвать волонтера").callbackData(VOLUNTEER);

                    var markup1 = new InlineKeyboardMarkup(infoButton1, takeButton1, reportButton1, volunteerButton1);
                    bot.execute(new SendMessage(chatId, "Выберите действие ").replyMarkup(markup1));
                    return;
                } else if (callbackData.equals("back")) {
                    bot.execute(new SendMessage(chatId, "Когда нибудь он вернется"));
                checkAndAddUser(update);

                }

                if (callbackData.equals(INFO)) {
                    bot.execute(new SendMessage(chatId, "Мы приют в котором очень любят собак мы готовы помочь вам с их выбором," +
                            "или просто проконсультировать какая порода вам больше подойдет "));

                } else if (callbackData.equals(TAKE)) {
                    bot.execute(new SendMessage(chatId, "Что нужно чтоб взять собаку из приюта: "));

                } else if (callbackData.equals(REPORT)) {
                    bot.execute(new SendMessage(chatId, "Писать отчет нужно в описании к фотографии одним сообщением. " +
                            "Пример отчета: "));

                } else if (callbackData.equals(VOLUNTEER)) {
                    bot.execute(new SendMessage(chatId, "Вы можете связаться с волонтером по этому номеру +382 688 ***"));

                } else if (callbackData.equals(INFOCAT)) {
                    bot.execute(new SendMessage(chatId, "Мы приют в котором очень любят кошек мы готовы помочь вам с их выбором," +
                            "или просто проконсультировать какая порода вам больше подойдет "));

                } else if (callbackData.equals(TAKECAT)) {
                    bot.execute(new SendMessage(chatId, "Что нужно чтоб взять кошку из приюта: "));

                } else if (callbackData.equals(REPORTCAT)) {
                    bot.execute(new SendMessage(chatId, "Писать отчет нужно в описании к фотографии одним сообщением. " +
                            "Пример отчета: "));

                } else bot.execute(new SendMessage(chatId, "Извините я не могу вам помочь, но вы можете связаться" +
                        " с волонтером по этому номеру +382 688 ***"));
            }


        }

        public void checkAndAddUser(Update update) { //Идея такова что при первом нажатии это меню всплывет, а при втором нажатии на старт будет другое меню уже
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(new InlineKeyboardButton[][]{
                    {new InlineKeyboardButton("О приюте").callbackData("about")},
                    {new InlineKeyboardButton("Расписание и адрес").callbackData("schedule")},
                    {new InlineKeyboardButton("Контакты охраны").callbackData("security")},
                    {new InlineKeyboardButton("Рекомендации по безопасности").callbackData("safety")},
                    {new InlineKeyboardButton("Оставить контактные данные").callbackData("contact")},
                    {new InlineKeyboardButton("Позвать волонтера").callbackData("volunteer")},
                    {new InlineKeyboardButton("Дальше").callbackData("next")}
            });

            if (update.message() != null) {
                long chatId = update.message().chat().id();
                SendMessage sendMessage = new SendMessage(chatId,
                        "Выберите действие")
                        .replyMarkup(keyboard);
                bot.execute(sendMessage);
            }

            if (update.callbackQuery() != null) {
                var chatId = update.callbackQuery().message().chat().id();
                var callbackData = update.callbackQuery().data();

                if (callbackData.equals("about")) {
                    bot.execute(new SendMessage(chatId, "О приюте"));
                    return;
                } else if (callbackData.equals("schedule")) {
                    bot.execute(new SendMessage(chatId, "Расписание и адрес"));
                    return;
                }

                if (callbackData.equals("security")) {
                    bot.execute(new SendMessage(chatId, "Контакты охраны"));

                } else if (callbackData.equals("safety")) {
                    bot.execute(new SendMessage(chatId, "Рекомендации по безопасности"));

                } else if (callbackData.equals("contact")) {
                    bot.execute(new SendMessage(chatId, "Оставить контактные данные"));

                } else if (callbackData.equals("volunteer")) {
                    bot.execute(new SendMessage(chatId, "Вы можете связаться с волонтером по этому номеру +382 688 ***"));

                } else if (callbackData.equals("next")) {
                    bot.execute(new SendMessage(chatId, "Чтоб узнать больше информации нажмите тут /start"));
                handle(update);

                } else bot.execute(new SendMessage(chatId, "Извините я не могу вам помочь, но вы можете связаться" +
                        " с волонтером по этому номеру +382 688 ***"));
            }
        }
    }

