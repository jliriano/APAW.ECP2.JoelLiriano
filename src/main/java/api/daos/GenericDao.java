package api.daos;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, I> {

    void save(T entity);

    Optional<T> read(I id);

    void deleteById(I id);

    List<T> findAll();

}
