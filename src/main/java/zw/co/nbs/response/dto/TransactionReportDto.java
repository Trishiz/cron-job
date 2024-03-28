package zw.co.nbs.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReportDto {

        private String description;
        private String currency;
        private String responseCodeStatus;
        private String numberOfTransactions;
        private String transactionIdentifier;
}

