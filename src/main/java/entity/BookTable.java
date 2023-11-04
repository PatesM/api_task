package entity;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class BookTable {

    private Long id;
    private String bookTitle;
    private String authorId;
    private Timestamp updated;
}
