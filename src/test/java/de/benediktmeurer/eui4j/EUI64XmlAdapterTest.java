package de.benediktmeurer.eui4j;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit tests for the {@link EUI64XmlAdapter} class.
 * 
 * @author Benedikt Meurer
 * @see EUI64XmlAdapter
 */
public class EUI64XmlAdapterTest {
    @DataProvider(name = "names")
    public String[][] dataProviderNames() {
        return new String[][] {
                { null },
                { "00:00:00:00:00:00:00:00" },
                { "00:11:22:33:44:55:66:77" },
                { "00:00:00:11:11:11:11:00" },
                { "ff:ff:ff:ff:ff:ff:ff:ff" }
        };
    }

    @Test(dataProvider = "names")
    public void testMarshall(String name) throws Exception {
        assertEquals(new EUI64XmlAdapter().marshal(name == null ? null : EUI64.fromString(name)), name);
    }

    @Test(dataProvider = "names")
    public void testUnmarshall(String name) throws Exception {
        assertEquals(new EUI64XmlAdapter().unmarshal(name), name == null ? null : EUI64.fromString(name));
    }
}
