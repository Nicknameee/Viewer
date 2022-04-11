package application.data.loadableResources.service;

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

    public LoadableResource getLoadableResourceByFilename(LoadableResource loadableResource) {
        return loadableResourceRepository.getLoadableResourceByFilename(
                loadableResource.getFilename()
        );
    }

    public synchronized LoadableResource saveLoadableResource(LoadableResource loadableResource) {
        return loadableResourceRepository.saveLoadableResource(loadableResource);
    }

    public LoadableResource updateLoadableResource(LoadableResource loadableResource) {
        return loadableResourceRepository.updateLoadableResource(loadableResource);
    }

    public void deleteLoadableResource(LoadableResource loadableResource) {
        loadableResourceRepository.deleteLoadableResource(loadableResource.getId());
    }

    public void processResourcesForArticle(MultipartFile[] files , Integer articleId) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                Runnable savingMediaFileTask = () -> {
                    try {
                        String uniqueFileName = FileProcessingUtility.uploadFile(file);
                        ResourceType type = file.getContentType().contains("image")
                                ? ResourceType.IMAGE : ResourceType.VIDEO;
                        saveLoadableResource(new LoadableResource(0L , articleId , uniqueFileName , type , file.getSize()));
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