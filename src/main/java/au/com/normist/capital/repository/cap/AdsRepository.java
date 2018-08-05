package au.com.normist.capital.repository.cap;

import au.com.normist.capital.domain.cap.CapBaseModel;

import java.util.Collection;

public abstract class AdsRepository<T extends CapBaseModel> implements IRepository<T> {

    // The db column name of rowID
    private final String rowIdDbName = "ROWID";

    private AdsConnDriver adsConnDriver;

    public AdsRepository() {}

    public AdsRepository (AdsConnDriver adsConnDriver) {
        this.adsConnDriver = adsConnDriver;
    }


    @Override
    public T GetById(String id) {
        return null;
    }

    @Override
    public Collection<T> GetByIds(Collection<String> ids) {
        return null;
    }

    @Override
    public Collection<T> GetAll() {
        return null;
    }

    @Override
    public void Update(T record) {

    }

    @Override
    public int Delete(T record) {
        return 0;
    }

    @Override
    public int Insert(T record) {
        return 0;
    }
}
