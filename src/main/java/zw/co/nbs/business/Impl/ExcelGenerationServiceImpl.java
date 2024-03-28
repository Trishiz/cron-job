package zw.co.nbs.business.Impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import zw.co.nbs.business.api.ExcelGenerationService;
import zw.co.nbs.response.dto.TransactionReportDto;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ExcelGenerationServiceImpl implements ExcelGenerationService {
    private final JavaMailSender mailSender;
    public ExcelGenerationServiceImpl(final ApplicationContext context){
        this.mailSender=context.getBean(JavaMailSender.class);
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
        String excelFilePath =customDir.getAbsoluteFile() + File.separator + new Date() + "report.xls";
        try  {
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setTo("majonitrish522@gmail.com");
//            helper.setSubject("Excel Report");
//            helper.setText("Please find the attached Excel transaction report.");
//
//            helper.addAttachment("report.xlsx", new File(excelFilePath));
//            System.out.println(helper);
//            mailSender.send(message);
            sendSimpleMessage();
        } catch (IOException e) {
            log.error("Error sending email with attachment: ", e);
        }
//        catch (MessagingException e){
//      }
    }

    public void sendSimpleMessage(
            ) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("majonitrish522@gmail.com");
        message.setTo("trish.majoni@nbs.co.zw");
        message.setSubject("Excel Report");
        message.setText("Please find the attached Excel transaction report.");
        mailSender.send(message);
        System.out.println(message);

    }

    }




//@Bean
//public JavaMailSender getJavaMailSender() {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//    mailSender.setHost("smtp.gmail.com");
//    mailSender.setPort(587);
//
//    mailSender.setUsername("my.gmail@gmail.com");
//    mailSender.setPassword("password");
//
//    Properties props = mailSender.getJavaMailProperties();
//    props.put("mail.transport.protocol", "smtp");
//    props.put("mail.smtp.auth", "true");
//    props.put("mail.smtp.starttls.enable", "true");
//    props.put("mail.debug", "true");
//
//    return mailSender;
//============================================================
//spring.mail.host=smtp.gmail.com
//spring.mail.port=587
//spring.mail.username=<login user to smtp server>
//spring.mail.password=<login password to smtp server>
//spring.mail.properties.mail.smtp.auth=true
//spring.mail.properties.mail.smtp.starttls.enable=true
