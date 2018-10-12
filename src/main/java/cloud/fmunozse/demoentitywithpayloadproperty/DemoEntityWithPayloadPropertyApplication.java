package cloud.fmunozse.demoentitywithpayloadproperty;

import cloud.fmunozse.demoentitywithpayloadproperty.model.TestEntityWithComposite;
import cloud.fmunozse.demoentitywithpayloadproperty.model.types.PaymentPayload;
import cloud.fmunozse.demoentitywithpayloadproperty.model.types.Phone;
import cloud.fmunozse.demoentitywithpayloadproperty.model.types.iso2022.CreditTransferTransaction;
import cloud.fmunozse.demoentitywithpayloadproperty.repository.TestEntityWithCompositeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class DemoEntityWithPayloadPropertyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoEntityWithPayloadPropertyApplication.class, args);
	}



	@Bean
	public CommandLineRunner loadData (TestEntityWithCompositeRepository repository) {

		return (args -> {

			CreditTransferTransaction trn =  new CreditTransferTransaction("MyId","creditPartyId","debitPArtyId",BigDecimal.valueOf(1.1));
			TestEntityWithComposite entity = new TestEntityWithComposite();
			entity.setName("my name");
			entity.setPhone(new Phone(1, "12345"));
			entity.setPaymentPayload(new PaymentPayload(CreditTransferTransaction.class, trn));
			 repository.save(entity);

			trn =  new CreditTransferTransaction("MyId2","2creditPartyId","2debitPArtyId",BigDecimal.valueOf(2.1));
			entity = new TestEntityWithComposite();
			entity.setName("my name");
			entity.setPhone(new Phone(1, "12345"));
			entity.setPaymentPayload(new PaymentPayload(CreditTransferTransaction.class, trn));
			repository.save(entity);
		});

	}
}
