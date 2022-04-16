package application.data.loadableResources.repository;

import application.data.loadableResources.LoadableResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoadableResourceRepositoryImplementation {
    private LoadableResourceRepository loadableResourceRepository;

    @Autowired
    public void setLoadableResourceRepository(LoadableResourceRepository loadableResourceRepository) {
        this.loadableResourceRepository = loadableResourceRepository;
    }

    public List<LoadableResource> getAllLoadableResources() {
        return loadableResourceRepository.findAll();
    }

    public LoadableResource getLoadableResourceByFilename(String filename) {
        return loadableResourceRepository.getLoadableResourceByFilename(filename);
    }

    public LoadableResource saveLoadableResource(LoadableResource loadableResource) {
        return loadableResourceRepository.save(loadableResource);
    }

    public LoadableResource updateLoadableResource(LoadableResource loadableResource) {
        loadableResourceRepository.updateLoadableResource(
                loadableResource.getFilename()  ,
                loadableResource.getFilename()  ,
                loadableResource.getSize()
        );
        return getLoadableResourceByFilename(loadableResource.getFilename());
    }

    public void deleteLoadableResource(String filename) {
        loadableResourceRepository.deleteLoadableResource(filename);
    }
}