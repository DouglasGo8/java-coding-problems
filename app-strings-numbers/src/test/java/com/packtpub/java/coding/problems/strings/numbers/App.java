package com.packtpub.java.coding.problems.strings.numbers;

import com.packtpub.java.coding.problems.common.domain.SkinnyExchange;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class App {


    @Test
    public void countDuplicatedChars() {

        var word = "Before away baby let alright";

        var charsGroupedBy = word.replaceAll("\\W", "")
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        out.println(charsGroupedBy);
    }

    @Test
    public void firstNonDuplicatedChars() {
        var word = "Before away baby let alright";
        var chs = word.replaceAll("\\W", "")
                .codePoints() // cast to chars
                .boxed() // return a IntStream (char numbers)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));

        out.println(chs);
    }

    @Test
    public void reverseWords() {

        var word = "Knocking on heavens door";

        var result = Pattern.compile(" +").splitAsStream(word)
                .map(w -> {
                    // out.println(w);
                    return new StringBuilder(w).reverse();
                })
                .collect(Collectors.joining(""));

        out.println(result);
    }

    @Test
    public void checkingOnlyDigits() {

        var words = "Spell with someone";
        var digits = "1234";

        out.println(words.matches("[0-9]+")); // false
        out.println(digits.matches("[0-9]+")); // true

        // better performance solution
        var isOnlyDigit = true;

        for (int i = 0; i < words.length(); i++) {
            if (!Character.isDigit(words.charAt(i))) {
                isOnlyDigit = false;
            }
        }

        out.println(isOnlyDigit);
    }

    @Test
    public void countVowels() {
        var word = "In my dreams with you";
        var allVowels = new HashSet<>(List.of('a', 'e', 'i', 'o', 'u'));
        var result = word.toLowerCase()
                .chars()
                .mapToObj(c -> (char) c)
                .filter(allVowels::contains)
                .count();

        out.println(result);
    }

    @Test
    public void makeSkinnyExchangeSample() {

        var skin = new SkinnyExchange("xZp2ii2212xxx", "fooKey", "http://bla/api", null);

        out.println(skin.getToken());

    }

    // pg 95
}
