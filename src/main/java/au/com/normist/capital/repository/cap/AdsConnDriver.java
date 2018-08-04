package au.com.normist.capital.repository.cap;

import au.com.normist.capital.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Connection;

import static java.sql.DriverManager.getConnection;

@Component("AdsConnDriver")
public class AdsConnDriver implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(AdsConnDriver.class);

    private final ApplicationProperties applicationProperties;

    private String connectionString;


    public AdsConnDriver(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        connectionString = this.applicationProperties.getCapital().getConnectionString();
    }

    public Connection getDbConnection() {
        Connection connection = null;
        try {
            Class.forName("com.extendedsystems.jdbc.advantage.ADSDriver");
            connection = getConnection(connectionString);
        } catch (Exception e) {
            log.error("unable to get connection to Capital database: ", e);
        }

        if (connection == null) {
            log.error("Failed to get a ADS connection");
        }

        return connection;
    }


    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }


}
