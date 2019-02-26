package hello.bookControllerIT;

import hello.modelDTO.BookDTO;
import hello.repository.CategoryRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static hello.Factory.getRandomBookDTO;
import static hello.IntegrationTestUtils.BookDTOToJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookCreateTest {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private MockMvc mvc;

    @After
    public void down() {
        categoryRepository.deleteAll();
    }

    @Test
    public void createBook_thenStatus200() throws Exception {
        BookDTO bookDTO = getRandomBookDTO();
        mvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookName").value(bookDTO.getBookName()));
    }

    @Test
    public void createBookWithoutName_thenStatus400() throws Exception {
        BookDTO bookDTO = getRandomBookDTO();
        bookDTO.setBookName("");
        mvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBookWithoutPages_thenStatus400() throws Exception {
        BookDTO bookDTO = getRandomBookDTO();
        bookDTO.setNumOfPages(null);
        mvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBookWithoutCategoryName_thenStatus400() throws Exception {
        BookDTO bookDTO = getRandomBookDTO();
        bookDTO.setCategoryName("");
        mvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBookWithoutNullBody_thenStatus400() throws Exception {
        mvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(null)))
                .andExpect(status().isBadRequest());
    }
}
