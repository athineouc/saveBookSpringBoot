package hello.modelDTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class CategoryDTO {

    private final List<BookDTO> Books = new ArrayList<BookDTO>();

    @NotBlank
    private String categoryName;
}
