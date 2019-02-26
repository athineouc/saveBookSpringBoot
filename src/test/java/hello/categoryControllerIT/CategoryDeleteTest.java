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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoryDeleteTest {

    private static final String CATEGORY_NAME = "thriller";
    private static final String NOT_EXIST_CATEGORY_NAME = "horror";
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
    public void deleteCategory_thenStatus200() throws Exception {
        mvc.perform(delete("/category/delete/{categoryName}", CATEGORY_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCategory_thenStatus404() throws Exception {
        mvc.perform(delete("/category/delete/{categoryName}", NOT_EXIST_CATEGORY_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCategoryWithoutName_thenStatus404() throws Exception {
        mvc.perform(delete("/category/delete")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
