package application.data.loadableResources.utils;

import application.data.utils.threads.TaskDistributorTool;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class FileProcessingUtility {
    private static final String PATH = "./src/main/resources/uploads/";

    public static String uploadFile(MultipartFile file) {
        if (!file.isEmpty()) {
            String name = String.format("%s_%s"
                    , generateUniqueFilenameIndex().toString().replace("-" , "")
                    , file.getOriginalFilename().replace(" " , ""));
            Runnable fileSavingTask = () -> {
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(PATH + name));
                    stream.write(bytes);
                    stream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            };
            TaskDistributorTool.execute(fileSavingTask);
            return name;
        }
        return null;
    }

    public static Boolean deleteFiles(List<String> filenames) {
        Boolean errorOccurs = false;
        for (String filename : filenames) {
            try {
                Files.deleteIfExists(
                        Paths.get(PATH + filename));
            }
            catch (IOException e) {
                e.printStackTrace();
                errorOccurs = true;
            }
        }
        return !errorOccurs;
    }

    private static UUID generateUniqueFilenameIndex() {
        return UUID.randomUUID();
    }
}