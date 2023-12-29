package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import logonedigital.webappapi.entity.FileData;
import logonedigital.webappapi.service.fileManager.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * This controller allows me to test uploadFile and downloadFile Features
 */

@RestController
@RequestMapping(path = "/api/file_manager")
@Slf4j
@Tag(name="FileManager APIs")
public class FileManagerController {

    private final FileManager fileManager;

    public FileManagerController( FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public FileData uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        return this.fileManager.uploadFile(file);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable(name = "fileName") String fileName) throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        byte[] imageDate = this.fileManager.dowloadFile(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(header)
                .contentLength(imageDate.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(imageDate);
    }


}
