package models.get_all_author_books;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import entity.AuthorTable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllAuthorBooksResponse {

    @XmlElement(name = "author", required = true)
    private AuthorTable author;

    @XmlElement(name = "book_title", required = true)
    private String bookTitle;

}
