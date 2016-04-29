package de.seven.fate.model.builder;

import de.seven.fate.model.Person;

import javax.inject.Inject;

/**
 * Created by Mario on 28.04.2016.
 */
public class PersonBuilder extends AbstractModelBuilder<Person> {

    private final PropertyBuilder propertyBuilder;

    @Inject
    public PersonBuilder(PropertyBuilder propertyBuilder) {
        this.propertyBuilder = propertyBuilder;
    }

    @Override
    public Person min() {
        Person min = super.min();

        min.setId(null);
        min.setPhones(propertyBuilder.list());

        return min;
    }
}
