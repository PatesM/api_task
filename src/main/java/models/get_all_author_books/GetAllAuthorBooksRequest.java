package models.get_all_author_books;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAllAuthorBooksRequest {

    private String authorId;
}
