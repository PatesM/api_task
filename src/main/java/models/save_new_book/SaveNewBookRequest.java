package models.save_new_book;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveNewBookRequest {

    public static String bookTitle;
    public static Object author;
    public static long authorId;
}
