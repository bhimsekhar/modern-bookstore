```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testVulnerabilityDemonstration() throws Exception {
        // Demonstrate the vulnerability existed (now fixed)
        // This should not be possible after the fix
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                .param("sortField", "<script>alert('XSS')</script>"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testFixPreventsExploitation() throws Exception {
        // Confirm the fix prevents exploitation
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                .param("encodedSortField", java.net.URLEncoder.encode("<script>alert('XSS')</script>")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testNormalFunctionality() throws Exception {
        // Confirm normal functionality still works
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                .param("encodedSortField", "id")
                .param("sortDir", "asc")
                .param("encodedSearch", ""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/books/search/genre")
                .param("genre", "fiction"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
```