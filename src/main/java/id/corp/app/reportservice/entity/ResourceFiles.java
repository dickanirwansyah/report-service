package id.corp.app.reportservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resource_files")
public class ResourceFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Lob
    @Column(name = "file_content", nullable = false, columnDefinition = "text")
    private String fileContent;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
