package zw.co.nbs.business.Impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import zw.co.nbs.business.api.ExcelGenerationService;
import zw.co.nbs.integrations.auth.api.NotificationService;
import zw.co.nbs.response.dto.TransactionReportDto;
import zw.co.nbs.utils.notifications.request.SendEmailRequestDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ExcelGenerationServiceImpl implements ExcelGenerationService {
    private final JavaMailSender mailSender;
    private final NotificationService notificationService;
   private String message;
//    private MultipartFile multipartFile;

    public ExcelGenerationServiceImpl(final ApplicationContext context) {
        this.mailSender = context.getBean(JavaMailSender.class);
        this.notificationService = context.getBean(NotificationService.class);
    }

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
        String excelFilePath = customDir.getAbsoluteFile() + File.separator + new Date() + "report.xls";
        try {
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            sendMessage(message);
        } catch (IOException e) {
            log.error("Error sending email with attachment: ", e);
        }
    }

    public void sendMessage(final String message) {
        SendEmailRequestDto dto = new SendEmailRequestDto();
        dto.setMessage(message);
        dto.setSubject("TRANSACTION REPORT}");
        dto.setTo(new String[]{TransactionReportDto.getEmailAddress()});
        dto.setPersonalizationText("National Building Society");
        log.info("Email: {}", message);

//        MultipartFile multipartFile = convertToMultipartFile(dto);
//
//
//        notificationService.uploadEmailAttachments(multipartFile);
    }
//    private MultipartFile convertToMultipartFile(SendEmailRequestDto dto) {
//        File tempFile;
//        try {
//            tempFile = File.createTempFile("temp", ".tmp");
//            tempFile.deleteOnExit();
//        } catch (IOException e) {
//            return null;
//        }
//        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
//            fos.write(dto.getMessage().getBytes());
//        } catch (IOException e) {
//            return null;
//        }
//        return multipartFile;
//    }
}