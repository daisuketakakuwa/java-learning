package dt.learning.policyapi.db.repository;

import org.springframework.data.repository.CrudRepository;

import dt.learning.policyapi.db.domain.MaintenanceProgressEntity;

public interface MaintenanceProgressRepository extends CrudRepository<MaintenanceProgressEntity, Integer> {

}
