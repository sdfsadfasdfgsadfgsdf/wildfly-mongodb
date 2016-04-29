package de.seven.fate.model;

import de.seven.fate.model.builder.PropertyBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Mario on 28.04.2016.
 */
public class PropertyTest {

    Property sut;

    PropertyBuilder builder = new PropertyBuilder();

    @Before
    public void setUp() throws Exception {
        sut = builder.random();
    }

    @Test
    public void testImplementation() throws Exception {
        Property.class.isAssignableFrom(IdAble.class);
    }

    @Test
    public void test() throws Exception {
        Assert.assertNull(sut.getId());

        Assert.assertNotNull(sut.getKey());
        Assert.assertNotNull(sut.getValue());
    }
}
