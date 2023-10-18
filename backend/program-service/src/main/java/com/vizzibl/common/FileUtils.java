package com.vizzibl.common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    public static void createFile(String uploadDir, String fileName,
                                  InputStream inputStream) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public static String renameFile(String originalFileName, String newName, String uuid) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return uuid + "-" + newName + extension;
    }

    public static String copyFileUsingStream(File source, String file, String tempSavePath) throws IOException {

        String tempSaveFile = tempSavePath + file;
        File tempD = new File(tempSavePath);
        if (!tempD.exists()) {
            tempD.mkdirs();
        }
        File test = new File(tempSaveFile);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(test);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {

            is.close();
            os.close();
        }
        return tempSaveFile;
    }


}
