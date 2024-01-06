package dt.learning.policyapi.it.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;

import dt.learning.policyapi.api.controller.dto.PolicyDto;
import dt.learning.policyapi.api.service.PolicyService;
import dt.learning.policyapi.db.domain.PolicyEntity;
import dt.learning.policyapi.db.repository.PolicyRepository;
import dt.learning.policyapi.db.specification.PolicySpecification;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceUnitTest {

    // DIされるものは全てMock化する必要がある。
    @Mock
    private PolicySpecification policySpecification;
    @Mock
    private PolicyRepository policyRepository;
    @Mock
    private ModelMapper modelMapper;

    // テスト対象
    @InjectMocks
    private PolicyService policyService;

    @Test
    public void searchPolicies() {
        // findAll() の戻り値モック
        PolicyEntity policy1 = new PolicyEntity();
        policy1.setPolicyNo("80000001");
        policy1.setPolicyHolderName("契約者A");
        policy1.setPolicyHolderGender("M");
        PolicyEntity policy2 = new PolicyEntity();
        policy2.setPolicyNo("80000002");
        policy2.setPolicyHolderName("契約者B");
        policy2.setPolicyHolderGender("M");
        PolicyEntity policy3 = new PolicyEntity();
        policy3.setPolicyNo("80000003");
        policy3.setPolicyHolderName("契約者C");
        policy3.setPolicyHolderGender("F");
        List<PolicyEntity> mockEntities = new ArrayList<>();
        mockEntities.add(policy1);
        mockEntities.add(policy2);
        mockEntities.add(policy3);

        // DIされる各クラスのMock設定
        Map<String, Object> conditionMap = new HashMap<>();
        Specification<PolicyEntity> mockSpecs = mock(Specification.class);
        when(policySpecification.buildSpecifications(anyMap())).thenReturn(mockSpecs);
        when(policyRepository.findAll(mockSpecs)).thenReturn(mockEntities);

        // テスト対象メソッドの呼び出し
        List<PolicyDto> results = policyService.searchPolicies(conditionMap);

        // アサーション
        assertEquals(3, results.size());
    }

}
