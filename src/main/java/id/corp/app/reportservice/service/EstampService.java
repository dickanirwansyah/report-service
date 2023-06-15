package id.corp.app.reportservice.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import id.corp.app.reportservice.helper.PdfPageHelper;
import id.corp.app.reportservice.model.StampRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@Service
public class EstampService {

    @Value("${destination.file}")
    private String destinationFile;

    @Autowired
    private PdfPageHelper pdfPageHelper;

    @Async
    public void stampingPdf(StampRequest request){
        try{
            log.info("start to execute stamping pdf");
            log.info("convert pdf base64 to file");
            byte[] decodeFilePdf = Base64.getDecoder()
                    .decode(request.getDocumentPdf().getBytes(StandardCharsets.UTF_8));
            Path destinantionFilePdf = Paths.get("D:/resources", "my-file.pdf");
            Files.write(destinantionFilePdf, decodeFilePdf);

            log.info("convert materai base64 to file");
            byte[] decodeFileMaterai = Base64.getDecoder()
                    .decode(request.getStampingMaterai().getBytes(StandardCharsets.UTF_8));
            Path destinationFileMaterai = Paths.get("D:/resources", "my-materai.jpg");
            Files.write(destinationFileMaterai, decodeFileMaterai);

            log.info("destination pdf file is = {} ",destinantionFilePdf.toUri());
            String destinantionFilePdfStr = destinantionFilePdf.toString();
            log.info("destination materai file is = {} ",destinationFileMaterai.toUri());
            String destinantionFileMateraiStr = destinationFileMaterai.toString();

            log.info("processing read pdf");
            PdfReader pdfReader = new PdfReader(destinantionFilePdfStr);

            Image image = Image.getInstance(destinantionFileMateraiStr);
            image.setAbsolutePosition(200, 400);
            int total = pdfReader.getNumberOfPages();
            log.info("total pages pdf is = {} ",total);
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("D:/resources/my-new-document.pdf"));
            log.info("stamping in page = {} ",request.getStampingPageNumber());

            for (int i=1; i<= pdfReader.getNumberOfPages(); i++){
                /** pdf stamping in position **/
                PdfContentByte content = pdfStamper.getUnderContent(request.getStampingPageNumber());
                image.setAbsolutePosition(200, 400);
                content.addImage(image);
            }
            pdfStamper.close();
            pdfReader.close();
        }catch (IOException e){
            log.error("error because IOException = {} ",e.getMessage());
            throw new RuntimeException("error stamping pdf");
        }catch (DocumentException e){
            log.error("error because DocumentException = {} ",e.getMessage());
            throw new RuntimeException("error stamping pdf");
        }
    }

}
