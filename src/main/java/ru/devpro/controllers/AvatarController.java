package ru.devpro.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.devpro.model.Avatar;
import ru.devpro.service.AvatarServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("avatar")
@Tag(name = "Аватарки", description = "Методы работы с аватарками")

public class AvatarController {
    private final AvatarServiceImpl avatarServiceImpl;

    public AvatarController(AvatarServiceImpl avatarServiceImpl) {
        this.avatarServiceImpl = avatarServiceImpl;
    }

    @PostMapping(value = "/{animalId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long animalId,
                                               @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 600) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        avatarServiceImpl.uploadAvatar(animalId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarServiceImpl.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getPreview().length);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(avatar.getPreview());
    }


    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarServiceImpl.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(value = "/page-avatars-from-db")
    public ResponseEntity<Page<Avatar>> downloadAvatars(@RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "1") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Avatar> avatarPage = avatarServiceImpl.listAvatars(pageable);

        return ResponseEntity.ok(avatarPage);
    }
}
