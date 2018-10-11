package cloud.fmunozse.demoentitywithpayloadproperty.repository;

import cloud.fmunozse.demoentitywithpayloadproperty.model.TestEntityWithComposite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityWithCompositeRepository extends JpaRepository<TestEntityWithComposite, Long> {

}
