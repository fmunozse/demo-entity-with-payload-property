package cloud.fmunozse.demoentitywithpayloadproperty.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Phone {

    private Integer areaCode = null;
    private String phoneNum = null;
}
