```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testVulnerabilityExisted() throws Exception {
        // Actuator endpoints should be accessible without authentication
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testFixPreventsExploitation() throws Exception {
        // Actuator endpoints should not be accessible with a normal user role
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "actuator", roles = "ACTUATOR")
    public void testFixAllowsAuthorizedAccess() throws Exception {
        // Actuator endpoints should be accessible with the ACTUATOR role
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNormalFunctionalityStillWorks() throws Exception {
        // Public endpoints should still be accessible without authentication
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/login"))
                .andExpect(status().isOk());
    }
}
```