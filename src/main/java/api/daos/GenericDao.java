package api.daos;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, Id> {

    void save(T entity);

    Optional<T> read(Id id);

    void deleteById(Id id);

    List<T> findAll();

}
