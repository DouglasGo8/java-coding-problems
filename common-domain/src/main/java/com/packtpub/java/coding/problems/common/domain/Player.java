package com.packtpub.java.coding.problems.common.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;

import java.util.Objects;


@AllArgsConstructor
public class Player {

    private int id;
    private String name;

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (null == obj)
            return false;
        if (getClass() != obj.getClass())
            return false;

        final Player player = (Player) obj;



        if (this.id == player.id)
            return true;

        if (!Objects.equals(this.name, player.name))
            return false;


        return true;
    }


    @Override
    public int hashCode() {

        int hash = 7;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.name);

        return hash;
    }
}
