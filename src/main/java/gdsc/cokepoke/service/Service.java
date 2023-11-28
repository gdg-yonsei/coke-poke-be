package gdsc.cokepoke.service;

import gdsc.cokepoke.domain.DomainObject;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

abstract public class Service {
    abstract protected JpaRepository<DomainObject, Object> getRepository();

    public void save(final DomainObject obj) {
        getRepository().saveAndFlush(obj);
    }

    public List<? extends DomainObject> findAll() {
        return getRepository().findAll();
    }

    public void saveAll(final List<? extends DomainObject> objects) {
        getRepository().saveAll(objects);
        getRepository().flush();
    }

    public void delete(final DomainObject obj) {
        getRepository().delete(obj);
    }

    public void deleteAll() {
        getRepository().deleteAll();
    }

    public long count() {
        return getRepository().count();
    }

    protected List<? extends DomainObject> findBy(final Example<DomainObject> example) {
        return getRepository().findAll(example);
    }

    public boolean existsById(final Object id) {
        return getRepository().existsById(id);
    }

    public DomainObject findById(final Object id) {
        if (null == id) {
            return null;
        }
        final Optional<DomainObject> res = getRepository().findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        return null;
    }
}
