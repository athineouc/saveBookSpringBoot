package hello.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
@Entity
@ToString(exclude = "bookCategory")
@EqualsAndHashCode(exclude = "bookCategory")
public class Book extends BaseEntity {

    private String bookName;
    private Long numOfPages;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Category bookCategory;


}

