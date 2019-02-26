package hello.transformer;

import hello.model.Book;
import hello.model.Category;
import hello.modelDTO.BookDTO;

public class BookTransformer {

    public static BookDTO toDTO(Book book) {
        BookDTO bookDTO = BookDTO.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .numOfPages(book.getNumOfPages())
                .categoryName(book.getBookCategory().getCategoryName())
                .build();
        return bookDTO;
    }

    public static Book toEntity(BookDTO bookDTO, Category category) {
        Book book = Book.builder()
                .bookName(bookDTO.getBookName())
                .numOfPages(bookDTO.getNumOfPages())
                .bookCategory(category)
                .build();
        book.setId(bookDTO.getId());
        return book;
    }
}
