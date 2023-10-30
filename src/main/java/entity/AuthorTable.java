package entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthorTable {

    @NonNull
    @XmlElement(name = "id", required = true)
    private Long id;

    @NonNull
    @XmlElement(name = "first_name", required = true)
    private String firstName;

    @NonNull
    @XmlElement(name = "family_name", required = true)
    private String familyName;

    @XmlElement(name = "second_name")
    private String secondName;

    public AuthorTable(long id) {
        this.id = id;
    }
}
