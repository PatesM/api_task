package models.get_all_author_books_xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.get_all_author_books.GetAllAuthorBooksResponse;

import javax.xml.bind.annotation.*;
import java.util.List;

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
