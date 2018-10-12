package cloud.fmunozse.demoentitywithpayloadproperty.model.types;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentPayloadType implements CompositeUserType {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String[] getPropertyNames() {
        return new String[]{"classType", "payload"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                org.hibernate.type.StandardBasicTypes.STRING,
                org.hibernate.type.StandardBasicTypes.STRING
        };
    }


    @Override
    public Object getPropertyValue(Object o, int i)  throws HibernateException {
        if (o == null) {
            return null;
        } else if (i == 0) {
            return ((PaymentPayload) o).getClassType();
        } else if (i == 1) {
            return ((PaymentPayload) o).getPayload();
        }
        return null;
    }

    @Override
    public void setPropertyValue(Object o, int i, Object o1)throws HibernateException {
        if (o != null) {
            if (i == 0) {
                try {
                    ((PaymentPayload) o).setClassType( Class.forName( (String) o1));
                } catch (ClassNotFoundException e) {
                    throw new HibernateException(e);
                }
            } else if (i == 1 && o1 != null) {
                try {
                    Class clazz =  ((PaymentPayload) o).getClassType();
                    ((PaymentPayload) o).setPayload( mapper.readValue((String)o1 , clazz));
                } catch (IOException e) {
                    throw new HibernateException(e);
                }
            }
        }
    }

    @Override
    public Class returnedClass() {
        return PaymentPayload.class;
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
    public Object nullSafeGet(ResultSet rs, String[] strings, SessionImplementor si, Object o) throws HibernateException, SQLException {
        try {
            Class clazz = Class.forName(rs.getString(strings[0]));
            return new PaymentPayload(clazz, mapper.readValue(rs.getString(strings[1]) , clazz) );
        } catch (ClassNotFoundException e) {
            throw new HibernateException(e);
        } catch (IOException e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object o, int i, SessionImplementor si) throws HibernateException, SQLException {
        if (o != null) {
            ps.setString(i, ((PaymentPayload) o).getClassType().getName());
            try {
                ps.setString(i + 1,  mapper.writeValueAsString(((PaymentPayload) o).getPayload()));
            } catch (JsonProcessingException e) {
                throw new HibernateException(e);
            }
        } else {
            ps.setString(i, null);
            ps.setString(i + 1, null);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o != null) {
            PaymentPayload newPaymentPayload = (PaymentPayload) o;
            //TODO Check deepCopy of Object ...
            return new PaymentPayload(newPaymentPayload.getClassType(), newPaymentPayload.getPayload());
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
