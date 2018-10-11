package cloud.fmunozse.demoentitywithpayloadproperty.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PaymentPayload<T> {

    @NotNull
    private Class type;

    @NotNull
    private T payload;

    public PaymentPayload(T payload) {

        this.payload = payload;
        this.type = payload.getClass();
    }
}
