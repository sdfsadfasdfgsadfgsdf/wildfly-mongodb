package it.de.seven.fate.dao;

import de.seven.fate.dao.PersonDAO;
import de.seven.fate.model.Person;
import de.seven.fate.model.builder.PersonBuilder;
import it.de.seven.fate.util.DeploymentUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(Arquillian.class)
public class PersonDAOIT {


    @Inject
    PersonDAO sut;

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction transaction;

    @Inject
    PersonBuilder builder;

    Person model;

    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentUtil.createDeployment();
    }

    @Before
    public void setUp() throws Exception {
        model = builder.random();
        transactional(() -> sut.save(model));
    }

    @After
    public void tearDown() throws Exception {
        transactional(() -> sut.deleteAll());
    }

    @Test
    public void get() throws Exception {
        assertNotNull(sut.get(model));
    }

    @Test
    public void getByAccountId() throws Exception {
        assertNotNull(sut.getByAccountId(model.getAccountId()));
    }

    @Test
    public void getByAccountIdOnIdNull() throws Exception {
        model.setId(null);
        assertNotNull(sut.get(model));
    }

    @Test
    public void getAllByFirstName() throws Exception {
        assertNotNull(sut.getAllByFirstName(model.getFirstName()));
    }

    @Test
    public void getAllWhereFirstName() throws Exception {
        assertNotNull(sut.getAllByLastName(model.getFirstName().substring(0, 5)));
    }

    @Test
    public void getAllByLastName() throws Exception {
        assertNotNull(sut.getAllByLastName(model.getLastName()));
    }

    @Test
    public void getAllWhereLastName() throws Exception {
        assertNotNull(sut.getAllByLastName(model.getLastName().substring(0, 5)));
    }

    @Test
    public void getAllByPhone() throws Exception {
        assertNotNull(sut.getAllByPhone(model.getPhones().stream().findAny().get()));
    }

    @Test
    public void list() throws Exception {
        assertFalse(sut.list().isEmpty());
    }

    private void transactional(Runnable runnable) throws Exception {
        transaction.begin();
        em.joinTransaction();

        runnable.run();

        transaction.commit();
    }

}
