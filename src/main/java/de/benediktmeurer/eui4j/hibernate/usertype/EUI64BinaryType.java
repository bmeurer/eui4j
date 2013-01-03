package de.benediktmeurer.eui4j.hibernate.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import de.benediktmeurer.eui4j.EUI64;

/**
 * Maps an {@link EUI64} to a JDBC BINARY.
 * 
 * @author Benedikt Meurer
 * @see EUI64
 */
public class EUI64BinaryType implements UserType {
    /**
     * @see UserType#sqlTypes()
     */
    @Override
    public int[] sqlTypes() {
        return new int[] { Types.BINARY };
    }

    /**
     * @see UserType#returnedClass()
     */
    @Override
    public Class<?> returnedClass() {
        return EUI64.class;
    }

    /**
     * @see UserType#equals(Object, Object)
     */
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return (x == null ? y == null : x.equals(y));
    }

    /**
     * @see UserType#hashCode(Object)
     */
    @Override
    public int hashCode(Object x) throws HibernateException {
        return (x == null ? 0 : x.hashCode());
    }

    /**
     * @see UserType#nullSafeGet(ResultSet, String[], SessionImplementor, Object)
     */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        byte[] octets = rs.getBytes(names[0]);
        return (rs.wasNull() ? null : new EUI64(octets));
    }

    /**
     * @see UserType#nullSafeSet(PreparedStatement, Object, int, SessionImplementor)
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value != null) {
            st.setBytes(index, ((EUI64) value).getOctets());
        }
        else {
            st.setNull(index, sqlTypes()[0]);
        }
    }

    /**
     * @see UserType#deepCopy(Object)
     */
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    /**
     * @see UserType#isMutable()
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /**
     * @see UserType#disassemble(Object)
     */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    /**
     * @see UserType#assemble(Serializable, Object)
     */
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    /**
     * @see UserType#replace(Object, Object, Object)
     */
    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
