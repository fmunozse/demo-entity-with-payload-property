package cloud.fmunozse.demoentitywithpayloadproperty.repository;

import cloud.fmunozse.demoentitywithpayloadproperty.model.TestEntityWithComposite;
import cloud.fmunozse.demoentitywithpayloadproperty.model.types.PaymentPayload;
import cloud.fmunozse.demoentitywithpayloadproperty.model.types.Phone;
import cloud.fmunozse.demoentitywithpayloadproperty.model.types.iso2022.CreditTransferTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.AllOf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TestEntityWithCompositeRepositoryTest {

    @Autowired
    TestEntityWithCompositeRepository testEntityWithCompositeRepository;

    @Test
    public void whenPersistEntityComposite_thenItsGeneratedaValue() throws JsonProcessingException {

        CreditTransferTransaction trn = createCreditTransferTransactionFake("MyId");
        TestEntityWithComposite entity = new TestEntityWithComposite();
        entity.setName("my name");
        entity.setPhone(new Phone(1, "12345"));

        PaymentPayload<CreditTransferTransaction> paymentPayload = new PaymentPayload<CreditTransferTransaction>(CreditTransferTransaction.class, trn);

        entity.setPaymentPayload(paymentPayload);

        entity = testEntityWithCompositeRepository.save(entity);

        assertThat(entity.getId(), is(notNullValue()));
        assertThat(entity.getPhone(), is(notNullValue()));
        assertThat(entity.getPaymentPayload(), is(notNullValue()));
        assertThat(entity.getPaymentPayload().getPayload(), is(notNullValue()));
    }

    @Test
    public void whenSearchAEntity_thenExpectedFilledComposite() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CreditTransferTransaction trn = createCreditTransferTransactionFake("MyId2");
        TestEntityWithComposite entity = new TestEntityWithComposite();
        entity.setName("my name");
        entity.setPhone(new Phone(3, "12345"));
        entity.setPaymentPayload(new PaymentPayload<>(CreditTransferTransaction.class, trn));

        entity = testEntityWithCompositeRepository.saveAndFlush(entity);
        entity = testEntityWithCompositeRepository.findOne(entity.getId());

        assertThat(entity.getPhone().getAreaCode(), is(3));
        assertThat(entity.getPhone().getPhoneNum(), is ("12345"));
        assertThat(mapper.writeValueAsString(entity.getPaymentPayload().getPayload()), hasJsonPath("$.id", is("MyId2")));

    }



    private CreditTransferTransaction createCreditTransferTransactionFake (String id) {
        return new CreditTransferTransaction(
                id,
                "creditPartyId",
                "debitPArtyId",
                BigDecimal.valueOf(1.1)
        );
    }


}