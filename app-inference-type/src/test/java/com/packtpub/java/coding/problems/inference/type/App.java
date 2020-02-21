package com.packtpub.java.coding.problems.inference.type;

import org.junit.Test;

import java.util.Stack;
import static java.lang.System.out;

public class App {


    @Test
    public void howToStack() {
        var stack = new Stack<String>();
        stack.push("x");

        stack.forEach(out::println);
    }
}
