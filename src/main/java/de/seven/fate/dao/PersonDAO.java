package de.seven.fate.dao;

import de.seven.fate.model.Person;
import de.seven.fate.model.Property;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.inject.Inject;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mario on 28.04.2016.
 */
public class PersonDAO extends AbstractDAO<Person> {

    @Inject
    private PropertyDAO propertyDAO;

    @Override
    public Person get(Person person) {
        Validate.notNull(person);

        if (StringUtils.isNotBlank(person.getId())) {
            return super.get(person);
        }

        if (person.getAccountId() != null) {
            return getByAccountId(person.getAccountId());
        }

        return null;

    }

    @Override
    public void copyProperties(Person recent, Person entity) {
        super.copyProperties(recent, entity);
    }

    @Override
    public void save(Person property) {

        Optional.of(property.getPhones()).ifPresent(propertyDAO::save);
        super.save(property);
    }

    public Person getByAccountId(Long accountId) {
        Validate.notNull(accountId);

        Query query = em.createQuery("FROM Person p WHERE p.accountId = :accountId");
        query.setParameter("accountId", accountId);

        List<Person> resultList = query.getResultList();

        Iterator<Person> iterator = resultList.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }

        return null;
    }

    public List<Person> getAllByFirstName(String firstName) {
        Validate.notNull(firstName);

        Query query = em.createQuery("FROM Person p WHERE p.firstName = :firstName");
        query.setParameter("firstName", firstName);

        return query.getResultList();
    }

    public List<Person> getAllByLastName(String lastName) {
        Validate.notNull(lastName);

        Query query = em.createQuery("FROM Person p WHERE p.lastName = :lastName");
        query.setParameter("lastName", lastName);

        return query.getResultList();
    }

    public List<Person> getAllByPhone(Property phone) {
        Validate.notNull(phone);

        Query query = em.createQuery("FROM Person p WHERE :phone in (p.phones)");
        query.setParameter("phone", phone);

        return query.getResultList();
    }
}
