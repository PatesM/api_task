package models.add_new_author;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddNewAuthorRequest {

    public static String firstName;
    public static String familyName;
    public static String secondName;
}
