package entity;

import lombok.Data;

@Data
public class BookTable {

    private Long id;
    private String bookTitle;
    private String authorId;
}
