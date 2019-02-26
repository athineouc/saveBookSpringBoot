package hello.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
@Entity
@ToString
public class Category {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bookCategory", orphanRemoval = true)
    private final List<Book> Books = new ArrayList<Book>();

    @Id
    private String categoryName;

}

