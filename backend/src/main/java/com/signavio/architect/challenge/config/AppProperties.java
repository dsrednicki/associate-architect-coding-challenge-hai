package com.signavio.architect.challenge.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private List<UserProperties> users;

    public List<UserProperties> getUsers() {
        return users;
    }

    public void setUsers(final List<UserProperties> users) {
        this.users = users;
    }

    public static class UserProperties {
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String roles;

        public String getUsername() {
            return username;
        }

        public void setUsername(final String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(final String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(final String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(final String lastName) {
            this.lastName = lastName;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(final String roles) {
            this.roles = roles;
        }
    }
}
