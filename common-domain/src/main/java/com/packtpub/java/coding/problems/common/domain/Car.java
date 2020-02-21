package com.packtpub.java.coding.problems.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class Car {

    @NotNull(message = "Car name cannot be null")
    private final String name;
    @NotNull(message = "Car Color cannot be null")
    private final Color color;

    public void assignMe(@NotNull(message = "License cannot be null") String license) {
        System.out.println(license);
    }

    @Override
    public String toString() {
        return "{\"name\": " + "\"" + name + "\"}";
    }
}
