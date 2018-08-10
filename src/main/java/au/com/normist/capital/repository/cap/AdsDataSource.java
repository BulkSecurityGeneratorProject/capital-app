package au.com.normist.capital.repository.cap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component("AdsDataSource")
//@DependsOn("LambdaToAdsSql")
public class AdsDataSource {
    private static final Logger log = LoggerFactory.getLogger(AdsDataSource.class);

//    private static HikariConfig config = new HikariConfig();
//
//    private static HikariDataSource ds;
//
//
//    static {
//        try {
//            Class.forName("com.extendedsystems.jdbc.advantage.ADSDriver");
//        } catch (ClassNotFoundException e) {
//            log.error("error in ADS datasource: ", e);
//        }
//
//        config.setJdbcUrl("");
//        config.setUsername("");
//        config.setPassword("");
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//        ds = new HikariDataSource(config);
//    }
//
//
//    public static Connection getConnection() throws SQLException {
//        return ds.getConnection();
//    }
//
//    public static DataSource getDataSource() {
//        return ds;
//    }
}
