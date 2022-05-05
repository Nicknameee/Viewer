package application.data.loadableResources.repository;

import application.data.loadableResources.LoadableResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LoadableResourceRepository extends JpaRepository<LoadableResource , Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM LoadableResource r WHERE r.filename=:filename")
    void deleteLoadableResourceByName(@Param("filename") String filename);
}