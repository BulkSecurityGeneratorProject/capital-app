package au.com.normist.capital;

import au.com.normist.capital.config.ApplicationProperties;
import au.com.normist.capital.config.DefaultProfileUtil;
import io.github.jhipster.config.JHipsterConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collection;

import static java.lang.System.getProperty;
import static java.nio.file.Files.createTempDirectory;

@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class CapitalappApp {

    private static final Logger log = LoggerFactory.getLogger(CapitalappApp.class);

    private static final String DUMP_CLASSES_PROP = "jdk.internal.lambda.dumpProxyClasses";

    private final Environment env;

    public CapitalappApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes capitalapp.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        init();
        SpringApplication app = new SpringApplication(CapitalappApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}\n\t" +
                "External: \t{}://{}:{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            protocol,
            hostAddress,
            env.getProperty("server.port"),
            env.getActiveProfiles());
    }

    private static void init() {
        /* Initializes the jdk.internal.lambda.dumpProxyClasses system property with a temporary directory.
         * See https://bugs.openjdk.java.net/browse/JDK-8023524
         */
        try {
            if (getProperty(DUMP_CLASSES_PROP) == null)
                System.setProperty(DUMP_CLASSES_PROP, createTempDirectory("lambda").toString());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
