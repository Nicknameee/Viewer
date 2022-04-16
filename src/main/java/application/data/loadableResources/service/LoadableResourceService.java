package application.data.loadableResources.service;

import application.data.articles.Article;
import application.data.loadableResources.LoadableResource;
import application.data.loadableResources.models.ResourceType;
import application.data.loadableResources.repository.LoadableResourceRepositoryImplementation;
import application.data.loadableResources.utils.FileProcessingUtility;
import application.data.utils.threads.TaskDistributorTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class LoadableResourceService {
    private LoadableResourceRepositoryImplementation loadableResourceRepository;

    @Autowired
    public void setLoadableResourceRepository(LoadableResourceRepositoryImplementation loadableResourceRepository) {
        this.loadableResourceRepository = loadableResourceRepository;
    }

    public List<LoadableResource> getAllLoadableResources() {
        return loadableResourceRepository.getAllLoadableResources();
    }

    public LoadableResource getLoadableResourceByFilename(String filename) {
        return loadableResourceRepository.getLoadableResourceByFilename(filename);
    }

    public synchronized LoadableResource saveLoadableResource(LoadableResource loadableResource) {
        return loadableResourceRepository.saveLoadableResource(loadableResource);
    }

    public LoadableResource updateLoadableResource(LoadableResource loadableResource) {
        return loadableResourceRepository.updateLoadableResource(loadableResource);
    }

    public void deleteLoadableResource(LoadableResource loadableResource) {
        loadableResourceRepository.deleteLoadableResource(loadableResource.getFilename());
    }

    public void processResourcesForArticle(MultipartFile[] files , Article article) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                Runnable savingMediaFileTask = () -> {
                    try {
                        String uniqueFileName = FileProcessingUtility.uploadFile(file);
                        ResourceType type = file.getContentType().contains("image")
                                ? ResourceType.IMAGE : ResourceType.VIDEO;
                        saveLoadableResource(new LoadableResource(uniqueFileName , type , file.getSize() , article));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                TaskDistributorTool.execute(savingMediaFileTask);
            }
        }
    }
}