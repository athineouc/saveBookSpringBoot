package hello.serviceTest;


import hello.exceptions.CategoryAlreadyExistException;
import hello.exceptions.CategoryNotFoundException;
import hello.model.Category;
import hello.modelDTO.CategoryDTO;
import hello.repository.CategoryRepository;
import hello.service.CategoryService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static hello.Factory.getRandomBook;
import static hello.Factory.getRandomListOf;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    private static final String CATEGORY_NAME = "thriller";
    private static final String CATEGORY_NAME2 = "action";
    private static final String NOT_EXIST_CATEGORY_NAME = CATEGORY_NAME + UUID.randomUUID().toString();

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        final Category category = new Category(CATEGORY_NAME);
        category.getBooks().addAll(getRandomListOf(() -> getRandomBook(category)));
        categoryRepository.save(category);
    }

    @After
    public void down() {
        categoryRepository.deleteAll();
    }

    @Test
    public void findByCategoryName_Should_Work() {
        CategoryDTO categoryDTO = categoryService.getCategoryByName(CATEGORY_NAME);
        assertThat(categoryDTO.getCategoryName())
                .isEqualTo(CATEGORY_NAME);
        assertThat(categoryDTO.getBooks())
                .isNotEmpty();
    }

    @Test
    public void findByCategoryName_Should_FAIL() {
        assertThatThrownBy(() -> categoryService.getCategoryByName(NOT_EXIST_CATEGORY_NAME))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    public void createCategoryName_Should_WORK() {
        assertThat(categoryService.createCategory(CATEGORY_NAME2).getCategoryName())
                .isEqualTo(CATEGORY_NAME2);
    }

    @Test
    public void createCategoryName_Should_FAIL() {
        assertThatThrownBy(() -> categoryService.createCategory(CATEGORY_NAME))
                .isInstanceOf(CategoryAlreadyExistException.class);
    }

    @Test
    public void getAllCategory_Should_WORK() {
        assertThat(categoryService.getAllCategory())
                .isNotEmpty();
    }

    @Test
    public void deleteCategoryByName_Should_WORK() {
        categoryRepository.deleteById(CATEGORY_NAME);
        assertThatThrownBy(() -> categoryService.getCategoryByName(CATEGORY_NAME))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    public void deleteCategoryByName_Should_FAIL() {
        assertThatThrownBy(() -> categoryService.deleteCategory(NOT_EXIST_CATEGORY_NAME))
                .isInstanceOf(CategoryNotFoundException.class);
    }


}
