package logonedigital.webappapi.service.fileManager;

import logonedigital.webappapi.entity.FileData;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.repository.FileDataRepo;
import logonedigital.webappapi.utils.SomeConstants;
import logonedigital.webappapi.utils.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileManagerImpl implements FileManager {

    private final FileDataRepo fileDataRepo;

    public FileManagerImpl(FileDataRepo fileDataRepo) {
        this.fileDataRepo = fileDataRepo;
    }

/*    @Override
    public FileData uploadFile(MultipartFile file) throws IOException {
        String fileName = Tool.generateFileName(file);
        String filePath = SomeConstants.IMG_PATH_FOLDER+"\\"+fileName;

        file.transferTo(new File(filePath));
        return this.fileDataRepo.save(FileData.build(null,
                fileName,
                file.getContentType(),
                filePath));
    }*/

    @Override
    public FileData uploadFile(MultipartFile file) throws IOException {
        String fileName = Tool.generateFileName(file);
        String filePath;

        // Determine path based on file type (assuming extension-based mapping):
        if (file.getContentType().startsWith("image/")) {
            filePath = SomeConstants.IMG_PATH_FOLDER + "\\" + fileName;
        } else if (file.getContentType().startsWith("application/pdf")) {
            filePath = SomeConstants.BROCHURE_PATH_FOLDER + "\\" + fileName;
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }

        file.transferTo(new File(filePath));
        return this.fileDataRepo.save(FileData.build(null, fileName, file.getContentType(), filePath));
    }

    @Override
    public byte[] dowloadFile(String fileName) throws IOException {
        Optional<FileData> fileData = this.fileDataRepo.findByFileName(fileName);
        if(fileData.isEmpty())
            throw new RessourceNotFoundException("This file doesn't exist !");

        return Files.readAllBytes(new File(fileData.get().getFilePath()).toPath());
    }
}