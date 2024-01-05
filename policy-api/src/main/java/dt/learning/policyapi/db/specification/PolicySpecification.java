package dt.learning.policyapi.db.specification;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import dt.learning.policyapi.db.domain.PolicyEntity;

@Component
public class PolicySpecification {

    public Specification<PolicyEntity> buildSpecifications(Map<String, Object> conditionMap) {
        Specification<PolicyEntity> specs = Specification.where(null);
        for (Entry<String, Object> e : conditionMap.entrySet()) {
            String key = e.getKey();
            String value = String.valueOf(e.getValue());
            switch (key) {
                case "policyHolderName":
                    specs = specs.and(partialMatch(key, value));
                    break;
                default:
                    specs = specs.and(exactMatch(key, value));
                    break;
            }
        }

        return specs;

    }

    // 完全一致
    private Specification<PolicyEntity> exactMatch(String key, String value) {
        return StringUtils.isBlank(value) ? null
                : (root, query, builder) -> builder.equal(root.get(key), value);
    }

    // 部分一致
    private Specification<PolicyEntity> partialMatch(String key, String value) {
        return StringUtils.isBlank(value) ? null
                : (root, query, builder) -> builder.like(root.get(key), "%" + value + "%");
    }

}
