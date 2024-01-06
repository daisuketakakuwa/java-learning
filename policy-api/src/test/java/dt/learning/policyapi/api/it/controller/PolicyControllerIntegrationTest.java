package dt.learning.policyapi.api.it.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PolicyControllerIntegrationTest {

    // @AutoConfigureMockMvc で自動セットアップ
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/db/policy.sql")
    public void givenPolicyNo_whenSearchPolicies_thenReturnMatchedOne() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/policies?policyNo=80000001"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }

    @Test
    @Sql("/db/policy.sql")
    public void givenNoCondition_whenSearchPolicies_thenReturnAll()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/policies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5));
    }

}
