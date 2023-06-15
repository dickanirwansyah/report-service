package id.corp.app.reportservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StampRequest {

    @JsonProperty("document_pdf")
    private String documentPdf;
    @JsonProperty("stamping_materai")
    private String stampingMaterai;
    @JsonProperty("stamping_page_number")
    private Integer stampingPageNumber;
}
