package com.packtpub.java.coding.problems.immutability.objects;

import com.packtpub.java.coding.problems.common.domain.Car;
import com.packtpub.java.coding.problems.common.domain.Color;
import com.packtpub.java.coding.problems.common.domain.Player;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import static java.lang.System.out;

public class App {


    @Test
    public void checkNullRef() {

        var numbers = Arrays.asList(1, 2, null, 4, null);

        var result = numbers.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).sum();

        out.println(result);

    }

    @Test
    public void checkNullInCarObject() {

        var car = new Car("Tiguan", Color.GRAY);
        out.println(car);
    }

    @Test
    public void equalAndHashCode() {

        var p1 = new Player(1, "Rafael Nadal");
        var p2 = new Player(1, "Rafael Nadal");

        var players = new HashSet<>();

        players.add(p1);
        players.add(p2);

        out.println(players.contains(new Player(1, "Rafael Nadal")));

        out.println(p1.equals(p2));
        out.println(p1.hashCode());
        out.println(p2.hashCode());

    }

    @Test
    public void stringIsReallyImmutable() throws Exception {
        String user = "guest";
        out.println("User is of type:" + user);

        Class<String> type = String.class;
        Field field = type.getDeclaredField("value");
        field.setAccessible(true);
        char[] chars = (char[]) field.get(user);
        chars[0] = 'x';

        out.println("User is of type:" + user);
    }
}
