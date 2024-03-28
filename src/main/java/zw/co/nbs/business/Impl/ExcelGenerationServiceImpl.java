package zw.co.nbs.business.Impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import zw.co.nbs.business.api.ExcelGenerationService;
import zw.co.nbs.response.dto.TransactionReportDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ExcelGenerationServiceImpl implements ExcelGenerationService {
    public void generateExcelAndSendEmail(List<TransactionReportDto> transactionReportDtoList) {
        List<TransactionReportDto> items = transactionReportDtoList;

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Items");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("description");
        headerRow.createCell(1).setCellValue("currency");
        headerRow.createCell(2).setCellValue("transactionIdentifier");
        headerRow.createCell(3).setCellValue("responseCode");
        headerRow.createCell(4).setCellValue("NUMBEROFTRANSACTIONS");

        int rowNumber = 1;
        for (TransactionReportDto transactionReportDto : items) {
            Row dataRow = sheet.createRow(rowNumber++);
            dataRow.createCell(0).setCellValue(transactionReportDto.getDescription());
            dataRow.createCell(1).setCellValue(transactionReportDto.getCurrency());
            dataRow.createCell(2).setCellValue(transactionReportDto.getTransactionIdentifier());
            dataRow.createCell(3).setCellValue(transactionReportDto.getResponseCodeStatus());
            dataRow.createCell(4).setCellValue(transactionReportDto.getNumberOfTransactions());
        }


        String path = System.getProperty("user.home") + File.separator + "Documents";
        path += File.separator + "TRANSACTION_REPORT";
        File customDir = new File(path);

        if (customDir.exists()) {
            System.out.println(customDir + " already exists");
        } else if (customDir.mkdirs()) {
            System.out.println(customDir + "created");
        } else {
            System.out.println("no permissions to create directory path: " + customDir);
        }
        String excelFilePath =customDir.getAbsoluteFile() + File.separator + new Date() + "report.xls";
        try  {
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }



//        try {
//            MimeMessage message = mailSender.crea
//            teMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setTo("ICT Application");
//            helper.setSubject("Excel Report");
//            helper.setText("Please find the attached Excel report.");
//
//            helper.addAttachment("report.xlsx", new File(excelFilePath));
//
//            mailSender.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
    }
}