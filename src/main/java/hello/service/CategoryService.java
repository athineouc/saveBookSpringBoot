package hello.service;

import hello.exceptions.CategoryAlreadyExistException;
import hello.exceptions.CategoryNotFoundException;
import hello.model.Category;
import hello.modelDTO.CategoryDTO;
import hello.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static hello.transformer.CategoryTransformer.toDTO;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryDTO createCategory(String categoryName) {
        categoryRepository.findByCategoryName(categoryName)
                .ifPresent(c -> {
                    throw new CategoryAlreadyExistException("BookName-" + c.getCategoryName());
                });

        Category save = categoryRepository.save(new Category(categoryName));
        return toDTO(save);
    }

    public CategoryDTO getCategoryByName(String categoryName) {
        return toDTO(categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException("BookName-" + categoryName)));
    }


    public List<CategoryDTO> getAllCategory() {
        return categoryRepository.findAll().stream()
                .map(category -> toDTO(category))
                .collect(Collectors.toList());
    }

    public void deleteCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException("BookID-" + categoryName));
        categoryRepository.delete(category);
    }
}
