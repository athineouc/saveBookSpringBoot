package hello.serviceTest;


import hello.exceptions.BookNotFoundException;
import hello.model.Book;
import hello.model.Category;
import hello.modelDTO.BookDTO;
import hello.repository.BookRepository;
import hello.repository.CategoryRepository;
import hello.service.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static hello.Factory.*;
import static hello.transformer.BookTransformer.toEntity;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    private static final String BOOK_NAME = "randomName";
    private static final String CATEGORY_NAME = "thriller";
    private static final Long NOT_EXIST_ID = 500L;

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookRepository bookRepository;

    @Before
    public void setUp() {
        final Category category = new Category(CATEGORY_NAME);

        Book book = Book.builder()
                .bookName(BOOK_NAME)
                .numOfPages(getRandomLong())
                .bookCategory(category)
                .build();

        category.getBooks().add(book);
        categoryRepository.save(category);
    }

    @After
    public void down() {
        categoryRepository.deleteAll();
    }

    @Test
    public void createBookInNotExistingCategory_Should_WORK() {
        BookDTO bookDTO = getRandomBookDTO();
        BookDTO save = bookService.createBook(bookDTO);

        Category category = categoryRepository.findByCategoryName(bookDTO.getCategoryName()).get();

        Book book = toEntity(save, category);

        assertThat(category.getCategoryName())
                .isEqualTo(bookDTO.getCategoryName());

        assertThat(category.getBooks())
                .contains(book);

        assertThat(bookRepository.findById(save.getId()).get())
                .isEqualTo(book);
    }

    @Test
    public void updateBook_Should_WORK() {
        BookDTO bookDTO = getRandomBookDTO();
        BookDTO save = bookService.createBook(bookDTO);

        save.setCategoryName(CATEGORY_NAME);
        save.setBookName(getRandomString());
        save.setNumOfPages(getRandomLong());

        BookDTO updatedBook = bookService.updateBook(save);

        Category category = categoryRepository.findByCategoryName(updatedBook.getCategoryName()).get();

        Book book = toEntity(updatedBook, category);

        assertThat(category.getCategoryName())
                .isEqualTo(updatedBook.getCategoryName());

        assertThat(category.getBooks())
                .contains(book);

        assertThat(bookRepository.findById(save.getId()).get())
                .isEqualTo(book);
    }

    @Test
    public void getAllCategory_Should_WORK() {
        assertThat(bookService.getAll())
                .isNotEmpty();
    }

    @Test
    public void getById_Should_WORK() {
        BookDTO bookDTO = getRandomBookDTO();
        BookDTO save = bookService.createBook(bookDTO);

        assertThat(bookService.getBookById(save.getId()))
                .isEqualTo(save);
    }

    @Test
    public void getById_Should_FAIL() {
        assertThatThrownBy(() -> bookService.getBookById(NOT_EXIST_ID))
                .isInstanceOf(BookNotFoundException.class);
    }


    @Test
    public void getByName_Should_WORK() {
        BookDTO bookDTO = getRandomBookDTO();
        BookDTO bookDTO2 = getRandomBookDTO();

        bookDTO2.setBookName(bookDTO.getBookName());

        BookDTO save = bookService.createBook(bookDTO);
        BookDTO save2 = bookService.createBook(bookDTO2);

        assertThat(bookService.getBooksByName(bookDTO.getBookName()))
                .contains(save, save2);
    }


    @Test
    public void deleteBookByid_Should_WORK() {
        BookDTO bookDTO = getRandomBookDTO();
        BookDTO save = bookService.createBook(bookDTO);


        bookService.deleteBookById(save.getId());

        assertThatThrownBy(() -> bookService.getBookById(save.getId()))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    public void deleteBookByid_Should_FAIL() {
        assertThatThrownBy(() -> bookService.deleteBookById(NOT_EXIST_ID))
                .isInstanceOf(BookNotFoundException.class);
    }


}
