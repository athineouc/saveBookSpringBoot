package hello.categoryControllerIT;

import hello.model.Category;
import hello.repository.CategoryRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoryCreateTest {

    private static final String CATEGORY_NAME = "thriller";
    private static final String NOT_EXIST_CATEGORY_NAME = "comedy";
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        Category category = new Category(CATEGORY_NAME);
        categoryRepository.save(category);
    }

    @After
    public void down() {
        categoryRepository.deleteAll();
    }

    @Test
    public void createCategory_thenStatus200() throws Exception {
        mvc.perform(post("/category/create/{categoryName}", NOT_EXIST_CATEGORY_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName").value(NOT_EXIST_CATEGORY_NAME));
    }

    @Test
    public void createCategoryAllreadyExist_thenStatus() throws Exception {
        mvc.perform(post("/category/create/{categoryName}", CATEGORY_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCategoryWithoutName_thenStatus() throws Exception {
        mvc.perform(post("/category/create/{categoryName}", "")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
