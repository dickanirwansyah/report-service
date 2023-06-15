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
public class UploadRequest {

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_content")
    private String fileContent;

    @JsonProperty("file_extension")
    private String fileExtension;

    @JsonProperty("file_path")
    private String filePath;
}
