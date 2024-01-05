package dt.learning.policyapi.db.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "policy")
public class PolicyEntity implements java.io.Serializable {

    @Id
    @Column(name = "policy_no")
    private String policyNo;

    @Column(name = "policy_holder_name")
    private String policyHolderName;

    @Column(name = "policy_holder_gender")
    private String policyHolderGender;

    @Column(name = "policy_type")
    private String policyType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "policy")
    private List<MaintenanceProgressEntity> maintenanceProgresses;
}
