package application.api.gdrive.service;

import application.api.gdrive.interaction.FileManager;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class GDriveAPIService {
    private FileManager fileManager;

    @Autowired
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public List<File> listFilesFromDirectory(String parentId) throws IOException, GeneralSecurityException {
        return fileManager.listFolderContent(parentId);
    }

    public String createDirectory(String path) throws Exception {
        return fileManager.getFolderId(path);
    }

    public String uploadFile(MultipartFile file , String path) {
        return fileManager.uploadFile(file , path);
    }

    public void deleteFile(String id) throws Exception {
        fileManager.deleteFile(id);
    }
}