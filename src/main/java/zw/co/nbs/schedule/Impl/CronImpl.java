package zw.co.nbs.schedule.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import zw.co.nbs.business.api.ExcelGenerationService;
import zw.co.nbs.connection.api.GatewayConn;
import zw.co.nbs.response.dto.TransactionReportDto;
import zw.co.nbs.schedule.api.Cron;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
@Slf4j
public class CronImpl implements Cron {

    private final AtomicBoolean busy = new AtomicBoolean(false);
    private final GatewayConn gatewayConn;
    private final ExcelGenerationService excelGenerationService;
    public static Connection conn;
    @Value("${Gateway.query}")
    private String gatewayQuery;

    public CronImpl(final ApplicationContext context) {
        this.gatewayConn = context.getBean(GatewayConn.class);
        this.excelGenerationService=context.getBean(ExcelGenerationService.class);
    }
    @Override
    @Scheduled(cron = "0/15 * * * * *")
    public void atSchedule() {
        try {
            if (!busy.compareAndSet(false, true)) {
                log.info("Another  scheduler still executing.......");
                Thread.currentThread().interrupt();
                return;
            }
            busy.set(true);
            executeQuery();
        } catch (Exception ex) {
            log.error("Error in running  processor  {} > > {}", Thread.currentThread().getId(), ex.toString());
        } finally {
            busy.set(false);
            Thread.currentThread().interrupt();
        }
    }

    public void executeQuery() {

      LocalDate b = LocalDate.now();
      LocalDate a = LocalDate.now().minusDays(1);
    String prev =  a.toString();
    String curr =   b.toString();

        String query = gatewayQuery
                .replace("{previous_date}",prev)
                .replace("{current_date}",curr);

        List<TransactionReportDto> transactionReportDtoList = new ArrayList<>();
        try {
            ResultSet resultSet = gatewayConn.executeQuery(query);

            while (resultSet.next()) {
                TransactionReportDto reportDto = new TransactionReportDto();

                String description = resultSet.getString("description");
                String currency = resultSet.getString("currency");
                String transactionIdentifier = resultSet.getString("transaction_identifier");
                String responseCode = resultSet.getString("responseCode");
                int numberOfTransactions = resultSet.getInt("NUMBEROFTRANSACTIONS");

                reportDto.setCurrency(currency);
                reportDto.setDescription(description);
                reportDto.setTransactionIdentifier(transactionIdentifier);
                reportDto.setResponseCodeStatus(responseCode);
                reportDto.setNumberOfTransactions(String.valueOf(numberOfTransactions));

                transactionReportDtoList.add(reportDto);

                System.out.println("Description: " + description);
                System.out.println("Currency: " + currency);
                System.out.println("Transaction Identifier: " + transactionIdentifier);
                System.out.println("Response Code: " + responseCode);
                System.out.println("Number of Transactions: " + numberOfTransactions);
                System.out.println("------------------------------------");
            }
            excelGenerationService.generateExcelAndSendEmail(transactionReportDtoList);

            resultSet.close();

            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
