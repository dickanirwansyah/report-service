package id.corp.app.reportservice.helper;

import id.corp.app.reportservice.entity.Accounts;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ExcelsHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String[] headersOfExcel = {"ID", "FULLNAME", "USERNAME", "PHONENUMBER", "ADRESS", "TWITTER", "STATUS"};
    public static String accountSheet = "Accounts Active";

    public static boolean excelFormatValidate(MultipartFile file){
        if (!file.getContentType().equals(TYPE)){
            log.error("invalid format, this not excel file.");
            return false;
        }
        log.info("valid format excel.");
        return true;
    }

    /** to upload data excel **/
    public static final List<Accounts> parseAccounts(InputStream inputStream){
        try{
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(accountSheet);
            Iterator<Row> rows = sheet.iterator();
            List<Accounts> accounts = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()){
                Row currentRow = rows.next();
                /** skip header **/
                if (rowNumber == 0){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Accounts accountData = new Accounts();

                int cellIndex = 0;
                while (cellsInRow.hasNext()){
                    Cell currentCell = cellsInRow.next();

                    switch (cellIndex){
                        case 0 :
                            accountData.setId((long) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            accountData.setFullName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            accountData.setUsername(currentCell.getStringCellValue());
                            break;
                        case 3:
                            accountData.setPhoneNumber(String.valueOf(currentCell.getNumericCellValue()));
                            break;
                        case 4:
                            accountData.setAddress(currentCell.getStringCellValue());
                            break;
                        case 5:
                            accountData.setTwitter(currentCell.getStringCellValue());
                            break;
                        case 6:
                            accountData.setStatus(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIndex++;
                }
                accounts.add(accountData);
            }
            workbook.close();
            log.info("success parse accounts");
            return accounts;
        }catch (IOException e){
            log.error("error when parse account from [stacktrace] = {} ",e.getStackTrace());
            log.error("error when parse account from [message] = {} ",e.getMessage());
            throw new RuntimeException("failed to parse excel file");
        }
    }

    /** to download data excel **/
    public static ByteArrayInputStream parseAccountToDownload(List<Accounts> accountsList){

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            Sheet sheet = workbook.createSheet(accountSheet);
            /** header **/
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headersOfExcel.length; col++){
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headersOfExcel[col]);
            }
            int rowIndex = 1;
            for (Accounts accounts : accountsList){
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(accounts.getId());
                row.createCell(1).setCellValue(accounts.getFullName());
                row.createCell(2).setCellValue(accounts.getUsername());
                row.createCell(3).setCellValue(accounts.getPhoneNumber());
                row.createCell(4).setCellValue(accounts.getAddress());
                row.createCell(5).setCellValue(accounts.getTwitter());
                row.createCell(6).setCellValue(accounts.getStatus());
            }
            workbook.write(byteArrayOutputStream);
            log.info("success download data to excel");
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        }catch (IOException e){
            log.error("error when parse account to download [stacktrace] = {}",e.getStackTrace());
            log.error("error when parse account to download [message] = {} ",e.getMessage());
            throw new RuntimeException("failed to download file excel");
        }
    }
}
