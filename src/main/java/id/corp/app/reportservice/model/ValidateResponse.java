package id.corp.app.reportservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidateResponse {
    private boolean valid;
    private String fileName;
    private Integer totalData;
    private Long sizeFile;
    private String status;
}
