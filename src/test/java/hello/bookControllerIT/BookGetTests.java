package hello.bookControllerIT;

import hello.modelDTO.BookDTO;
import hello.repository.CategoryRepository;
import hello.service.BookService;
import org.junit.After;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookGetTests {


    private static final String BOOK_NAME = "random book";
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
        bookDTO.setBookName(BOOK_NAME);

        BookDTO save = bookService.createBook(bookDTO);

        EXIST_ID = save.getId();

        bookService.createBook(bookDTO);
    }

    @After
    public void down() {
        categoryRepository.deleteAll();
    }

    @Test
    public void getAllBook_thenStatus200() throws Exception {
        mvc.perform(get("/book/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getAllBookByName_thenStatus200() throws Exception {
        mvc.perform(get("/book/getAll/{bookName}", BOOK_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    public void getBookById_thenStatus200() throws Exception {
        mvc.perform(get("/book/get/{id}", EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EXIST_ID));
    }


    @Test
    public void getBookByIdNotExist_thenStatus404() throws Exception {
        mvc.perform(get("/book/get/{id}", getRandomLong() + EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getBookWithoutId_thenStatus404() throws Exception {
        mvc.perform(get("/book/get/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
