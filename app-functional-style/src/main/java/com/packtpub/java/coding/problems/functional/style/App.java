package com.packtpub.java.coding.problems.functional.style;

import com.packtpub.java.coding.problems.common.domain.Melon;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.System.out;

public class App {

    @Test
    public void filteringMelonsJdk8() {

        Predicate<Melon> v1 = m -> "Watermelon".equalsIgnoreCase(m.getType());

        var melons = List.of(new Melon(1200, "Watermelon"), new Melon(1500, "Cas"));

        melons.stream().filter(v1).forEach(out::println);

    }

    @Test
    public void streamOfFlatMap() {

        Melon[][] melons = {
                {new Melon(2000, "Gac"), new Melon(1600, "Hemi")},
                {new Melon(2000, "Gac"), new Melon(2000, "Apollo")},
                {new Melon(1700, "Horned"), new Melon(1600, "Hemi")}};

        Arrays.stream(melons)
                .distinct()
                .collect(Collectors.toList())
                .forEach(out::println);

        Arrays.stream(melons)
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(out::println);
    }

    @Test
    public void sumMinMaxReduce() {
        var melons = List.of(new Melon(1200, "Watermelon"), new Melon(1500, "Cas"));

        out.println(melons.stream().mapToInt(Melon::getWeight).sum());

        out.println(melons.stream().map(Melon::getWeight).reduce(0, Integer::sum));

    }

    @Test
    public void filteringNonZero() {

        var ints = List.of(1, 2, -4, 0, 2, 0, -1, 14, 0, -1);

        ints.stream().filter(i -> i != 0)
                //.collect(Collectors.toList())
                .forEach(out::println);

        out.println("------------");

        ints.stream().filter(i -> i != 0)
                .distinct()
                .skip(1)
                .limit(2)
                .sorted()
                .forEach(out::println);

        out.println("------------");

        ints.stream().filter(v -> v > 0 && v < 10 && v % 2 == 0)
                .collect(Collectors.toList())
                .forEach(out::println);

    }

    @Test
    public void infiniteStream() {

        Stream.iterate(1, i -> i + 1)
                .filter(i -> i % 2 == 0)
                .limit(10)
                .forEach(out::println);

        var rdn = new Random();

        rdn.ints(1, 100).filter(i -> i % 2 == 0)
                .limit(10)
                .boxed()
                .collect(Collectors.toList())
                .forEach(out::println);

        rdn.ints(20, 48, 126)
                .mapToObj(n -> String.valueOf((char) n))
                .forEach(out::println);


    }

    @Test
    public void dropAndTakeWhile() {
        IntStream.iterate(1, i -> i <= 10, i -> i + 1)
                .boxed()
                .forEach(out::println);

        IntStream.iterate(1, i -> i + 1)
                .dropWhile(i -> i <= 10)
                .limit(5)
                .boxed()
                .forEach(out::println);
    }

    @Test
    public void usingMap() {
        milfMelons().stream().map(Melon::getType).sorted().forEach(out::println);
        milfMelons().stream().map(Melon::getWeight).sorted().forEach(out::println);

    }


    private List<Melon> milfMelons() {
        return List.of(new Melon(2000, "Gac"),
                new Melon(1600, "Hemi"),
                new Melon(3000, "Gac"),
                new Melon(2000, "Apollo"),
                new Melon(1700, "Horned"));
    }


}
