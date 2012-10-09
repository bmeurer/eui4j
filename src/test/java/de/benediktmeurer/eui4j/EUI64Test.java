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
import static org.testng.Assert.assertNotEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit tests for the {@link EUI64} class.
 * 
 * @author Benedikt Meurer
 * @see EUI64
 */
public class EUI64Test {
    @DataProvider(name = "bits")
    public Long[][] dataProviderBits() {
        return new Long[][] {
                { 0L },
                { -1L },
                { 0xffff0000ffff0000L },
                { 0xf0f0f0f0f0f0f0f0L },
                { 0x0123456789abcdefL }
        };
    }

    @DataProvider(name = "bitsAndEUI64Names")
    public Object[][] dataProviderBitsAndEUI64Names() {
        return new Object[][] {
                { 0L, "00:00:00:00:00:00:00:00" },
                { 0x1122334455667788L, "11:22:33:44:55:66:77:88" },
                { 0xffffffffffffffffL, "ff:ff:ff:ff:ff:ff:ff:ff" }
        };
    }

    @DataProvider(name = "bitsAndAlternateNames")
    public Object[][] dataProviderBitsAndAlternateNames() {
        return new Object[][] {
                { 0x9876543210L, "00-00-00-98-76-54-32-10" },
                { -1L, "ff-FF-ff-FF-ff-FF-ff-FF" }
        };
    }

    @DataProvider(name = "bitsAndOctets")
    public Object[][] dataProviderBitsAndOctets() {
        return new Object[][] {
                { 0x1122334455667711L, new byte[] { 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x11 } },
                { 0L, new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 } }
        };
    }

    @DataProvider(name = "invalidEUI64Names")
    public String[][] dataProviderInvalidEUI64Names() {
        return new String[][] {
                { "invalid" },
                { "111:22:33:44:55:66:77:88" },
                { "11-22:33-44:55-66:77-88" },
                { "ik:01:23:45:67:89:10:11" },
                { "11:22:33:44:55:66:77:88:99:00" },
                { "ff:ff:ff" }
        };
    }

    @Test(dataProvider = "bitsAndOctets")
    public void testGetOctets(long bits, byte[] octets) {
        assertEquals(new EUI64(bits).getOctets(), octets);
    }

    @Test(dataProvider = "bitsAndOctets")
    public void testConstructorWithOctets(long bits, byte[] octets) {
        assertEquals(new EUI64(octets).getBits(), bits);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testConstructorWithNullOctets() {
        new EUI64(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWithEmptyOctets() {
        new EUI64(new byte[0]);
    }

    @Test(dataProvider = "bits")
    public void testCompareTo(long bits) {
        assertEquals(new EUI64(bits).compareTo(new EUI64(bits)), 0);
    }

    @Test(dataProvider = "bits")
    public void testCompareToInverted(long bits) {
        assertNotEquals(new EUI64(bits).compareTo(new EUI64(~bits)), 0);
    }

    @Test(dataProvider = "bits")
    public void testHashCode(long bits) {
        assertEquals(new EUI64(bits).hashCode(), new EUI64(bits).hashCode());
    }

    @Test(dataProvider = "bits")
    public void testEquals(long bits) {
        assertEquals(new EUI64(bits), new EUI64(bits));
    }

    @Test(dataProvider = "bits")
    public void testEqualsInverted(long bits) {
        assertNotEquals(new EUI64(bits), new EUI64(~bits));
    }

    @Test(dataProvider = "bitsAndEUI64Names")
    public void testToString(long bits, String name) {
        assertEquals(new EUI64(bits).toString(), name);
    }

    @Test(dataProvider = "bitsAndEUI64Names")
    public void testFromString(long bits, String name) {
        assertEquals(EUI64.fromString(name).getBits(), bits);
    }

    @Test(dataProvider = "bitsAndAlternateNames")
    public void testFromStringAlternate(long bits, String name) {
        assertEquals(EUI64.fromString(name).getBits(), bits);
    }

    @Test(dataProvider = "bits")
    public void testFromStringToString(long bits) {
        assertEquals(EUI64.fromString(new EUI64(bits).toString()).getBits(), bits);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testFromStringWithNull() {
        EUI64.fromString(null);
    }

    @Test(dataProvider = "invalidEUI64Names", expectedExceptions = IllegalArgumentException.class)
    public void testFromStringWithInvalidName(String name) {
        EUI64.fromString(name);
    }
}
