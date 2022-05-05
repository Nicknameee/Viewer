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

    public void deleteLoadableResourceByName(String filename) {
        loadableResourceRepository.deleteLoadableResourceByName(filename);
    }
}