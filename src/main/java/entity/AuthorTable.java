package entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "author")
public class AuthorTable {

    @NonNull
    @XmlElement(name = "id", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @XmlElement(name = "first_name", required = true)
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @XmlElement(name = "family_name", required = true)
    @Column(name = "family_name")
    private String familyName;

    @XmlElement(name = "second_name")
    @Column(name = "second_name")
    private String secondName;

    @XmlElement(name = "birth_date")
    @Column(name = "berth_date")
    private String birthDate;

    public AuthorTable(Long id) {
        this.id = id;
    }
}
