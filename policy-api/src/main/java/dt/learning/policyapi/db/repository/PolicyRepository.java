package dt.learning.policyapi.db.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dt.learning.policyapi.db.domain.PolicyEntity;

@Repository
public interface PolicyRepository
        extends CrudRepository<PolicyEntity, Integer>, JpaSpecificationExecutor<PolicyEntity> {

}
