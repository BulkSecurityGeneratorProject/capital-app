package au.com.normist.capital.repository.cap.lambda2sql;

import com.trigersoft.jaque.expression.LambdaExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Predicate;

import static java.lang.System.getProperty;
import static java.nio.file.Files.createTempDirectory;
import static java.util.Objects.requireNonNull;

/**
 * A class for converting java lambdas to SQL.
 * It must be initialized before any lambdas are created.
 */
@Component("LambdaToAdsSql")
public class LambdaToAdsSql {
    private static final String DUMP_CLASSES_PROP = "jdk.internal.lambda.dumpProxyClasses";

    static {
        init();
    }

    /**
     * Initializes the jdk.internal.lambda.dumpProxyClasses system property with a temporary directory.
     * See https://bugs.openjdk.java.net/browse/JDK-8023524
     */
    public static void init() {
        try {
            if (getProperty(DUMP_CLASSES_PROP) == null)
                System.setProperty(DUMP_CLASSES_PROP, createTempDirectory("lambda").toString());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * NOTE: dump class property must be initialized before any predicate created
     * Converts a predicate lambda to ADS SQL. <br/>
     * <pre>{@code person -> person.getAge() > 50 && person.isActive() }</pre>
     * Becomes a string:
     * <pre>{@code "age > 50 AND active" }</pre>
     * Supported operators: >,>=,<,<=,=,!=,&&,||,!
     */
    public static <T> String convertToAdsSql(Predicate<T> predicate) {
        requireNonNull(getProperty(DUMP_CLASSES_PROP), "Call init() before creating the predicate.");
        LambdaExpression<Predicate<T>> lambdaExpression = LambdaExpression.parse(predicate);
        return lambdaExpression.accept(new ToAdsSqlVisitor()).toString();
    }

}
