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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoryGetTest {

    private static final String CATEGORY_NAME = "thriller";
    private static final String NOT_EXIST_CATEGORY_NAME = "action";
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
    public void CategoryGetAll_thenStatus200() throws Exception {
        mvc.perform(get("/category/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void CategoryGetByName_thenStatus200() throws Exception {
        mvc.perform(get("/category/get/{categoryName}", CATEGORY_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName").value(CATEGORY_NAME));
    }

    @Test
    public void CategoryGetByNameNotExist_thenStatus404() throws Exception {
        mvc.perform(get("/category/get/{categoryName}", NOT_EXIST_CATEGORY_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void CategoryGetWithoutName_thenStatus404() throws Exception {
        mvc.perform(get("/category/get")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
