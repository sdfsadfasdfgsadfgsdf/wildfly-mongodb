package de.seven.fate.service;

import de.seven.fate.dao.PropertyDAO;
import de.seven.fate.model.Property;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Mario on 27.04.2016.
 */
@Stateless
public class PropertyService {

    @Inject
    PropertyDAO dao;

    public List<Property> getProperties() {

        return dao.list();
    }

    public void saveProperty(Property property) {
        dao.save(property);
    }

    public Property getProperty(String propertyId) {
        return dao.get(propertyId);
    }

    public void deleteProperty(String propertyId) {
        dao.delete(propertyId);
    }
}
