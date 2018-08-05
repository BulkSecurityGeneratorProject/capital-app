package au.com.normist.capital.repository.cap.salesforce;

import au.com.normist.capital.domain.cap.salesforce.CrmHistory;
import au.com.normist.capital.repository.cap.AdsConnDriver;
import au.com.normist.capital.repository.cap.AdsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
@DependsOn("AdsConnDriver")
public class CrmHistoryAdsRepository extends AdsRepository<CrmHistory> implements ICrmHistoryRepository {
    private static final Logger log = LoggerFactory.getLogger(CrmHistoryAdsRepository.class);

//    public CrmHistoryAdsRepository() {
//    }

    public CrmHistoryAdsRepository(AdsConnDriver adsConnDriver) {
        super(adsConnDriver);

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = adsConnDriver.getDbConnection();
            statement = connection.createStatement();
            resultSet = statement
                .executeQuery("SELECT STOCKID FROM CRMHIST");
            while (resultSet.next()) {
                log.info("*************** stockid: " + resultSet.getString("STOCKID"));
                System.out.println("********* stockid:"
                    + resultSet.getString("STOCKID"));
                break;
            }
        } catch (Exception e) {
            log.error("exception: ", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }


}
