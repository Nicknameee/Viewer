package application.data.loadableResources.repository;

import application.data.loadableResources.LoadableResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LoadableResourceRepository extends JpaRepository<LoadableResource , Long> {
    @Query("SELECT r FROM LoadableResource r WHERE r.filename=:filename")
    LoadableResource getLoadableResourceByFilename(String filename);

    @Modifying
    @Transactional
    @Query("UPDATE LoadableResource r SET r.filetype=:filetype ," +
            "r.size=:size WHERE r.filename=:filename")
    void updateLoadableResource(@Param("filename")      String filename   ,
                                @Param("filetype")      String filetype   ,
                                @Param("size")          Long size);

    @Modifying
    @Transactional
    @Query("DELETE FROM LoadableResource r WHERE r.filename=:filename")
    void deleteLoadableResource(@Param("filename") String filename);
}