package models.add_new_author;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddNewAuthorPositiveResponse {

    public static long authorId;
}
