package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepo extends JpaRepository<FileData,Integer> {
    Optional<FileData> findByFileName(String fileName);
}
