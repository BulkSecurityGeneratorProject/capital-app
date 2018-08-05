package au.com.normist.capital.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Capitalapp.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {


    private final CapitalConfig capital = new CapitalConfig();

    public static class CapitalConfig {
        public String connectionString;

        public String getConnectionString() {
            return connectionString;
        }

        public void setConnectionString(String connectionString) {
            this.connectionString = connectionString;
        }

    }

    public CapitalConfig getCapital() {
        return capital;
    }
}
