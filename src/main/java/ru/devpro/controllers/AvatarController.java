package ru.devpro.controllers;

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
import ru.devpro.model.AvatarCat;
import ru.devpro.service.AvatarCatsService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("avatar")


public class AvatarController {
    private final AvatarCatsService avatarCatsService;

    public AvatarController(AvatarCatsService avatarCatsService) {
        this.avatarCatsService = avatarCatsService;
    }

    @PostMapping(value = "/{catId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long catId,
                                               @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 600) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        avatarCatsService.uploadAvatar(catId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        AvatarCat avatarCat = avatarCatsService.findCatAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatarCat.getMediaType()));
        headers.setContentLength(avatarCat.getPreview().length);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(avatarCat.getPreview());
    }


    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        AvatarCat avatar = avatarCatsService.findCatAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping(value = "/page-avatars-from-db")
    public ResponseEntity<Page<AvatarCat>> downloadAvatars(@RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "1") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AvatarCat> avatarPage = avatarCatsService.listCatsAvatars(pageable);

        return ResponseEntity.ok(avatarPage);
    }
}
