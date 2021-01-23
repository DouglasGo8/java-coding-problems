package com.packtpub.java.coding.problems.optional;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.lang.System.out;

public class App {

    @Test
    public void bestPractices() {
        class Foo {}
        // avoid
        Optional<Foo> foo = null;
        // prefer
        Optional<Foo> foo2 = Optional.empty();

        // avoid
        foo2.get();

        if (foo2.isPresent()) {
            // take action here
            out.println("HI");
        }

        // prefer
        foo2.orElseGet(Foo::new);
        // prefer
        foo2.orElseThrow(()-> new NoSuchElementException("Fail"));
        // jdk 10+
        foo2.orElseThrow();

        // if (foo2.isPresent())
        // foo2.ifPresent(out::println);

        /*books.stream()
                .filter(b -> b.getPrice() < price)
                .findFirst()
                .map(Book::getName)
                .orElse(NOT_FOUND)*/



    }


}
