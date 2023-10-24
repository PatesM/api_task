package models.get_all_author_books_xml;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAllAuthorBooksXmlRequest {

    public static String authorId;
}
