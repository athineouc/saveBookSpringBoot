package hello.repositoryTest;

import hello.model.Category;
import hello.repository.CategoryRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static hello.Factory.getRandomBook;
import static hello.Factory.getRandomCategory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class categoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @After
    public void down() {
        categoryRepository.deleteAll();
    }

    @Test
    public void findByCategoryNameTest() {
        Category category = getRandomCategory();
        category.getBooks().add(getRandomBook(category));
        category.getBooks().add(getRandomBook(category));

        categoryRepository.save(category);
        Optional<Category> result = categoryRepository.findByCategoryName(category.getCategoryName());
        assert (result.isPresent());
        assertEquals(result.get().getCategoryName(), category.getCategoryName());
    }

    @Test
    public void FindAllTest() {
        Category category = getRandomCategory();
        category.getBooks().add(getRandomBook(category));
        category.getBooks().add(getRandomBook(category));

        categoryRepository.save(category);
        List<Category> result = categoryRepository.findAll();
        assertFalse(result.isEmpty());
    }


}
