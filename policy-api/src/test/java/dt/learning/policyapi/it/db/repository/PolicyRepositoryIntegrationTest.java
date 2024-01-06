package dt.learning.policyapi.it.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import dt.learning.policyapi.db.domain.PolicyEntity;
import dt.learning.policyapi.db.repository.PolicyRepository;

@DataJpaTest
@Sql("/db/policy.sql")
public class PolicyRepositoryIntegrationTest {

    @Autowired
    private PolicyRepository repository;

    @Test
    public void whenFindAll_thenReturnAll() {
        List<PolicyEntity> entities = StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        assertEquals(entities.size(), 5);
    }

    @Test
    public void givenPolicyNo_whenFindById_thenReturnMatchedOne() {
        Optional<PolicyEntity> entity = repository.findById(80000001);

        assertTrue(entity.isPresent());
        assertEquals(entity.get().getPolicyNo(), "80000001");
    }

}
