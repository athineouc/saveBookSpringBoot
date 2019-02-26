package hello.transformer;

import hello.model.Category;
import hello.modelDTO.CategoryDTO;

public class CategoryTransformer {

    public static CategoryDTO toDTO(Category category) {

        final CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryName(category.getCategoryName())
                .build();
        category.getBooks().forEach(book -> categoryDTO.getBooks().add(BookTransformer.toDTO(book)));
        return categoryDTO;
    }


}
