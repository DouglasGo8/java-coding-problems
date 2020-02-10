package com.packtpub.java.coding.problems.strings.numbers;

import org.junit.Test;

import java.util.function.Function;
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

    // Page 86
}
