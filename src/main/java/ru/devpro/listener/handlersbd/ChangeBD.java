package ru.devpro.listener.handlersbd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.devpro.dto.ShelterDTO;
import ru.devpro.dto.ShelterLocationDTO;
import ru.devpro.enums.BotCommand;
import ru.devpro.enums.State;
import ru.devpro.listener.Command;
import ru.devpro.service.ShelterLocationService;
import ru.devpro.service.ShelterService;

import java.time.LocalDateTime;
import java.util.*;



@Component
public class ChangeBD implements Command {
    private static TelegramBot bot;

    private static final Map<Long, State> stateMap = new HashMap<>();
    private static final Map<Long, ShelterLocationDTO> userPendingShelter = new HashMap<>();
    private static final Map<Long, ShelterDTO> shelterPendingShelter = new HashMap<>();
    private static ShelterLocationService shelterLocationService;
    private static ShelterService shelterService;

    public ChangeBD(TelegramBot bot, ShelterLocationService shelterLocationService,ShelterService shelterService) {
        ChangeBD.bot = bot;
        ChangeBD.shelterLocationService = shelterLocationService;
        ChangeBD.shelterService=shelterService;
    }

  /*  public boolean ifSuitable(Update update) {
        if (update != null && update.message() != null) {
            String text = update.message().text();
            return Arrays.stream(State.values())
                    .anyMatch(state -> state.name().equalsIgnoreCase(text));

        }
        return false;
    }*/
    public boolean ifSuitable(Update update) {
        return update != null && update.message() != null;
    }

    public void handle(Update update) throws TelegramApiException {
        System.out.println("Handling update...");
        // Обработка text-запросов
        if (update.message() != null) {
            var chatId = update.message().chat().id();
            var text = update.message().text();
            System.out.println("Received text: " + text);

            State state = Arrays.stream(State.values())
                    .filter(s -> s.name().equalsIgnoreCase(text))
                    .findFirst()
                    .orElse(null);
            System.out.println("Received text: " + state);
            if (stateMap.containsKey(chatId)) {
                handleAddShelterLocationInput(chatId, text);
                handleAddShelterInput(chatId,text);
            }
        }}

    public static void createShelterLocation(Long chatId) {
        // Создаем новый объект ShelterLocationDTO
        ShelterLocationDTO shelterLocation = new ShelterLocationDTO();
        // Устанавливаем текущее время в поле dateTime
        shelterLocation.setDateTime(LocalDateTime.now());
        // Устанавливаем состояние ожидания адреса
        stateMap.put(chatId, State.AWAITING_ADDRESS);
        // Сохраняем созданный объект в userPendingShelter
        userPendingShelter.put(chatId, shelterLocation);
        // Выводим сообщение и запрашиваем у пользователя адрес приюта
        System.out.println("State set to AWAITING_ADDRESS for chatId: " + chatId);
        sendResponseMessage(chatId, "Пожалуйста, введите адрес приюта:");

    }

    private static void handleAddShelterLocationInput(Long chatId, String text) throws TelegramApiException {
        State currentState = stateMap.get(chatId);
        System.out.println("Current state for chatId " + chatId + ": " + currentState); // Вывод состояния в консоль);
        ShelterLocationDTO shelterLocation = userPendingShelter.get(chatId);

        switch (currentState) {

            case AWAITING_ADDRESS -> {
                System.out.println("Processing AWAITING_ADDRESS state"); // Отладочный вывод для состояния
                shelterLocation.setAddress(text);
                stateMap.put(chatId, State.AWAITING_CITY);
                System.out.println("State set to AWAITING_CITY for chatId: " + chatId);
                sendResponseMessage(chatId, "Пожалуйста, введите город приюта:");
            }
            case AWAITING_CITY -> {
                shelterLocation.setCity(text);
                stateMap.put(chatId, State.AWAITING_STATE);
                sendResponseMessage(chatId, "Пожалуйста, введите штат приюта:");
            }
            case AWAITING_STATE -> {
                shelterLocation.setState(text);
                stateMap.put(chatId, State.AWAITING_ZIPCODE);
                sendResponseMessage(chatId, "Пожалуйста, введите почтовый индекс приюта:");
            }
            case AWAITING_ZIPCODE -> {
                shelterLocation.setZipcode(text);
                // Все поля заполнены, добавляем данные в базу
                addShelterAddressToDatabase(chatId, shelterLocation);
                userPendingShelter.remove(chatId);
                stateMap.remove(chatId);
                sendResponseMessage(chatId, "Данные приюта успешно добавлены в базу.");
            }
        }

    }

    private static void addShelterAddressToDatabase(Long chatId, ShelterLocationDTO shelterLocation) throws TelegramApiException {
        shelterLocationService.createShelterLocation(shelterLocation);

    }
    public static void createShelter(Long chatId) {
        // Создаем новый объект ShelterDTO
        ShelterDTO shelter = new ShelterDTO();
        // Устанавливаем текущее время в поле dateTime
        shelter.setDateTime(LocalDateTime.now());
        // Устанавливаем состояние ожидания названия
        stateMap.put(chatId, State.AWAITING_NAME);
        // Сохраняем созданный объект в shelterPendingShelter
        shelterPendingShelter.put(chatId, shelter);
        // Выводим сообщение и запрашиваем у пользователя имя приюта
        System.out.println("State set to  for chatId: " + chatId);
        sendResponseMessage(chatId, "Пожалуйста, введите название приюта:");

    }

    private static void handleAddShelterInput(Long chatId, String text) throws TelegramApiException {
        State currentState = stateMap.get(chatId);
        System.out.println("Current state for chatId " + chatId + ": " + currentState); // Вывод состояния в консоль);
        ShelterDTO shelter = shelterPendingShelter.get(chatId);

        switch (currentState) {
            case AWAITING_NAME -> {
                System.out.println("Processing AWAITING_NAME state"); // Отладочный вывод для состояния
                shelter.setName(text);
                stateMap.put(chatId, State.AWAITING_SAFETY);
                sendResponseMessage(chatId, "Пожалуйста, введите рекомендации по безопасности:");
            }
            case AWAITING_SAFETY -> {
                    shelter.setSafety(text);
                // Все поля заполнены, добавляем данные в базу
                    addShelterToDatabase(chatId, shelter);
                    shelterPendingShelter.remove(chatId);
                    stateMap.remove(chatId);
                    sendResponseMessage(chatId, " Приют успешно добавлен в базу.");
                }
            }

        }


    private static void addShelterToDatabase(Long chatId, ShelterDTO shelter) throws TelegramApiException {
        shelterService.createShelter(shelter);

    }


    private static void sendResponseMessage(Long chatId, String message) {
        SendMessage responseMessage = new SendMessage(chatId, message);
        bot.execute(responseMessage);
    }


}

