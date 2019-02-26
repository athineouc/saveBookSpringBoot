package hello.bookControllerIT;

import hello.model.Category;
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
import static hello.IntegrationTestUtils.BookDTOToJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookUpdateTest {


    private static final String CATEGORY_NAME = "action";
    private static Long EXIST_ID;

    @Autowired
    BookService bookService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        Category category = new Category(CATEGORY_NAME);
        categoryRepository.save(category);
        BookDTO bookDTO = getRandomBookDTO();
        BookDTO save = bookService.createBook(bookDTO);
        EXIST_ID = save.getId();

    }

    @After
    public void down() {
        categoryRepository.deleteAll();
    }

    @Test
    public void updateBook_thenStatus200() throws Exception {
        BookDTO bookDTO = getRandomBookDTO();
        bookDTO.setId(EXIST_ID);
        bookDTO.setCategoryName(CATEGORY_NAME);
        mvc.perform(put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookName").value(bookDTO.getBookName()));
    }

    @Test
    public void updateBookWithoutName_thenStatus400() throws Exception {
        BookDTO bookDTO = getRandomBookDTO();
        bookDTO.setBookName("");
        mvc.perform(put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateBookWithoutPages_thenStatus400() throws Exception {
        BookDTO bookDTO = getRandomBookDTO();
        bookDTO.setNumOfPages(null);
        mvc.perform(put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateBookWithoutCategoryName_thenStatus400() throws Exception {
        BookDTO bookDTO = getRandomBookDTO();
        bookDTO.setCategoryName("");
        mvc.perform(put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateBookWithoutNullBody_thenStatus400() throws Exception {
        mvc.perform(put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateBookWithoutId_thenStatus404() throws Exception {
        BookDTO bookDTO = getRandomBookDTO();
        mvc.perform(put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateBookThatNotExist_thenStatus404() throws Exception {
        BookDTO bookDTO = getRandomBookDTO();
        bookDTO.setId(EXIST_ID + getRandomLong());
        mvc.perform(put("/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookDTOToJsonString(bookDTO)))
                .andExpect(status().isNotFound());
    }
}
