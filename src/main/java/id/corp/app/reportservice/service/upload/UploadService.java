package id.corp.app.reportservice.service.upload;

import com.google.gson.Gson;
import id.corp.app.reportservice.entity.ResourceFiles;
import id.corp.app.reportservice.model.upload.UploadRequest;
import id.corp.app.reportservice.model.upload.UploadResponse;
import id.corp.app.reportservice.repository.ResourceFilesRepository;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class UploadService {

    @Autowired
    private ResourceFilesRepository resourceFilesRepository;

    @Value("${upload.file}")
    private String rootFile;

    @Synchronized
    public UploadResponse doUpload(UploadRequest request){
        log.info("request upload = {}",new Gson().toJson(request));
        try{
            if (request.getFilePath().isEmpty() || Objects.isNull(request.getFilePath())){
                log.info("convert base64 to file");
                byte[] decodeFileBase64 = Base64.getDecoder().decode(request.getFileContent().getBytes(StandardCharsets.UTF_8));

                log.info("create folder and replace file into folder");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(rootFile);
                stringBuilder.append("\\");
                stringBuilder.append(UUID.randomUUID().toString());
                log.info("expected folder = {}",stringBuilder.toString());

                File fileFolder = new File(stringBuilder.toString());
                if (!fileFolder.exists()){
                    log.info("folder is not existing..");
                    fileFolder.mkdirs();
                }

                log.info("folder is sucessfully creating..");
                Path destinationFiles = Paths.get(fileFolder.getPath(), request.getFileName()
                        .concat("."+request.getFileExtension()));
                Files.write(destinationFiles, decodeFileBase64);
                request.setFilePath(stringBuilder.toString());
            }
            log.info("file is succesfully upload");
            ResourceFiles resourceFiles = this.resourceFiles(request);
            return UploadResponse.builder()
                    .fileId(resourceFiles.getId())
                    .fileContent(resourceFiles.getFileContent())
                    .filePath(resourceFiles.getFilePath())
                    .fileName(resourceFiles.getFileName())
                    .fileExtension(resourceFiles.getExtension())
                    .build();
        }catch (IOException IOE){
            log.error("error IOE because = {}",IOE.getMessage());
        }
        return null;
    }

    private ResourceFiles resourceFiles(UploadRequest request){
        ResourceFiles resourceFiles = ResourceFiles.builder()
                .fileName(request.getFileName())
                .fileContent(request.getFileContent())
                .extension(request.getFileExtension())
                .filePath(request.getFilePath())
                .createdAt(LocalDateTime.now())
                .build();
        return this.resourceFilesRepository.save(resourceFiles);
    }
}
