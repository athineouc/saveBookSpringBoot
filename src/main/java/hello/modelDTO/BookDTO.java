package hello.modelDTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
public class BookDTO {

    private Long id;

    @NotBlank
    private String bookName;

    @NotNull
    private Long numOfPages;

    @NotBlank
    private String categoryName;

}
