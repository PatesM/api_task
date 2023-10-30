package models.get_all_author_books_xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "author")
public class GetAllAuthorBooksXmlRequest {

    @XmlElement(name = "author_id", required = true)
    private int authorId;
}
