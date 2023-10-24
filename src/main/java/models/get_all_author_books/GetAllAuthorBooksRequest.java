package models.get_all_author_books;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAllAuthorBooksRequest {

    public static String authorId;
}
