package models.save_new_book;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveNewBookPositiveResponse {

    public static long bookId;
}
