package ru.devpro.listener.handlersbd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.devpro.dto.AnimalDTO;
import ru.devpro.dto.ShelterDTO;
import ru.devpro.dto.ShelterLocationDTO;
import ru.devpro.dto.UserDTO;
import ru.devpro.enums.AccessLevel;
import ru.devpro.enums.AnimalType;
import ru.devpro.enums.State;
import ru.devpro.listener.Command;
import ru.devpro.service.AnimalService;
import ru.devpro.service.ShelterLocationService;
import ru.devpro.service.ShelterService;
import ru.devpro.service.UserService;

import java.time.LocalDateTime;
import java.util.*;


@Component
public class ChangeBD implements Command {
    private final TelegramBot bot;

    private final Map<Long, State> stateMap = new HashMap<>();
    private final Map<Long, ShelterLocationDTO> shelterLocationPending = new HashMap<>();
    private final Map<Long, ShelterDTO> shelterPending = new HashMap<>();
    private final Map<Long, UserDTO> userPending = new HashMap<>();
    private final Map<Long, AnimalDTO> amimalPending = new HashMap<>();
    private final ShelterLocationService shelterLocationService;
    private final ShelterService shelterService;
    private final UserService userService;
    private final AnimalService animalService;

    public ChangeBD(TelegramBot bot, ShelterLocationService shelterLocationService, ShelterService shelterService, UserService userService, AnimalService animalService) {
        this.bot = bot;
        this.shelterLocationService = shelterLocationService;
        this.shelterService = shelterService;
        this.userService = userService;
        this.animalService = animalService;
    }


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
                //handleAddShelterLocationInput(chatId, text);
                handleAddShelterInput(chatId, text);
                handleAddUserInput(chatId, text);
                handleAddAnimalInput(chatId,text);
            }
        }
    }

    //Создание приюта
    public void createShelter(Long chatId) {
        // Создаем новый объект ShelterDTO
        ShelterDTO shelter = new ShelterDTO();
        // Создаем новый объект ShelterLocationDTO
        ShelterLocationDTO shelterLocation = new ShelterLocationDTO();
        // Устанавливаем текущее время в поле dateTime
        shelter.setDateTime(LocalDateTime.now());
        shelterLocation.setDateTime(LocalDateTime.now());
        // Устанавливаем состояние ожидания названия
        stateMap.put(chatId, State.AWAITING_NAME);
        // Сохраняем созданный объект в shelterPendingShelter
        shelterPending.put(chatId, shelter);
        shelterLocationPending.put(chatId, shelterLocation);
        // Выводим сообщение и запрашиваем у пользователя имя приюта
        System.out.println("State set to  for chatId: " + chatId);
        sendResponseMessage(chatId, "Пожалуйста, введите название приюта:");


    }

    //Создание животного
    public void createAnimal(Long chatId) {
        // Создаем новый объект AnimalDTO
        AnimalDTO animal = new AnimalDTO();

        // Устанавливаем текущее время в поле dateTime
        animal.setDateTime(LocalDateTime.now());

        // Устанавливаем состояние ожидания имени
        stateMap.put(chatId, State.AWAITING_ANIMAL_NAME);
        // Сохраняем созданный объект в animalPending
        amimalPending.put(chatId, animal);

        // Выводим сообщение и запрашиваем  имя
        System.out.println("State set to  for chatId: " + chatId);
        sendResponseMessage(chatId, "Пожалуйста, введите имя животного:");
    }

    /*
     * Создание пользователя*/
    public void createUser(Long chatId) {
        // Создаем новый объект UserDTO
        UserDTO user = new UserDTO();
        user.setChatId(chatId);
        // Устанавливаем текущее время в поле dateTime
        user.setDateTime(LocalDateTime.now());

        // Устанавливаем состояние ожидания имени
        stateMap.put(chatId, State.AWAITING_USER_NAME);
        // Сохраняем созданный объект в userPending
        userPending.put(chatId, user);

        // Выводим сообщение и запрашиваем  имя
        System.out.println("State set to  for chatId: " + chatId);
        sendResponseMessage(chatId, "Пожалуйста, введите имя пользователя:");


    }


    private void handleAddUserInput(Long chatId, String text) throws TelegramApiException {
        UserDTO user = userPending.get(chatId);
        State currentState = stateMap.get(chatId);


        switch (currentState) {
            case AWAITING_USER_NAME -> {
                user.setName(text);
                stateMap.put(chatId, State.AWAITING_FAMILY); // Переход к фамилии
                sendResponseMessage(chatId, "Пожалуйста, введите Фамилию:");
            }
            case AWAITING_FAMILY -> {
                user.setFamily(text);
                stateMap.put(chatId, State.AWAITING_ROLE);
                sendResponseMessage(chatId, "Пожалуйста, введите роль пользователя:");
            }
            case AWAITING_ROLE -> {
                AccessLevel role = getAccessLevelFromText(text);
                if (role != null) {
                    user.setRole(role.name()); // Используйте name() для получения имени Enum в виде строки
                    stateMap.put(chatId, State.AWAITING_PHONE);
                    sendResponseMessage(chatId, "Пожалуйста, введите телефон пользователя:");
                } else {
                    sendResponseMessage(chatId, "Некорректная роль пользователя. Пожалуйста, введите роль снова:");
                }
            }
            case AWAITING_PHONE -> {
                user.setTelephone(text);
                stateMap.put(chatId, State.AWAITING_EMAIL);
                sendResponseMessage(chatId, "Пожалуйста, e-mail пользователя:");
            }
            case AWAITING_EMAIL -> {
                user.setEmail(text);
                // Все поля заполнены, добавляем данные в базу

                addUserToDatabase(user);
                userPending.remove(chatId);
                stateMap.remove(chatId);
                sendResponseMessage(chatId, "Данные пользователя успешно добавлены в базу.");
            }
        }
    }

    private void handleAddAnimalInput(Long chatId, String text) throws TelegramApiException {
        AnimalDTO animal = amimalPending.get(chatId);
        State currentState = stateMap.get(chatId);


        switch (currentState) {
            case AWAITING_ANIMAL_NAME -> {
                animal.setName(text);
                stateMap.put(chatId, State.AWAITING_TYPE); // Переход к типу
                sendResponseMessage(chatId, "Пожалуйста, введите тип животного:");
            }
            case AWAITING_TYPE -> {
                AnimalType type = getTypeAnimalFromText(text);
                if (type != null) {
                    animal.setType_animal(type);
                    stateMap.put(chatId, State.AWAITING_BREED);
                    sendResponseMessage(chatId, "Пожалуйста, введите породу:");
                } else {
                    sendResponseMessage(chatId, "Некорректный тип животного(CAT/DOG)." +
                            " Пожалуйста, введите тип снова:");
                }
            }
            case AWAITING_BREED -> {
                animal.setBreed(text);
                stateMap.put(chatId, State.AWAITING_ANIMAL_TEXT);
                sendResponseMessage(chatId, "Пожалуйста, введите краткое описание животного:");

            }

            case AWAITING_ANIMAL_TEXT -> {
                animal.setText(text);
                // Все поля заполнены, добавляем данные в базу

                addAnimalToDatabase(animal);

                amimalPending.remove(chatId);
                stateMap.remove(chatId);
                sendResponseMessage(chatId, "Данные животного успешно добавлены в базу.");
            }
        }
    }




    private AccessLevel getAccessLevelFromText(String text) {
        try {
            return AccessLevel.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Роль не найдена
        }
    }

    private AnimalType getTypeAnimalFromText(String text) {
        try {
            return AnimalType.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Тип не найден
        }
    }

    private void addUserToDatabase(UserDTO user) {
        // Сохраните пользователя в базе данных

        userService.createUser(user);

    }
    private void addAnimalToDatabase(AnimalDTO animal) {
        animalService.createAnimal(animal);
    }

    private void handleAddShelterInput(Long chatId, String text) throws TelegramApiException {

        State currentState = stateMap.get(chatId);
        System.out.println("Current state for chatId " + chatId + ": " + currentState);

        ShelterDTO shelter = shelterPending.get(chatId);

        if (currentState == State.AWAITING_NAME) {
            shelter = shelterPending.get(chatId);
            shelter.setName(text);
            stateMap.put(chatId, State.AWAITING_SAFETY); // Переход к рекомендациям по безопасности
            sendResponseMessage(chatId, "Пожалуйста, введите рекомендации по безопасности:");
        } else if (currentState == State.AWAITING_SAFETY) {
            shelter = shelterPending.get(chatId);
            shelter.setSafety(text);
            stateMap.put(chatId, State.AWAITING_ADDRESS); // Теперь переходим к запросу адреса приюта
            sendResponseMessage(chatId, "Пожалуйста, введите адрес приюта:");
        } else if (
                currentState == State.AWAITING_ADDRESS ||
                        currentState == State.AWAITING_CITY ||
                        currentState == State.AWAITING_STATE ||
                        currentState == State.AWAITING_ZIPCODE) {
            ShelterLocationDTO shelterLocation = shelterLocationPending.get(chatId);

            switch (currentState) {
                case AWAITING_ADDRESS -> {
                    shelterLocation.setAddress(text);
                    stateMap.put(chatId, State.AWAITING_CITY);
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

                    System.out.println("ShelterLocationDTO: " + shelter.getShelterLocationDTO()); // Вывод для отладки
                    shelterLocation.setZipcode(text);
                    // Все поля заполнены, добавляем данные в базу

                    addShelterAndAddressToDatabase(shelter, shelterLocation);
                    shelterPending.remove(chatId);
                    shelterLocationPending.remove(chatId);
                    stateMap.remove(chatId);
                    sendResponseMessage(chatId, "Данные приюта успешно добавлены в базу.");
                }
            }
        }


    }

    private void addShelterAndAddressToDatabase(ShelterDTO shelter, ShelterLocationDTO shelterLocation) {
        // Сохраните приют в базе данных

        shelterLocationService.createShelterLocation(shelterLocation);
        shelterService.createShelter(shelter);

        // Дополнительная логика, если необходимо
    }


    private void sendResponseMessage(Long chatId, String message) {
        SendMessage responseMessage = new SendMessage(chatId, message);
        bot.execute(responseMessage);
    }


}

