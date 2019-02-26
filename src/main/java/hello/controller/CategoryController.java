package hello.controller;

import hello.modelDTO.CategoryDTO;
import hello.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create/{categoryName}")
    public CategoryDTO createCategory(@PathVariable @NotBlank String categoryName) {
        return categoryService.createCategory(categoryName);
    }

    @GetMapping("get/{categoryName}")
    public CategoryDTO getByCategoryName(@PathVariable @NotBlank String categoryName) {
        return categoryService.getCategoryByName(categoryName);
    }

    @GetMapping("/getAll")
    public List<CategoryDTO> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @DeleteMapping("/delete/{categoryName}")
    public void deleteCategory(@PathVariable @NotBlank String categoryName) {
        categoryService.deleteCategory(categoryName);
    }
}
