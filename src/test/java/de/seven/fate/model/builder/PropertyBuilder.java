package de.seven.fate.model.builder;

import de.seven.fate.model.Property;
import de.seven.fate.model.builder.AbstractModelBuilder;
import de.seven.fate.model.builder.ModelBuilder;

/**
 * Created by Mario on 28.04.2016.
 */
public class PropertyBuilder extends AbstractModelBuilder<Property> {

    @Override
    public Property min() {
        Property min = super.min();

        min.setId(null);

        return min;
    }
}
