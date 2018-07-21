package au.com.normist.capital.repository;

import au.com.normist.capital.domain.ReportGeneration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReportGeneration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportGenerationRepository extends JpaRepository<ReportGeneration, Long>, JpaSpecificationExecutor<ReportGeneration> {

}
