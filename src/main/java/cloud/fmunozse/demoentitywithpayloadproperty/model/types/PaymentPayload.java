package cloud.fmunozse.demoentitywithpayloadproperty.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PaymentPayload {

    @NotNull
    private Class classType;

    @NotNull
    private Object payload;

    public PaymentPayload(Class classType, Object payload) {
        this.classType = classType;
        this.payload = payload;
    }

    public <T> T getPayloadTyped () {
        return (T)payload;
    }

}
