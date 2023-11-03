package models.add_new_author;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveNewAuthorRequest {

    @NonNull
    private String firstName;
    @NonNull
    private String familyName;
    private String secondName;
}
