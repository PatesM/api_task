package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class DateGenerator {

    public static String generateDate() {
        LocalDate from = LocalDate.of(1000, 1, 1);
        LocalDate to = LocalDate.of(1999, 12, 31);
        long days = from.until(to, ChronoUnit.DAYS);
        long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
        LocalDate randomDate = from.plusDays(randomDays);
        return randomDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
