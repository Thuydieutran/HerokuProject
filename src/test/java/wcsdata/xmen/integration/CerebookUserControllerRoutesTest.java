package wcsdata.xmen.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import wcsdata.xmen.result_matchers.StringCountOccurrences;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc(addFilters = false)
class CerebookUserControllerRoutesTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getAllCerebookUser() throws Exception {
        mvc.perform(get("/users")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(new StringCountOccurrences("Wolverine", 1))
                .andExpect(new StringCountOccurrences("superman", 1));
    }

}