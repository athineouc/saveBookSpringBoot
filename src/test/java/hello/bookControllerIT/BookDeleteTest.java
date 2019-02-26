package hello.bookControllerIT;

import hello.modelDTO.BookDTO;
import hello.repository.CategoryRepository;
import hello.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static hello.Factory.getRandomBookDTO;
import static hello.Factory.getRandomLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookDeleteTest {


    private static Long EXIST_ID;
    @Autowired
    BookService bookService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {

        BookDTO bookDTO = getRandomBookDTO();
        BookDTO save = bookService.createBook(bookDTO);
        EXIST_ID = save.getId();
        bookService.createBook(bookDTO);
    }

    @Test
    public void deleteBook_thenStatus200() throws Exception {
        mvc.perform(delete("/book/delete/{id}", EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBookWithoutId_thenStatus404() throws Exception {
        mvc.perform(delete("/book/delete/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteBookNotExist_thenStatus404() throws Exception {
        mvc.perform(delete("/book/delete/{id}", EXIST_ID + getRandomLong())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
