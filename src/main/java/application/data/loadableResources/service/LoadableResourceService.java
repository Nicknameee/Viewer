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

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

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

    public List<LoadableResource> processResourcesForArticle(MultipartFile[] files , Article article) {
        List<LoadableResource> resourceList = new LinkedList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String name = FileProcessingUtility.uploadFile(file);
                ResourceType type = file.getContentType().contains("image")
                        ? ResourceType.IMAGE : ResourceType.VIDEO;
                resourceList.add(new LoadableResource(0L , name , type , file.getSize() , article));
            }
        }
        return resourceList;
    }

    public void deleteLoadableResourceByName(String filename) {
        loadableResourceRepository.deleteLoadableResourceByName(filename);
    }
}