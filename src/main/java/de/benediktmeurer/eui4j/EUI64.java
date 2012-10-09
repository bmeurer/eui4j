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

import java.io.Serializable;

/**
 * A class that represents an immutable 64-bit extended unique identifier (EUI-64).
 * 
 * @author Benedikt Meurer
 * @see EUI48
 */
public final class EUI64 implements Comparable<EUI64>, Serializable {
    /** The serial version UID of this class. */
    private static final long serialVersionUID = 1L;

    /** The bit representation. */
    private final long bits;

    /**
     * Constructs a new {@link EUI64} using the given {@code bits}.
     * 
     * @param bits The bit representation.
     * @see #getBits()
     */
    public EUI64(long bits) {
        this.bits = bits;
    }

    /**
     * Constructs a new {@link EUI64} using the given {@code octets}.
     * 
     * @param octets The octet representation in transmission order.
     * @throws IllegalArgumentException If {@code octets} does not have exactly {@code 8} elements.
     * @throws NullPointerException If {@code octets} is {@code null}.
     * @see #getOctets()
     */
    public EUI64(byte[] octets) {
        if (octets.length != 8) {
            throw new IllegalArgumentException("octets is of illegal length " + octets.length);
        }
        this.bits = ((long) octets[0] << 56)
                    | ((long) octets[1] << 48)
                    | ((long) octets[2] << 40)
                    | ((long) octets[3] << 32)
                    | ((long) octets[4] << 24)
                    | ((long) octets[5] << 16)
                    | ((long) octets[6] << 8)
                    | octets[7];
    }

    /**
     * Returns the bit representation of this {@link EUI64}.
     * 
     * @return The bit representation of this EUI-64.
     * @see #EUI64(long)
     */
    public long getBits() {
        return this.bits;
    }

    /**
     * Returns the octet representation of this {@link EUI64} in transmission order.
     * 
     * @return The octect representation in transmission order.
     * @see #EUI64(byte[])
     */
    public byte[] getOctets() {
        byte[] octets = new byte[8];
        long bits = this.bits;
        for (int n = 8; --n >= 0; bits >>= 8) {
            octets[n] = (byte) (bits & 0xff);
        }
        return octets;
    }

    /**
     * Compares this {@link EUI64} with the specified {@link EUI64} based on their bit
     * representations.
     * 
     * @param val {@code EUI64} to which this {@code EUI64} should be compared.
     * @return {@code -1}, {@code 0} or {@code 1} if this {@link EUI64} is less than, equal to or
     *         greater than {@code val}.
     * @throws NullPointerException if {@code val} is {@code null}.
     */
    public int compareTo(EUI64 val) {
        if (this.bits < val.bits) {
            return -1;
        }
        else if (this.bits > val.bits) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Creates a {@link EUI64} from a string standard representation in {@code name}. The standard
     * representation is eight groups of two hexadecimal digits, separated by hyphens ({@code -}) or
     * colons ({@code :}), in transmission order.
     * 
     * @param name The string representation of of an EUI-64.
     * @return The EUI-64 represented by the specified {@code name}.
     * @throws IllegalArgumentException if {@code name} is not a valid string representation of an
     *             EUI-64.
     * @throws NullPointerException if {@code name} is {@code null}.
     * @see #toString()
     */
    public static EUI64 fromString(String name) {
        long bits = 0;
        char sep = 0;
        for (int n = 0;; ++n) {
            if (n == name.length()) {
                if (n == 23) {
                    return new EUI64(bits);
                }
                else {
                    break;
                }
            }
            char c = name.charAt(n);
            if (n == 2) {
                if (c != ':' && c != '-') {
                    break;
                }
                sep = c;
            }
            else if ((n - 2) % 3 == 0) {
                if (c != sep) {
                    break;
                }
            }
            else if (c >= '0' && c <= '9') {
                bits = (bits << 4) | (c - '0');
            }
            else if (c >= 'a' && c <= 'f') {
                bits = (bits << 4) | (10 + c - 'a');
            }
            else if (c >= 'A' && c <= 'F') {
                bits = (bits << 4) | (10 + c - 'A');
            }
            else {
                break;
            }
        }
        throw new IllegalArgumentException("Invalid EUI-64 string: " + name);
    }

    /**
     * Compares this {@link EUI64} to the specified object. The result is {@code true} if and only
     * if {@code obj} is not {@code null} and contains the same bits as this EUI-64.
     * 
     * @param obj The object to be compared.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj instanceof EUI64) {
            return ((EUI64) obj).bits == this.bits;
        }
        return false;
    }

    /**
     * Returns a hash code for this EUI-64.
     * 
     * @return A hash code value for this {@code EUI64}.
     */
    @Override
    public int hashCode() {
        return (int) (this.bits >> 32) ^ (int) this.bits;
    }

    /**
     * Returns the string representation of this {@link EUI64} in human-friendly form, composed of
     * eight groups of two hexadecimal digits, separated by colons ({@code :}), in transmission
     * order.
     * 
     * @return The string representation of this EUI-64.
     * @see #fromString(String)
     */
    @Override
    public String toString() {
        char[] value = new char[23];
        long bits = this.bits;
        for (int n = value.length; --n >= 0;) {
            if ((n - 2) % 3 == 0) {
                value[n] = ':';
            }
            else {
                int c = (int) bits & 0xf;
                value[n] = (char) (c + (c < 10 ? '0' : ('a' - 10)));
                bits >>= 4;
            }
        }
        return new String(value);
    }
}
