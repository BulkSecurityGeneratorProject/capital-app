package au.com.normist.capital.repository.cap.dbutilsjpa;

import au.com.normist.capital.repository.cap.AdsConnDriver;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a JPA-friendly interface to the underlying QueryRunner.
 *
 * Immutable and thread-safe.
 */
public class JpaQueryRunner {
    private static final Logger log = LoggerFactory.getLogger(JpaQueryRunner.class);

    public static final ScalarHandler<Long> DEFAULT_GENERATED_KEYS_HANDLER = new ScalarHandler<>();
    public static final SqlWriter DEFAULT_SQL_WRITER = new SqlWriter();
    public static final BasicRowProcessor DEFAULT_ROW_PROCESSOR = new BasicRowProcessor(new JpaBeanProcessor());
    public static final NewEntityTester DEFAULT_ENTITY_TESTER = new NewEntityTester() {
        @Override
        public boolean isNew(Object entity) {
            AccessibleObject idAccessor = Entities.getIdAccessor(entity.getClass());
            try {
                if (idAccessor instanceof Field) {
                    Field field = (Field) idAccessor;

                    return field.get(entity) == null;
                } else {
                    return ((Method) idAccessor).invoke(entity) == null;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    };


    private final QueryRunner queryRunner;
    private final SqlWriter sqlWriter;
    private final NewEntityTester entityTester;
    private final RowProcessor rowProcessor;
    private final ResultSetHandler<?> generatedKeysHandler;

    private AdsConnDriver adsConnDriver;

    public static class Builder {

        private SqlWriter sqlWriter;
        private NewEntityTester entityTester;
        private ResultSetHandler<?> generatedKeysHandler;
        private RowProcessor rowProcessor;
        private AdsConnDriver adsConnDriver;

        public JpaQueryRunner build(QueryRunner queryRunner) {
            return new JpaQueryRunner(queryRunner,
                choose(this.sqlWriter, DEFAULT_SQL_WRITER),
                choose(this.entityTester, DEFAULT_ENTITY_TESTER),
                choose(this.rowProcessor, DEFAULT_ROW_PROCESSOR),
                choose(this.generatedKeysHandler, DEFAULT_GENERATED_KEYS_HANDLER),
                this.adsConnDriver
            );
        }

        public Builder sqlWriter(SqlWriter sqlWriter) {
            this.sqlWriter = sqlWriter;
            return this;
        }

        public Builder entityTester(NewEntityTester entityTester) {
            this.entityTester = entityTester;
            return this;
        }

        public Builder generatedKeysHandler(ResultSetHandler<?> generatedKeysHandler) {
            this.generatedKeysHandler = generatedKeysHandler;
            return this;
        }

        public Builder rowProcessor(RowProcessor rowProcessor) {
            this.rowProcessor = rowProcessor;
            return this;
        }

        public Builder adsConnDriver(AdsConnDriver adsConnDriver) {
            this.adsConnDriver = adsConnDriver;
            return this;
        }

        private <T> T choose(T value, T fallback) {
            return value != null ? value : fallback;
        }
    }

    public JpaQueryRunner(QueryRunner queryRunner) {
        this(queryRunner, DEFAULT_SQL_WRITER, DEFAULT_ENTITY_TESTER, DEFAULT_ROW_PROCESSOR, DEFAULT_GENERATED_KEYS_HANDLER, null);
    }

    public JpaQueryRunner(QueryRunner queryRunner, AdsConnDriver adsConnDriver) {
        this(queryRunner, DEFAULT_SQL_WRITER, DEFAULT_ENTITY_TESTER, DEFAULT_ROW_PROCESSOR, DEFAULT_GENERATED_KEYS_HANDLER, adsConnDriver);
    }

    public JpaQueryRunner(QueryRunner queryRunner, SqlWriter sqlWriter, NewEntityTester entityTester,
                          RowProcessor rowProcessor, ResultSetHandler<?> generatedKeysHandler, AdsConnDriver adsConnDriver) {
        this.queryRunner = queryRunner;
        this.sqlWriter = sqlWriter;
        this.entityTester = entityTester;
        this.rowProcessor = rowProcessor;
        this.generatedKeysHandler = generatedKeysHandler;
        this.adsConnDriver = adsConnDriver;
    }

    /**
     * Find by primary key. Search for an entity of the specified class and
     * primary key. If the entity instance is contained in the persistence
     * context, it is returned from there.
     *
     * @param entityClass
     *          entity class
     * @param primaryKey
     *          primary key
     * @return the found entity instance or null if the entity does not exist
     * @throws IllegalArgumentException
     *           if the first argument does not denote an entity type or the
     *           second argument is is not a valid type for that entity&apos;s
     *           primary key or is null
     */
    public <T> T query(Class<T> entityClass, Object primaryKey) {
        Connection conn = null;
        try {
            conn = adsConnDriver.getDbConnection();
            return entityClass.cast(
                queryRunner.query(conn, sqlWriter.selectById(entityClass), new BeanHandler<>(entityClass, rowProcessor), primaryKey)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * Insert if new, update if already exists.
     *
     * @param entity
     * @return The number of rows updated
     */
    public int save(Object entity) {
        try {
            Class<? extends Object> entityClass = entity.getClass();
            boolean isNew = entityTester.isNew(entity);

            AccessibleObject idAccessor = Entities.getIdAccessor(entityClass);
            List<PropertyDescriptorWrapper> relevantPropertyDescriptors = new ArrayList<>();
            PropertyDescriptorWrapper idPropertyDescriptor = null;
            PropertyDescriptorWrapper[] propertyDescriptorWrappers = null;
            if (idAccessor instanceof Method) {
                propertyDescriptorWrappers = PropertyDescriptorWrapper.getPropertyDescriptorsFromMethods(entityClass);
            } else {
                propertyDescriptorWrappers = PropertyDescriptorWrapper.getPropertyDescriptorsFromFields(entityClass);
            }

            for (PropertyDescriptorWrapper propertyDescriptorWrapper : propertyDescriptorWrappers) {
                AccessibleObject accessibleObject = propertyDescriptorWrapper.getAccessibleObject();
                Member member = propertyDescriptorWrapper.getMember();

                if (!Entities.isMapped(member.getDeclaringClass()) || Entities.isRelation(accessibleObject)) {
                    continue;
                }

                if (Entities.isIdAccessor(accessibleObject)) {
                    idPropertyDescriptor = propertyDescriptorWrapper;
                    continue;
                }

                if (isNotSettable(isNew, accessibleObject)) {
                    continue;
                }

                relevantPropertyDescriptors.add(propertyDescriptorWrapper);
            }

            if (!isNew) {
                relevantPropertyDescriptors.add(idPropertyDescriptor);
            }

            Object[] args = new Object[relevantPropertyDescriptors.size()];
            for (int i = 0; i < args.length; i++) {
                PropertyDescriptorWrapper propertyDescriptor = relevantPropertyDescriptors.get(i);
                args[i] = propertyDescriptor.get(entity);
                if (args[i] != null && Enum.class.isAssignableFrom(args[i].getClass())) {
                    args[i] = args[i].toString();
                }
            }

            if (isNew) {
                Object newId = queryRunner.insert(sqlWriter.insert(entityClass), generatedKeysHandler, args);
                idPropertyDescriptor.set(entity, newId);

                return 1;
            } else {
                return queryRunner.update(sqlWriter.updateById(entityClass), args);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(Class<?> entityClass, Object primaryKey) {
        try {
            return queryRunner.update(sqlWriter.deleteById(entityClass), primaryKey);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isNotSettable(boolean isNew, AccessibleObject accessibleObject) {
        return (isNew && accessibleObject.isAnnotationPresent(Column.class) && !accessibleObject.getAnnotation(Column.class).insertable()) || (!isNew && accessibleObject.isAnnotationPresent(Column.class) && !accessibleObject.getAnnotation(Column.class).updatable());
    }

    public AdsConnDriver getAdsConnDriver() {
        return adsConnDriver;
    }

    public void setAdsConnDriver(AdsConnDriver adsConnDriver) {
        this.adsConnDriver = adsConnDriver;
    }

}
