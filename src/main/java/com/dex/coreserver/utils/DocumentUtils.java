package com.dex.coreserver.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class DocumentUtils {

    public static byte[] transformInputStreamInByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }

    public static String[] getUUIDandMymeTypeShort(MultipartFile multipartFile) {
        String[] documentValues = new String[2];
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        if (multipartFile.getContentType().equals("application/pdf")) {
            documentValues[0] = randomUUIDString + ".pdf";
            documentValues[1] =".pdf";
        } else if (multipartFile.getContentType().equals("image/png")) {
            documentValues[0] = randomUUIDString + ".png";
            documentValues[1] =".png";
        } else if (multipartFile.getContentType().equals("image/jpeg")) {
            documentValues[0] = randomUUIDString + ".jpg";
            documentValues[1] =".jpg";
        } else if (multipartFile.getContentType().equals("image/gif")) {
            documentValues[0] = randomUUIDString + ".gif";
            documentValues[1] =".gif";
        } else if (multipartFile.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            documentValues[0] = randomUUIDString + ".xlsx";
            documentValues[1] =".xlsx";
        } else if (multipartFile.getContentType().equals("application/vnd.ms-excel")) {
            documentValues[0] = randomUUIDString + ".csv";
            documentValues[1] =".csv";
        } else if (multipartFile.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")){
            documentValues[0] = randomUUIDString + ".docx";
            documentValues[1] =".docx";
        } else if (multipartFile.getContentType().equals("application/msword")){
            documentValues[0] = randomUUIDString + ".doc";
            documentValues[1] =".doc";
        }
        return documentValues;
    }

    public static void saveDocumentOnDisc(String randomUUIDString, MultipartFile multipartFile) {
        Path targetPath = Paths.get( ApplicationUtil.getFilePath()+ randomUUIDString);
        try (InputStream input = multipartFile.getInputStream()) {
            Files.copy(input, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("FILE_PATH_NOT_FOUND");
        }
    }

    public static void downloadFile(String pathToFile, HttpServletResponse response) throws Exception{
        File file = new File(pathToFile);
        if (file.exists()) {

            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

}
