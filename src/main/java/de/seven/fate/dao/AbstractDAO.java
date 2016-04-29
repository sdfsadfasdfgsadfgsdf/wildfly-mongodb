package de.seven.fate.dao;

import de.seven.fate.model.IdAble;
import de.seven.fate.util.ClassUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Mario on 27.04.2016.
 */
public class AbstractDAO<E extends IdAble> {

    private static final Logger logger = Logger.getLogger(AbstractDAO.class);

    @PersistenceContext(unitName = "mongo-ogm")
    protected EntityManager em;

    private Class<E> entityType;
    private String entityName;


    public void save(E property) {
        Validate.notNull(property);

        E recent = get(property);
        if (recent == null) {
            em.persist(property);
            return;
        } else if (recent.equals(property)) {
            return;
        }

        copyProperties(recent, property);

        em.merge(recent);
    }

    public void copyProperties(E recent, E entity) {
        Validate.notNull(recent);
        Validate.notNull(entity);

        try {
            BeanUtils.copyProperties(recent, entity);
        } catch (Exception e) {

            logger.warn("unable to update properties from: " + entity + " to " + recent);
            throw new IllegalStateException(e);
        }
    }

    public E get(E property) {
        Validate.notNull(property);

        return get(property.getId());
    }

    public List<E> list() {

        Query query = em.createQuery(String.format("FROM %s p", entityName));

        List<E> list = query.getResultList();

        return list;
    }

    public E get(String propertyId) {
        Validate.notNull(propertyId);

        return em.find(entityType, propertyId);
    }

    public void delete(String propertyId) {
        Validate.notNull(propertyId);

        delete(get(propertyId));
    }

    public void delete(E entity) {
        if (entity == null) {
            return;
        }

        em.remove(entity);
    }

    @PostConstruct
    private void init() {
        entityType = ClassUtil.getGenericType(getClass());
        entityName = entityType.getSimpleName();
    }

    public void deleteAll() {
        list().forEach(this::delete);
    }
}
