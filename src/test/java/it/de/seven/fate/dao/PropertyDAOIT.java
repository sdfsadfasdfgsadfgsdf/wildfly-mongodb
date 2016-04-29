package it.de.seven.fate.dao;

import com.mongodb.Mongo;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import de.seven.fate.dao.PropertyDAO;
import de.seven.fate.model.Property;
import de.seven.fate.model.builder.PropertyBuilder;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(Arquillian.class)
public class PropertyDAOIT {


    @Inject
    PropertyDAO sut;

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction transaction;

    @Inject
    PropertyBuilder builder;

    Property model;

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
    public void getByKey() throws Exception {
        assertNotNull(sut.getByKey(model.getKey()));
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
