/*-
 * Copyright 2012 Benedikt Meurer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.benediktmeurer.eui4j;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit tests for the {@link EUI48XmlAdapter} class.
 * 
 * @author Benedikt Meurer
 * @see EUI48XmlAdapter
 */
public class EUI48XmlAdapterTest {
    @DataProvider(name = "names")
    public String[][] dataProviderNames() {
        return new String[][] {
                { null },
                { "00:00:00:00:00:00" },
                { "00:11:22:33:44:55" },
                { "00:00:00:11:11:11" },
                { "ff:ff:ff:ff:ff:ff" }
        };
    }

    @Test(dataProvider = "names")
    public void testMarshall(String name) throws Exception {
        assertEquals(new EUI48XmlAdapter().marshal(name == null ? null : EUI48.fromString(name)), name);
    }

    @Test(dataProvider = "names")
    public void testUnmarshall(String name) throws Exception {
        assertEquals(new EUI48XmlAdapter().unmarshal(name), name == null ? null : EUI48.fromString(name));
    }
}
