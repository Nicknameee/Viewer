package application.data.loadableResources.service;

import application.api.service.GDriveAPIService;
import application.data.articles.Article;
import application.data.loadableResources.LoadableResource;
import application.data.loadableResources.models.ResourceType;
import application.data.loadableResources.repository.LoadableResourceRepositoryImplementation;
import application.data.utils.converters.CustomPropertySourceConverter;
import application.data.utils.generators.CodeGenerator;
import application.data.utils.loaders.CustomPropertyDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class LoadableResourceService {
    private LoadableResourceRepositoryImplementation loadableResourceRepository;

    @Autowired
    public void setLoadableResourceRepository(LoadableResourceRepositoryImplementation loadableResourceRepository) {
        this.loadableResourceRepository = loadableResourceRepository;
    }

    public List<LoadableResource> processResourcesForArticle(MultipartFile[] files , Article article , GDriveAPIService driveAPIService) {
        Map<String , String> driveAPIProperties =
                CustomPropertySourceConverter.convertToKeyValueFormat
                        (CustomPropertyDataLoader.getResourceContent("classpath:gdrive/drive.properties"));
        List<LoadableResource> resourceList = new LinkedList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String name = CodeGenerator.generateUniqueCode().toString();
                MultipartFile fileWithUniqueName = new MultipartFile() {
                    @Override
                    public String getName() {
                        return name;
                    }

                    @Override
                    public String getOriginalFilename() {
                        return name;
                    }

                    @Override
                    public String getContentType() {
                        return file.getContentType();
                    }

                    @Override
                    public boolean isEmpty() {
                        return file.isEmpty();
                    }

                    @Override
                    public long getSize() {
                        return file.getSize();
                    }

                    @Override
                    public byte[] getBytes() throws IOException {
                        return file.getBytes();
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        return file.getInputStream();
                    }

                    @Override
                    public void transferTo(File dest) throws IOException, IllegalStateException {

                    }
                };
                String fileId = driveAPIService.uploadFile(fileWithUniqueName , driveAPIProperties.get("root") + article.getFolderName());
                ResourceType type = file.getContentType().contains("image")
                        ? ResourceType.IMAGE : ResourceType.VIDEO;
                resourceList.add(new LoadableResource(0L , name , type , file.getSize() , article , fileId));
            }
        }
        return resourceList;
    }

    public void deleteLoadableResourceByName(String filename , String fileId , GDriveAPIService driveAPIService) throws Exception {
        driveAPIService.deleteFile(fileId);
        loadableResourceRepository.deleteLoadableResourceByName(filename);
    }
}