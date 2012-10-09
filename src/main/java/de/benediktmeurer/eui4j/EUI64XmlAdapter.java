package de.benediktmeurer.eui4j;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapts the {@link EUI64} type for custom marshalling using XML strings.
 * 
 * @author Benedikt Meurer
 * @see EUI64
 */
public class EUI64XmlAdapter extends XmlAdapter<String, EUI64> {
    /**
     * Converts the given {@link EUI64} value to its string representation. Returns {@code null } if
     * {@code val} is {@code null}.
     * 
     * @param val The EUI-64 value.
     * @return The string representation of {@code val}.
     * @see EUI64#toString()
     */
    @Override
    public String marshal(EUI64 val) throws Exception {
        return val == null ? null : val.toString();
    }

    /**
     * Converts the EUI-64 string representation in {@code str} to an {@link EUI64}. Returns
     * {@code null} if {@code str} is {@code null}.
     * 
     * @param str The EUI-64 string representation.
     * @return The {@link EUI64} represented by {@code str}.
     * @throws IllegalArgumentException if {@code str} is not a valid EUI-64 string representation.
     * @see EUI64#fromString(String)
     */
    @Override
    public EUI64 unmarshal(String str) throws Exception {
        return str == null ? null : EUI64.fromString(str);
    }
}
