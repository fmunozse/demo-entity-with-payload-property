package cloud.fmunozse.demoentitywithpayloadproperty.model.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneCompositeType implements CompositeUserType {
    @Override
    public String[] getPropertyNames() {
        return new String[]{"phoneNum", "areaCode"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                org.hibernate.type.StandardBasicTypes.STRING,
                org.hibernate.type.StandardBasicTypes.INTEGER
        };
    }


    @Override
    public Object getPropertyValue(Object o, int i)
            throws HibernateException {
        if (o == null) {
            return null;
        } else if (i == 0) {
            return ((Phone) o).getPhoneNum();
        } else if (i == 1) {
            return ((Phone) o).getAreaCode();
        }
        return null;
    }

    @Override
    public void setPropertyValue(Object o, int i, Object o1)
            throws HibernateException {
        if (o != null) {
            if (i == 0) {
                ((Phone) o).setPhoneNum((String) o1);
            } else if (i == 1 && o1 != null) {
                ((Phone) o).setAreaCode((Integer) o1);
            }
        }
    }

    @Override
    public Class returnedClass() {
        return Phone.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o == null && o1 == null) {
            return true;
        }
        if (o == null || o1 == null) {
            return false;
        }
        return o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        if (o != null) {
            return o.hashCode();
        }
        return 0;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] strings,
                              SessionImplementor si, Object o) throws HibernateException, SQLException {
        String phoneNum = rs.getString(strings[0]);
        String areaCode = rs.getString(strings[1]);
        if (phoneNum != null && areaCode != null) {
            return new Phone(Integer.valueOf(areaCode), phoneNum);
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object o, int i, SessionImplementor si) throws HibernateException, SQLException {
        if (o != null) {
            ps.setString(i, ((Phone) o).getPhoneNum());
            if (((Phone) o).getAreaCode() != null) {
                ps.setInt(i + 1, ((Phone) o).getAreaCode());
            } else {
                ps.setString(i + 1, null);
            }
        } else {
            ps.setString(i, null);
            ps.setString(i + 1, null);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o != null) {
            Phone newPhone = (Phone) o;
            return new Phone(newPhone.getAreaCode(), newPhone.getPhoneNum());
        }
        return null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o, SessionImplementor si) throws HibernateException {
        Object deepCopy = deepCopy(o);
        if (!(deepCopy instanceof Serializable)) {
            return (Serializable) deepCopy;
        }
        return null;
    }

    @Override
    public Object assemble(Serializable serializable, SessionImplementor sessionImplementor, Object o) throws HibernateException {
        return deepCopy(serializable);

    }

    @Override
    public Object replace(Object o, Object o1, SessionImplementor sessionImplementor, Object o2) throws HibernateException {
        return deepCopy(o);
    }
}
