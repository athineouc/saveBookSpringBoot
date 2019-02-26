package hello;

//import hello.model.Reservation;

import hello.model.Book;
import hello.model.Category;
import hello.modelDTO.BookDTO;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Factory {

    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    public static <T> List<T> getRandomListOf(Supplier<T> supplier) {
        return Stream.generate(supplier)
                .limit(ThreadLocalRandom.current().nextInt(5, 10))
                .collect(toList());
    }

    public static Long getRandomLong() {
        return ThreadLocalRandom.current().nextLong(2, 100);
    }

    public static BookDTO getRandomBookDTO() {
        BookDTO bookdto = BookDTO.builder()
                .bookName(getRandomString())
                .numOfPages(getRandomLong())
                .categoryName(getRandomString())
                .build();
        return bookdto;
    }

    public static Book getRandomBook(Category category) {
        Book book = Book.builder()
                .bookName(getRandomString())
                .numOfPages(getRandomLong())
                .bookCategory(category)
                .build();
        return book;
    }

    public static Category getRandomCategory() {
        Category category = Category.builder()
                .categoryName(getRandomString())
                .build();
        return category;
    }

}
