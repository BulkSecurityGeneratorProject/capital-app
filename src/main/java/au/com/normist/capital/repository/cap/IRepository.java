package au.com.normist.capital.repository.cap;

import au.com.normist.capital.domain.cap.CapBaseModel;

import java.util.Collection;

public interface IRepository<T extends CapBaseModel> {
    T getById(String id);

    Collection<T> getByIds(Collection<String> ids);

    Collection<T> getAll();

    void update(T record);

    int delete(T record);

    int insert(T record);

}
