package com.packtpub.java.coding.problems.common.domain;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Date;

public final class User {

    private final String nickName;
    private final String password;

    private final String firstname;
    private final String lastName;

    private final String email;
    private final Date created;

    private User(UserBuilder builder) {
        this.nickName = builder.nickName;
        this.password = builder.password;
        this.firstname = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.created = builder.created;
    }

    public static UserBuilder getBuilder(String nickName, String password) {
        return new User.UserBuilder(nickName, password);
    }

    public static final class UserBuilder {
        @Getter
        @NotNull(message = "cannot be null")
        private final String nickName;
        @Getter
        private final String password;

        @Getter
        private final Date created;
        private String email;
        private String firstName;
        private String lastName;

        public UserBuilder(String nickName, String password) {
            this.nickName = nickName;
            this.password = password;
            this.created = new Date();
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
