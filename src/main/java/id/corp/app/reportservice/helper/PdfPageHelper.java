package id.corp.app.reportservice.helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class PdfPageHelper extends PdfPageEventHelper {

    protected Phrase watermark = new Phrase("WATERMARK", new Font(Font.FontFamily.HELVETICA, 60, Font.NORMAL, BaseColor.LIGHT_GRAY));

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContentUnder();
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, watermark, 298, 421, 45);
    }

    public void pdfStamper(String dest) throws IOException, DocumentException{
        log.info("execute pdf stamper = {} ",dest);
        PdfReader pdfReader = new PdfReader(dest);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(dest));
        File file = new File("D:/resources/materai.jpg");
        if (file.exists()){
            log.info("image materai is exist");
            Image image = Image.getInstance(file.getPath());
            for (int i=0; i <= pdfReader.getNumberOfPages(); i++){
                PdfContentByte contentByte = pdfStamper.getUnderContent(i);
                image.setAbsolutePosition(150f, 750f);
                contentByte.addImage(image);
            }
            pdfStamper.close();
        }else{
            log.info("error because materai not exist");
            throw new RuntimeException("failed stamping");
        }
    }
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        writer.setPageEvent(new PdfPageHelper());
        document.open();
        document.add(new Chunk(""));
        document.close();
    }

}
