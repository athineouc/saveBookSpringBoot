/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hello.repositoryTest;

import hello.model.Book;
import hello.model.Category;
import hello.repository.BookRepository;
import hello.repository.CategoryRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static hello.Factory.getRandomBook;
import static hello.Factory.getRandomCategory;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @After
    public void down() {
        categoryRepository.deleteAll();
    }

    @Test
    public void FindAllTest() {
        Category category = getRandomCategory();
        category.getBooks().add(getRandomBook(category));
        category.getBooks().add(getRandomBook(category));

        categoryRepository.save(category);
        List<Book> result = bookRepository.findAll();
        assertFalse(result.isEmpty());
        Assert.assertEquals(category.getBooks(), result);
    }

    @Test
    public void FindAllByBookNameTest() {
        Category category = getRandomCategory();
        Book book1 = getRandomBook(category);
        Book book1Copy = getRandomBook(category);
        book1Copy.setBookName(book1.getBookName());
        Book book2 = getRandomBook(category);

        category.getBooks().add(book1);
        category.getBooks().add(book1Copy);
        category.getBooks().add(book2);

        categoryRepository.save(category);
        List<Book> result = bookRepository.findAllByBookName(book1.getBookName());
        assertFalse(result.isEmpty());
        assertThat(result, hasItems(
                book1,
                book1Copy
        ));
    }
}
