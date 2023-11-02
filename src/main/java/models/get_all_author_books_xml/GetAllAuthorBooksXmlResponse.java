package models.get_all_author_books_xml;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.get_all_author_books.GetAllAuthorBooksResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "authors_books")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllAuthorBooksXmlResponse {

    @XmlElementWrapper(name = "books")
    @XmlElement(name = "book", required = true)
    List<GetAllAuthorBooksResponse> books;
}
