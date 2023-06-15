package id.corp.app.reportservice.controller;

import id.corp.app.reportservice.entity.Accounts;
import id.corp.app.reportservice.model.ValidateResponse;
import id.corp.app.reportservice.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(value = "/api/v1/reports")
@RestController
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @PostMapping(value = "/upload")
    public ResponseEntity<ValidateResponse> upload(@RequestParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountsService.save(file));
    }

    @GetMapping(value = "/download")
    public ResponseEntity<Resource> download(){

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filemame="+"data_accounts.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(accountsService.store()));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<Accounts>> list(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountsService.allAccounts());
    }
}
