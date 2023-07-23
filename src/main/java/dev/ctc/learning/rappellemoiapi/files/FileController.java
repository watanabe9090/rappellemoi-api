package dev.ctc.learning.rappellemoiapi.files;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;




    @PostMapping
    public ResponseEntity<Void> uploadFiles(@RequestParam("file") MultipartFile file) throws FileAlreadyExistsException {
        fileService.save(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
