package zw.co.nbs.response.dto;

import com.sun.xml.registry.uddi.infomodel.EmailAddressImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReportDto {

        private String description;
        private String currency;
        private String responseCodeStatus;
        private String numberOfTransactions;
        private String transactionIdentifier;

    public static String getEmailAddress() {
        List<String> emails = new ArrayList<>();
        emails.add("trish.majoni@nbs.co.zw");
        emails.add("juliet.doka@nbs.co.zw");
        emails.add("glen.chiridza@nbs.co.zw");

        return emails.toString();
    }



}

