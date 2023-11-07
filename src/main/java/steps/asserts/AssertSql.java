package steps.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entity.BookTable;
import java.util.List;

public class AssertSql {

    public void assertListSize(List<BookTable> books, int size) {
        assertEquals(size, books.size());
    }
}
