package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.stream.Collectors;

public class StringGenerator {

    public static String generateString() {
        String symbols = "abcdefghijklmnopqrstuvwxyz";
        String random = new Random().ints(8, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
        return StringUtils.capitalize(random);
    }
}
