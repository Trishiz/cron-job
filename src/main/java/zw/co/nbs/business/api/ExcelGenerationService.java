package zw.co.nbs.business.api;

import zw.co.nbs.response.dto.TransactionReportDto;

import java.util.List;

public interface ExcelGenerationService {
   void generateExcelAndSendEmail(List<TransactionReportDto> transactionReportDtoList);
}
