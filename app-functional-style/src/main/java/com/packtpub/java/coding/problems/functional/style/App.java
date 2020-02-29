package com.packtpub.java.coding.problems.functional.style;

import com.packtpub.java.coding.problems.common.domain.Melon;
import org.junit.Test;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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

    @Test
    public void usingFlatMap() {

        Arrays.stream(this.milfsMelons()).forEach(out::println); // returns ...Melon;@7a3d45bd Hash instance
        Arrays.stream(this.milfsMelons()).map(Arrays::stream).forEach(out::println); // returns ...Stream<Stream<Melon>>;@76a3e297 Hash instance
        //
        out.println('\n');
        Arrays.stream(this.milfsMelons()).flatMap(Arrays::stream).forEach(out::println); // Stream<Melon>
        out.println('\n');
        Arrays.stream(this.milfsMelons()).flatMap(Arrays::stream).distinct().forEach(out::println); // Distinct Stream<Melon>
    }

    @Test
    public void usingFlatMapByList() {

        var melons = Arrays.asList
                (
                        Collections.singletonList(new Melon(2000, "Gac")),
                        Collections.singletonList(new Melon(1600, "Hemi")),
                        Collections.singletonList(new Melon(2000, "Gac")),
                        Collections.singletonList(new Melon(2000, "Apollo"))
                );

        melons.stream().map(Collection::stream).distinct().forEach(out::println); // same problem before mapped
        out.println('\n');
        melons.stream().flatMap(Collection::stream).distinct().forEach(out::println); // same problem before mapped

    }

    @Test
    public void findingElements() {

        var melons = Arrays.asList("Gac", "Cantaloupe", "Hemi", "Gac", "Gac", "Hemi",
                "Cantaloupe", "Horned", "Hemi", "Hemi");

        out.println(melons.parallelStream().findAny().orElse("nope")); // parallel without order
        out.println(melons.stream().findFirst().orElse("nope")); // keep order
        out.println(melons.parallelStream().filter(m -> m.equalsIgnoreCase("Apollo")).findFirst()
                .orElse("nope")); // keep order
        out.println(Stream.of(4, 8, 4, 5, 5, 7).map(x -> x * x - 1).filter(x -> x % 2 == 0).findFirst()
                .orElse(0));
        out.println(melons.stream().anyMatch(m -> m.equalsIgnoreCase("Gac"))); // trues (noneMatch|allMatch)
    }

    @Test
    public void sumMaxMin() {

        out.println(milfMelons().stream().mapToInt(Melon::getWeight).sum());
        out.println(milfMelons().stream().mapToInt(Melon::getWeight).max().orElse(-1));
        out.println(milfMelons().stream().mapToInt(Melon::getWeight).min().orElse(-1));

    }

    @Test
    public void reduce() {
        out.println(milfMelons().stream().mapToInt(Melon::getWeight).reduce(0, Integer::sum));
        out.println(milfMelons().stream().mapToInt(Melon::getWeight).reduce(Integer::max).orElse(-1));
        out.println(milfMelons().stream().mapToInt(Melon::getWeight).reduce(Integer::min).orElse(-1));
    }

    @Test
    public void collectionAsStream() {

        var list = milfMelons().stream().map(Melon::getWeight).filter(x -> x >= 1000).collect(Collectors.toList());

        var array = milfMelons().stream().map(Melon::getWeight).filter(x -> x >= 1000)
                .collect(Collectors.toCollection(ArrayList::new));

        var set = milfMelons().stream().map(Melon::getWeight).filter(x -> x >= 1000).collect(Collectors.toSet());

        var hashSet = milfMelons().stream().map(Melon::getWeight).filter(x -> x >= 1000)
                .collect(Collectors.toCollection(HashSet::new));

        var treeSet = milfMelons().stream().map(Melon::getWeight).filter(x -> x >= 1000)
                .collect(Collectors.toCollection(TreeSet::new));

        var map = milfMelons().stream().distinct().collect(Collectors.toMap(Melon::getType, Melon::getWeight));
        var map2 = milfMelons().stream().map(x -> Map.entry(new Random().nextInt(Integer.MAX_VALUE), x.getWeight()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        var map3 = milfMelons().stream().collect(Collectors.toMap(Melon::getType, Melon::getWeight, (o, n) -> o));

        var map4 = milfMelons().stream().sorted(Comparator.comparingInt(Melon::getWeight))
                .collect(Collectors.toMap(Melon::getType, Melon::getWeight, (o, n) -> o, LinkedHashMap::new));

        out.println(map4);


    }

    @Test
    public void joiningStream() {

        out.println(milfMelons()
                .stream()
                .map(Melon::getType)
                .distinct().sorted()
                .collect(Collectors.joining(",", "Available:", "Thank u")));
    }

    @Test
    public void summingAverageStream() {
        out.println(milfMelons().stream().collect(Collectors.summingInt(Melon::getWeight))); // replace by mapToInt and sum
        out.println(milfMelons().stream().collect(Collectors.averagingInt(Melon::getWeight)));

    }

    @Test
    public void groupingBy() {
        // Single Level Group
        out.println(milfMelons().stream().collect(Collectors.groupingBy(Melon::getType)));
        // reject duplicated or use distinct
        out.println(milfMelons().stream().collect(Collectors.groupingBy(Melon::getType, Collectors.toSet())));
        // ordered
        var mapped = milfMelons().stream()
                .collect(Collectors.groupingBy(Melon::getWeight, TreeMap::new, Collectors.toSet()));

        out.println(mapped);

        // {1600=[Hemi], 1700=[Horned], 2000=[Apollo, Gac], 3000=[Gac]}
        var mapped2 = milfMelons().stream()
                .collect(Collectors.groupingBy(Melon::getWeight, TreeMap::new,
                        Collectors.mapping(Melon::getType, Collectors.toSet())));

        var mapped3 = milfMelons().stream()
                .collect(Collectors.groupingBy(Melon::getWeight, TreeMap::new,
                        Collectors.mapping(Melon::getWeight, Collectors.counting())));

        out.println(mapped3);
        out.println(mapped2);

        // minBy - maxBy

        // Multi-level using Enum
        // milfMelons().stream().collect(Collectors.groupingBy(Melon::getSugar,
        //        Collectors.groupingBy(Melon::getWeight, TreeMap::new,
        //                Collectors.mapping(Melong::getType, Collectors.toSet())));

        // partitioning
        out.println(milfMelons().stream().collect(Collectors.partitioningBy(m -> m.getWeight() > 2000)));
    }

    @Test
    public void filteringWithGroup() {
        var group1 = milfMelons().stream().collect(Collectors.groupingBy(Melon::getType,
                Collectors.filtering(m -> m.getWeight() > 2000, Collectors.toSet()))); // with null
        var group2 = milfMelons().stream().filter(m -> m.getWeight() > 2000)
                .collect(Collectors.groupingBy(Melon::getType, Collectors.toSet())); // without null
        out.println(group1);
        out.println(group2);
    }

    @Test
    public void parallelStreamProcessing() {
        var numbers = Stream.of(1, 22, 333, 22, 321, 33, 223, 24313, 2, 67,32137, 982);
        out.println(numbers.parallel().reduce(Integer::sum).orElse(-1));
    }

    // Composing Comparators (comparing), predicates (and or) and functions (andThen)

    // jdk12 Teeing

    private Melon[][] milfsMelons() {
        return new Melon[][]{
                {
                        new Melon(2000, "Gac"),
                        new Melon(1600, "Hemi")
                },
                {
                        new Melon(2000, "Gac"),
                        new Melon(2000, "Apollo")
                },
                {
                        new Melon(1700, "Horned"),
                        new Melon(1600, "Hemi")
                },
        };
    }

    private List<Melon> milfMelons() {
        return List.of(new Melon(2000, "Gac"),
                new Melon(1600, "Hemi"),
                new Melon(3000, "Gac"),
                new Melon(2000, "Apollo"),
                new Melon(1700, "Horned"));
    }


}
