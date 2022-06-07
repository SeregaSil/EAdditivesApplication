package com.example.e_additives.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
class FileReadingServiceTest {

    @Autowired
    private FileReadingService fileReadingService;

    @Test
    void readFileReturnEmptyListIfFileIsNotSupported() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                        "file",
                        "file.png",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        "Е100".getBytes(StandardCharsets.UTF_8)
        );
        List<String> emptyList = fileReadingService.readFile(file);
        Assertions.assertTrue(emptyList.isEmpty());
    }

    @Test
    void readFileReturnListWithIndexesIfFileHasIndexes() throws IOException {
        MultipartFile file = new MockMultipartFile(
                "file",
                "file.txt",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "Е100".getBytes(StandardCharsets.UTF_8)
        );
        List<String> indexesList = fileReadingService.readFile(file);
        Assertions.assertEquals("E100", indexesList.get(0));
    }
}
