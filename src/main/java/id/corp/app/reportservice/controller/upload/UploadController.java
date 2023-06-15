package id.corp.app.reportservice.controller.upload;

import id.corp.app.reportservice.model.upload.UploadRequest;
import id.corp.app.reportservice.model.upload.UploadResponse;
import id.corp.app.reportservice.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping(value = "/send")
    public ResponseEntity<UploadResponse> send(@RequestBody UploadRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadService.doUpload(request));
    }
}
