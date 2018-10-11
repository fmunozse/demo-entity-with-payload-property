package cloud.fmunozse.demoentitywithpayloadproperty.model.types.iso2022;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreditTransferTransaction {

    private String id;
    private String creditPartyAgentId;
    private String debitPartyAgentId;
    private BigDecimal paymentAmount;

}
