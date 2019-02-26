package hello.service;

import hello.exceptions.BookNotFoundException;
import hello.model.Book;
import hello.model.Category;
import hello.modelDTO.BookDTO;
import hello.repository.BookRepository;
import hello.repository.CategoryRepository;
import hello.transformer.BookTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static hello.transformer.BookTransformer.toDTO;
import static hello.transformer.BookTransformer.toEntity;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ServiceUtils serviceUtils;

    public BookDTO createBook(final BookDTO bookDTO) {

        Category category = serviceUtils.getOrCreateCategoryByName(bookDTO.getCategoryName());
        Book bookEntity = toEntity(bookDTO, category);
        Book createdBook = bookRepository.save(bookEntity);
        return toDTO(createdBook);

    }

    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream()
                .map(BookTransformer::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(final Long id) {
        return toDTO(bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Id:" + id)));
    }


    public List<BookDTO> getBooksByName(String bookName) {
        return bookRepository.findAllByBookName(bookName).stream()
                .map(BookTransformer::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO updateBook(final BookDTO bookDTO) {

        serviceUtils.validateUpdate(bookDTO);
        Category category = serviceUtils.getOrCreateCategoryByName(bookDTO.getCategoryName());
        Book book = bookRepository.findById(bookDTO.getId())
                .orElseThrow(() -> new BookNotFoundException(("Book Id: " + bookDTO.getId())));
        bookDTO.setId(book.getId());
        return toDTO(bookRepository.save(toEntity(bookDTO, category)));
    }

    public void deleteBookById(final Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Id: " + id));
        serviceUtils.detachDeleteRelations(book);
        bookRepository.deleteById(id);
    }

}
