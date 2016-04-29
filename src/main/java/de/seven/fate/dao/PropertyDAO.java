package de.seven.fate.dao;

import de.seven.fate.model.Property;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mario on 27.04.2016.
 */
public class PropertyDAO extends AbstractDAO<Property> {


    @Override
    public Property get(Property property) {
        Validate.notNull(property);

        if (StringUtils.isNotBlank(property.getId())) {
            return get(property.getId());
        }

        return getByKey(property.getKey());
    }

    public Property getByKey(String propertyKey) {
        Validate.notNull(propertyKey);

        Query query = em.createQuery("FROM Property p WHERE p.key = :key");
        query.setParameter("key", propertyKey);

        List<Property> resultList = query.getResultList();

        Iterator<Property> iterator = resultList.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }

        return null;
    }
}
