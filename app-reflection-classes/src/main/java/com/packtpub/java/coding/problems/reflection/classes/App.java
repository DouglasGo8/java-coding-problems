package com.packtpub.java.coding.problems.reflection.classes;

import com.packtpub.java.coding.problems.common.domain.Car;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Arrays;

import static java.lang.System.out;

public class App {


    @Test
    public void inspectingPackages() {
        try {

            Class<?> clazz = Class.forName("java.lang.Integer");
            Package packageOfClass = clazz.getPackage();

            out.println(packageOfClass);

            var file = new File(".");
            Package packageOfFile = file.getClass().getPackage();

            out.println(packageOfFile.getName());

            Arrays.stream(Package.getPackages()).forEach(out::println);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void howToConstructors() throws NoSuchMethodException {

        Class<Car> clazz = Car.class;
        Constructor<Car> empty = clazz.getConstructor();
        out.println(empty);
    }
}
