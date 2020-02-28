package com.packtpub.java.coding.problems.collection.data;

import com.packtpub.java.coding.problems.common.domain.Melon;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class App {

    @Test
    public void sortOverJdk8() {

        // All sort will be ASC
        var numbers = new int[]{1, 3, 4, 2, 7, 12, 10, 5};
        Arrays.sort(numbers);

        out.println(Arrays.toString(numbers));

        var melons = new Melon[]{new Melon(2, "Melon1"), new Melon(1, "Melon2")};

        Arrays.sort(melons, Comparator.comparingInt(Melon::getWeight));

        out.println(Arrays.toString(melons));

        Arrays.parallelSort(melons, Comparator.comparing(Melon::getWeight));

        out.println(Arrays.toString(melons));

        // All sort will be DESC
        Arrays.sort(melons, (m1, m2) -> (-1) * Integer.compare(m1.getWeight(), m2.getWeight()));
        Arrays.parallelSort(melons, (m1, m2) -> (-1) * Integer.compare(m1.getWeight(), m2.getWeight()));

        out.println(Arrays.toString(melons));

        var ofNums = new Integer[]{1, 3, 4, 2, 33, 12};

        Arrays.parallelSort(ofNums, Collections.reverseOrder());

        out.println(Arrays.toString(ofNums));


    }

    @Test
    public void sortOverJdk8WithBench() {

        // Bubble Sort
        // Insertion Sort
        // Counting Sort
        // Heap Sort
        // Quick Sort

    }

    @Test
    public void findElementInArray() {

        var numbers = new int[]{4, 5, 1, 3, 7, 4, 1};
        var melons = new Melon[]{new Melon(2000, "Crenshaw"),
                new Melon(1200, "Gac"),
                new Melon(2200, "Bitter")};

        Arrays.parallelSort(numbers); // sort
        out.println(Arrays.binarySearch(numbers, 3));

        out.println(containsElem(melons, melons[1]));

    }

    @Test
    public void checkOnlyForFirstIndex() {
        var numbers = new int[]{4, 5, 1, 3, 7, 4, 1};
        var index = IntStream.range(0, numbers.length)
                .filter(i -> 1 == numbers[i])
                .findFirst()
                .orElse(-1);
        out.println(index);
    }

    @Test
    public void arraysAreEquals() {

        var numbers1 = new int[]{3, 4, 5, 6, 1, 5};
        var numbers2 = new int[]{3, 4, 5, 6, 1, 5};
        var melons1 = new Melon[]{new Melon(2, "Melon1"), new Melon(1, "Melon2")};
        var melons2 = new Melon[]{new Melon(2, "Melon1"), new Melon(1, "Melon2")};

        out.println(Arrays.equals(numbers1, numbers2));
        out.println(Arrays.equals(melons1, melons2));
    }

    @Test
    public void checkingWhetherTwoArraysContainMismatch() {
        var numbers1 = new int[]{3, 4, 5, 6, 1, 5};
        var numbers2 = new int[]{3, 4, 5, 6, 1, 5};

        var result = Arrays.mismatch(numbers1, numbers2);

        out.println(result); // -1 are equal

    }

    @Test
    public void createStreamOfArray() {

        String[] numbers = {"One", "Two", "Three", "Four", "Five"};

        Arrays.stream(numbers).forEach(out::println);

        int[] integers = {2, 3, 4, 8};

        IntStream.of(integers).forEach(out::println);

    }

    @Test
    public void minMaxAverageOfArray() {

        int[] numbers = {2, 3, 4, 1, -4, 6, 2};

        var max = Arrays.stream(numbers).max().getAsInt();
        var average = Arrays.stream(numbers).average().getAsDouble();
        out.println(max);
        out.println(average);
    }

    @Test
    public void reversedArray() {

        int[] numbers = {2, 3, 4, 1, -4, 6, 2};
        int[] reversed = IntStream.rangeClosed(1, numbers.length).map(i -> numbers[numbers.length - i]).toArray();

        out.println(Arrays.toString(reversed));

        Melon[] melons = {
                new Melon(2000, "Crenshaw"),
                new Melon(1200, "Gac"),
                new Melon(2200, "Bitter")
        };

        var reversedMelon = IntStream.rangeClosed(1, melons.length)
                .mapToObj(i -> melons[melons.length - i])
                .toArray(Melon[]::new);

        out.println(Arrays.toString(reversedMelon));

    }

    @Test
    public void fillArray() {
        int[] arr = new int[10];
        Arrays.fill(arr, 1);
        out.println(Arrays.toString(arr));

        Collections.unmodifiableCollection(Arrays.asList(1, 2, 3)).forEach(out::println);

        var mapOfMelon = Map.ofEntries(Map.entry(1, new Melon(1, "Old")));

        out.println(mapOfMelon);

    }

    @Test
    public void computeAbsentPresentOfMap() {

        var map = new HashMap<String, String>();
        map.put("postgresql", "127.0.0.1");
        map.put("mysql", "192.168.0.50");

        out.println(map);

        // BiFunction<String, String, String> by Key - Value -> Result
        out.println(map.computeIfPresent("mysql", (k, v) -> "jdbc:" + k + "://" + v + "/some_db"));
        // Function<String, String>
        out.println(map.computeIfAbsent("mongodb", (k) -> k + "://localhost/customer_db"));
        // BiFunction<String, String, String> by Key - Value -> Result
        out.println(map.compute("mysql", (k, v) -> "jdbc:" + k + "://" + ((v == null) ? "localhost" : v) + "/customer_db"));

    }

    @Test
    public void mapMergeOf() {

        var map = new HashMap<String, String>();

        map.put("auUrl", "http://api.au/com");
        map.put("pepUrl", "http://api.pep/com");

        out.println(map.merge("auUrl", "/ssn", String::concat));

        map.putIfAbsent("noExists", "xpto");

        out.println(map);
        map.remove("noExits");
        out.println(map);
    }

    @Test
    public void mapReplaceOf() {

        var mapOfMelon = new HashMap<Integer, Melon>();

        mapOfMelon.put(1, new Melon(3000, "Apollo"));
        mapOfMelon.put(2, new Melon(3500, "Jade Dew"));
        mapOfMelon.put(3, new Melon(1500, "Cantaloupe"));

        out.println(mapOfMelon.replace(2, new Melon(1000, "Gac")));

        mapOfMelon.replaceAll((k, v) -> v.getWeight() > 1000 ? new Melon(1000, v.getType()) : v);

        out.println(mapOfMelon);

    }

    @Test
    public void mapRemoveAllElements() {

        var melons = new ArrayList<Melon>();

        melons.add(new Melon(3000, "Apollo"));
        melons.add(new Melon(3500, "Jade Dew"));
        melons.add(new Melon(1500, "Cantaloupe"));
        melons.add(new Melon(1600, "Gac"));
        melons.add(new Melon(1400, "Hami"));

        melons.removeIf(t -> t.getWeight() < 3000);

        out.println(melons);

        melons.stream().filter(t -> t.getWeight() >= 3000)
                .collect(Collectors.toList())
                .forEach(out::println);

    }

    @Test
    public void partitioningBy() {

        var melons = new ArrayList<Melon>();

        melons.add(new Melon(3000, "Apollo"));
        melons.add(new Melon(3500, "Jade Dew"));
        melons.add(new Melon(1500, "Cantaloupe"));
        melons.add(new Melon(1600, "Gac"));
        melons.add(new Melon(1400, "Hami"));


        var split = melons.stream().collect(Collectors.partitioningBy(t -> t.getWeight() >= 3000));

        out.println(split.get(false));
    }


    @Test
    public void concurrentThreadSageList() {
        // Single-thread - Multi-threaded
        // ArrayList - CopyOnWriteArrayList
        // LinkedList - Vector

        var queue = new ConcurrentLinkedDeque<Integer>();
        var blockQueue2 = new PriorityBlockingQueue<Integer>();
        var blockingQueue = new ArrayBlockingQueue<Integer>(100);
        var synchronous  = new SynchronousQueue<Integer>();
        var syncList = Collections.synchronizedList(new ArrayList<Integer>());
        var syncMap = Collections.synchronizedMap(new HashMap<Integer, Integer>());
    }

    private <T> boolean containsElem(T[] arr, T toContain) {
        for (T elem : arr) {
            if (elem.equals(toContain))
                return true;
        }
        return false;
    }

}
