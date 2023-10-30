package models.save_new_book;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.AuthorTable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveNewBookRequest {

    private String bookTitle;
    private AuthorTable author;
}
