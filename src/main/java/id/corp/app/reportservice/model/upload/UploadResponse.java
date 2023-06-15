package id.corp.app.reportservice.model.upload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse {

    @JsonProperty("file_id")
    private Long fileId;

    @JsonProperty("file_content")
    private String fileContent;

    @JsonProperty("file_path")
    private String filePath;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_extension")
    private String fileExtension;
}
