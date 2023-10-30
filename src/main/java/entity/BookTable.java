package entity;

import lombok.Data;

@Data
public class BookTable {

    private long id;
    private String bookTitle;
    private String authorId;
}
