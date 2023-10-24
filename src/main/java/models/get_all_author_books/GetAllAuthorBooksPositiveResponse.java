package models.get_all_author_books;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllAuthorBooksPositiveResponse {

    public static List<Object> books;
    public static List<Object> authors;
}
