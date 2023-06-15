package id.corp.app.reportservice.repository;

import id.corp.app.reportservice.entity.ResourceFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceFilesRepository extends JpaRepository<ResourceFiles, Long> {
}
