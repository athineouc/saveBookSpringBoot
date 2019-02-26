package hello.controller;

import hello.modelDTO.BookDTO;
import hello.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/create")
    public BookDTO createBook(@RequestBody @NotNull @Valid BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }

    @PutMapping("/update")
    public BookDTO updateBook(@RequestBody @NotNull @Valid BookDTO bookDTO) {
        return bookService.updateBook(bookDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable @NotNull Long id) {
        bookService.deleteBookById(id);
    }

    @GetMapping("/getAll")
    public List<BookDTO> GetAll() {
        return bookService.getAll();
    }

    @GetMapping("/getAll/{bookName}")
    public List<BookDTO> GetAllByName(@PathVariable @NotBlank String bookName) {
        return bookService.getBooksByName(bookName);
    }

    @GetMapping("/get/{id}")
    public BookDTO GetById(@PathVariable @NotNull Long id) {
        return bookService.getBookById(id);
    }

}




