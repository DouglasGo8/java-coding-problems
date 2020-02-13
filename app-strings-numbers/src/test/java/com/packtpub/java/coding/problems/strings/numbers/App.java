package com.packtpub.java.coding.problems.strings.numbers;

import com.packtpub.java.coding.problems.common.domain.SkinnyExchange;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


    @Test
    public void removeWhiteSpaces() {
        var word = "Lady in red";

        out.println(word.replaceAll("\\s", ""));
    }

    @Test
    public void joinerString() {
        var word = new String[]{"I'll try no be so long", "Java Rocks"};
        out.println(Arrays.stream(word, 0, word.length).collect(Collectors.joining(",")));
    }

    @Test
    public void permuteAndPrint() {
        var prefix = "F";
        var str = "Dream";

        permute(prefix, str);
    }

    @Test
    public void removeDuplicated() {
        var word = "Humanity Falls";
        out.println(Arrays.stream(word.split("")).distinct().collect(Collectors.joining()));

    }

    @Test
    public void countCharWithMoreAppear() {
        var word = "For lamb of god still my bleeding heart";

        out.println(word.chars().filter(c -> !Character.isWhitespace(c))
                .mapToObj(c-> (char)c)
                //.forEach(out::println) // print each char
                .collect(Collectors.groupingBy(c-> c, Collectors.counting()))
                .entrySet()
                //.forEach(out::println); //a=2...
                .stream()
                .max(Map.Entry.comparingByValue()).get());

    }

    // 15.

    private void permute(String prefix, String str) {
        int n = str.length();
        if (n == 0) {
            out.println(prefix);
        } else {
            IntStream.range(0, n)
                    .parallel()
                    .forEach(i -> permute(prefix + str.charAt(i),
                            str.substring(i + 1, n) + str.substring(0, i)));
        }
    }
}
