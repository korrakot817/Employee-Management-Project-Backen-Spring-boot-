package com.Project.Backend.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileUploadUtil {

    public static String saveFile(String fileName, MultipartFile multipartFile)
            throws IOException {
        Path uploadPath = Paths.get("C:/Users/Rhamty/Projcet/Project-Springboots/FrontendX/src/assets/Files-Upload");


        LocalDateTime time = LocalDateTime.now();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM_HH-mm-ss");
        String formatDateTime = time.format(format);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // RandomStringUtils add dependency <groupId>org.apache.commons</groupId> <artifactId>commons-lang3</artifactId>
        String fileCode = formatDateTime;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode+"-"+fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

        return fileCode;
    }

}
