package models.add_new_author;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveNewAuthorRequest {

    @NonNull
    private String firstName;
    @NonNull
    private String familyName;
    private String secondName;
    private String birthDate;
}
