package ru.devpro.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.devpro.model.AvatarCat;
import ru.devpro.model.Cat;
import ru.devpro.repositories.AvatarCatsRepository;
import ru.devpro.repositories.AvatarDogsRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarCatsService {
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private final AvatarCatsRepository avatarCatsRepository;
    private final CatService catService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AvatarCatsService.class);

    public AvatarCatsService(AvatarCatsRepository avatarCatsRepository,
                             CatService catService) {
        this.avatarCatsRepository = avatarCatsRepository;

        this.catService = catService;

    }

    public void uploadAvatar(Long catId, MultipartFile avatarFile) throws IOException {
        LOGGER.info("Uploading avatar for student with ID: {}", catId);
        Cat cat = catService.findCatById(catId);

        //путь к папке где аватар и берем расширение файла(т е после ".")
        Path filePath = Path.of(avatarsDir, catId + "." + getExtensions(avatarFile.getOriginalFilename()));
        //проверка папки по указанному пути и если нет то создаст
        Files.createDirectories(filePath.getParent());
        //удаление старой версии файла если есть
        Files.deleteIfExists(filePath);


        try (
                //открытие входного потока
                InputStream is = avatarFile.getInputStream();
                //создание файла для записи
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                //расширяем поток с размером 1024
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                //запись
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            //передача данных + закрытие потока
            bis.transferTo(bos);
        }
        //заполняем обьект
        AvatarCat avatar = findCatAvatar(catId);
        avatar.setCat(cat);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        //переформатируем в нужный размер исходный файл
        avatar.setPreview(generateImagePreview(filePath));
        //сохраняем в БД
        avatarCatsRepository.save(avatar);
        LOGGER.info("Avatar uploaded successfully for student with ID: {}", catId);
    }


    /**
     * findAvatar - метод поиска картинки у студента
     *
     * @param catId - индитификатор кота
     * @return возврашает найденное или создает новый
     */
    public AvatarCat findCatAvatar(Long catId) {
        LOGGER.info("Finding avatar for student with ID: {}", catId);
        return avatarCatsRepository.findCatById(catId).orElse(new AvatarCat());
    }

    public Page<AvatarCat> listCatsAvatars(Pageable pageable) {
        LOGGER.info("Listing avatars with pagination: page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize());
        return avatarCatsRepository.findAll(pageable);
    }

    /**
     * generateImagePreview - создание уменьшенного изображения
     *
     * @param filePath - путь к файлу искомому
     * @return -
     * @throws IOException
     */
    public byte[] generateImagePreview(Path filePath) throws IOException {
        LOGGER.info("Generating image preview for file: {}", filePath);
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            LOGGER.info("Image preview generated for file: {}", filePath);
            return baos.toByteArray();
        }

    }

    private String getExtensions(String fileName) {
        LOGGER.debug("Getting extension for file name: {}", fileName);
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        LOGGER.debug("Extension found: {}", extension);
        return extension;
    }
}