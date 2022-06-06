package com.example.e_additives.service;

import com.example.e_additives.entity.EAdditive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
class FileReadingServiceTest {

    @Autowired
    private FileReadingService fileReadingService;

    @Test
    void readFileReturnEmptyListIfFileIsNotSupported() throws IOException {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "file.doc",
                        MediaType.MULTIPART_FORM_DATA_VALUE,
                        "E100".getBytes(StandardCharsets.UTF_8));
        List<String> emptyList = fileReadingService.readFile(file);
        Assertions.assertTrue(emptyList.isEmpty());
    }
}
