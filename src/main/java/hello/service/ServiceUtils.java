package hello.service;

import hello.exceptions.BookIdNullException;
import hello.model.Book;
import hello.model.Category;
import hello.modelDTO.BookDTO;
import hello.repository.BookRepository;
import hello.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ServiceUtils {


    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public Category getOrCreateCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name).orElseGet(() -> {
            Category category = new Category(name);
            return categoryRepository.save(category);
        });
    }

    public void validateUpdate(BookDTO bookDTO) {
        Optional.ofNullable(bookDTO.getId())
                .orElseThrow(() -> new BookIdNullException("Book id should not be null."));
    }

    public void detachDeleteRelations(final Book book) {
        Optional<Category> byCategoryName = categoryRepository.findByCategoryName(book.getBookCategory().getCategoryName());
        byCategoryName.ifPresent(category -> {
            category.getBooks().remove(book);
            categoryRepository.save(category);
        });
    }
}
