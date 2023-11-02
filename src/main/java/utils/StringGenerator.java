package utils;

import java.util.Random;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class StringGenerator {

    public static String generateString(int stringSize) {
        String symbols = "abcdefghijklmnopqrstuvwxyz";
        String random = new Random().ints(stringSize,
                0, symbols.length())
            .mapToObj(symbols::charAt)
            .map(Object::toString)
            .collect(Collectors.joining());
        return StringUtils.capitalize(random);
    }
}
