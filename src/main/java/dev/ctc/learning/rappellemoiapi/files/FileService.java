package dev.ctc.learning.rappellemoiapi.files;

import dev.ctc.learning.rappellemoiapi.base.BaseService;
import dev.ctc.learning.rappellemoiapi.user.User;
import dev.ctc.learning.rappellemoiapi.user.UserRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService extends BaseService {
    private final Path root = Paths.get("uploads");
    private final FileRepository fileRepository;

    public FileService(UserRepository userRepository, FileRepository fileRepository) {
        super(userRepository);
        this.fileRepository = fileRepository;
    }

    public void save(MultipartFile file) throws FileAlreadyExistsException {
        User user = getUser();
        try {
            String systemFilename = UUID.randomUUID().toString();
            Path newFilePath = this.root
                    .resolve(String.valueOf(user.getId()))
                    .resolve(systemFilename);
            Files.createDirectories(newFilePath.getParent());
            Files.copy(file.getInputStream(), newFilePath);
            File newFile = File.builder()
                    .filename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .systemFilename(systemFilename)
                    .systemPath(newFilePath.toString())
                    .build();
            fileRepository.save(newFile);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new FileAlreadyExistsException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

}
