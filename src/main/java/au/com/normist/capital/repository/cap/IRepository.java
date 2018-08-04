package au.com.normist.capital.repository.cap;

import au.com.normist.capital.domain.cap.CapBaseModel;

import java.util.Collection;

public interface IRepository<T extends CapBaseModel> {
    T GetById(String id);

    Collection<T> GetByIds(Collection<String> ids);

    Collection<T> GetAll();

    void Update(T record);

    int Delete(T record);

    int Insert(T record);

}
