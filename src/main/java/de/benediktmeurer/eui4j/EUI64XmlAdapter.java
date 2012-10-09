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
