package id.corp.app.reportservice.service;

import id.corp.app.reportservice.entity.Accounts;
import id.corp.app.reportservice.helper.ExcelsHelper;
import id.corp.app.reportservice.model.ValidateResponse;
import id.corp.app.reportservice.repository.AccountsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    /** update **/
    @Transactional
    public ValidateResponse save(MultipartFile file){
        log.info("file information to upload is content type {}, name {} , original file name {} ",file.getContentType(), file.getName(), file.getOriginalFilename());
        if (ExcelsHelper.excelFormatValidate(file)){
            try{
                List<Accounts> accountsList = ExcelsHelper.parseAccounts(file.getInputStream());
                this.accountsRepository.saveAll(accountsList);
                log.info("success save data from excel");
                return ValidateResponse.builder()
                        .valid(true)
                        .fileName(file.getOriginalFilename())
                        .totalData(accountsList.size())
                        .sizeFile(file.getSize())
                        .status("file status is valid")
                        .build();
            }catch (IOException e){
                log.error("error save data from [stacktrace] = {} ",e.getStackTrace());
                log.error("error save data from [message] = {} ",e.getMessage());
                return ValidateResponse.builder()
                        .valid(false)
                        .fileName(file.getOriginalFilename())
                        .totalData(0)
                        .sizeFile(file.getSize())
                        .status("file status is valid")
                        .build();
            }
        }
        return ValidateResponse.builder()
                .valid(false)
                .fileName(file.getOriginalFilename())
                .sizeFile(file.getSize())
                .status("file status invalid format")
                .build();
    }

    public ByteArrayInputStream store(){
        List<Accounts> accounts = accountsRepository.findAll();
        if (!accounts.isEmpty()){
            ByteArrayInputStream inputStream = ExcelsHelper.parseAccountToDownload(accounts);
            log.info("data is not empty ready to download");
            return inputStream;
        }
        log.info("data is empty");
        return null;
    }

    public List<Accounts> allAccounts(){
        return accountsRepository.findAll();
    }
}
