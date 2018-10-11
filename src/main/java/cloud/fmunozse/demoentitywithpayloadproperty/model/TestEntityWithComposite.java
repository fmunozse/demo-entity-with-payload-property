package cloud.fmunozse.demoentitywithpayloadproperty.model;

import cloud.fmunozse.demoentitywithpayloadproperty.model.types.PaymentPayload;
import cloud.fmunozse.demoentitywithpayloadproperty.model.types.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestEntityWithComposite {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Type(type = "cloud.fmunozse.demoentitywithpayloadproperty.model.types.PhoneCompositeType")
    @Columns(columns = {
            @Column(name = "phone_number", nullable = true),
            @Column(name = "area_code", nullable = true)
    })
    private Phone phone;

    @NotNull
    @Type(type = "cloud.fmunozse.demoentitywithpayloadproperty.model.types.PaymentPayloadType")
    @Columns(columns = {
            @Column(name = "type", nullable = false),
            @Column(name = "payload", nullable = false)
    })
    private PaymentPayload paymentPayload;



}
