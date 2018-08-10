package au.com.normist.capital.repository.cap;

import au.com.normist.capital.domain.cap.CapBaseModel;
import au.com.normist.capital.repository.cap.dbutilsjpa.JpaQueryRunner;
import com.google.common.base.Preconditions;
import org.apache.commons.dbutils.QueryRunner;

import java.util.Collection;

public abstract class AdsRepository<T extends CapBaseModel> implements IRepository<T> {

    // The db column name of rowID
    private final String rowIdDbName = "ROWID";

    private AdsConnDriver adsConnDriver;

    // corresponding class for generic type T
    private Class<T> objClass;

    private QueryRunner queryRunner;
    private JpaQueryRunner jpaQueryRunner;

//    public AdsRepository() {}

    public AdsRepository (AdsConnDriver adsConnDriver, Class<T> objClass) {
        Preconditions.checkNotNull(adsConnDriver);
        Preconditions.checkNotNull(objClass);

        this.adsConnDriver = adsConnDriver;
        this.objClass = objClass;

        queryRunner = new QueryRunner();
        jpaQueryRunner = new JpaQueryRunner(queryRunner, adsConnDriver);
    }

    @Override
    public T getById(String id) {
        return jpaQueryRunner.query(objClass, id);
    }

    @Override
    public Collection<T> getByIds(Collection<String> ids) {
        return null;
    }

    @Override
    public Collection<T> getAll() {
        return null;
    }

    @Override
    public void update(T record) {

    }

    @Override
    public int delete(T record) {
        return 0;
    }

    @Override
    public int insert(T record) {
        return 0;
    }
}
