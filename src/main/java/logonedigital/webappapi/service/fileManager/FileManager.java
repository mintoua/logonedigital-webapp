package logonedigital.webappapi.service.fileManager;

import logonedigital.webappapi.entity.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileManager {
    FileData uploadFile(MultipartFile file) throws IOException;
    byte[] dowloadFile(String fileName) throws IOException;
}