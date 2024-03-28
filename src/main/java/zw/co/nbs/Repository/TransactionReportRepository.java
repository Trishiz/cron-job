package zw.co.nbs.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.nbs.response.dto.TransactionReportDto;

public interface TransactionReportRepository extends JpaRepository<TransactionReportDto, String> {
}
