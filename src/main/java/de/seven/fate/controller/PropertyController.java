package de.seven.fate.controller;

import de.seven.fate.model.Property;
import de.seven.fate.service.PropertyService;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.inject.Model;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Mario on 27.04.2016.
 */
@Model
public class PropertyController {

    private Property property = new Property();

    @Inject
    private PropertyService service;


    public void save() {
        property.setId(StringUtils.trimToNull(property.getId()));

        service.saveProperty(property);
        property = new Property();
    }

    public void edit(String propertyId) {
        property = service.getProperty(propertyId);
    }

    public List<Property> getProperties() {
        return service.getProperties();
    }

    public Property getProperty() {
        return property;
    }

    public void delete(String propertyId) {
        service.deleteProperty(propertyId);
    }
}
