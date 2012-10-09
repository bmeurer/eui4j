package de.benediktmeurer.eui4j;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapts the {@link EUI48} type for custom marshalling using XML strings.
 * 
 * @author Benedikt Meurer
 * @see EUI48
 */
public class EUI48XmlAdapter extends XmlAdapter<String, EUI48> {
    /**
     * Converts the given {@link EUI48} value to its string representation. Returns {@code null } if
     * {@code val} is {@code null}.
     * 
     * @param val The EUI-48 value.
     * @return The string representation of {@code val}.
     * @see EUI48#toString()
     */
    @Override
    public String marshal(EUI48 val) throws Exception {
        return val == null ? null : val.toString();
    }

    /**
     * Converts the EUI-48 string representation in {@code str} to an {@link EUI48}. Returns
     * {@code null} if {@code str} is {@code null}.
     * 
     * @param str The EUI-48 string representation.
     * @return The {@link EUI48} represented by {@code str}.
     * @throws IllegalArgumentException if {@code str} is not a valid EUI-48 string representation.
     * @see EUI48#fromString(String)
     */
    @Override
    public EUI48 unmarshal(String str) throws Exception {
        return str == null ? null : EUI48.fromString(str);
    }
}
