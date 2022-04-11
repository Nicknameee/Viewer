package application.data.loadableResources.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.UUID;

public class FileProcessingUtility {
    public static String uploadFile(MultipartFile file) {
        if (!file.isEmpty()) {
            String path = "./src/main/resources/uploads/";
            String name = String.format("%s_%s"
                    , generateUniqueFilenameIndex().toString().replace("-" , "")
                    , file.getOriginalFilename().replace(" " , ""));
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(path + name));
                stream.write(bytes);
                stream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return name;
        }
        return null;
    }

    private static UUID generateUniqueFilenameIndex() {
        return UUID.randomUUID();
    }
}