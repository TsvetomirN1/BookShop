package finalproject.bookshop.web.restControllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthorRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

//    @Test
//    @WithMockUser(username = "user")
//    void test_AuthorResponsePage_LoadsCorrectly() throws Exception {
//        mockMvc
//                .perform(get("/api/authors/all"))
//                .andExpect(status().isOk())
////                .andExpect(view().name("admin/statistics"))
////                .andExpect(model().attributeExists("stats"));
//    }

}