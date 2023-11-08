package models.add_new_author;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"secondName", "birthDate"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveNewAuthorRequest {

    @NonNull
    private String firstName;
    @NonNull
    private String familyName;
    private String secondName;
    private String birthDate;
}
