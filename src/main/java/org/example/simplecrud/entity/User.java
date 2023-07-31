package org.example.simplecrud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Comparable {
    private int id;
    private String email;
    private String phoneNumber;
    private int password;
    private String firstName;
    private String surname;
    private DetailInformation detailInformation;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public int compareTo(Object o) {
        User user = (User) o;
        return Integer.compare(this.getId(), user.getId());
    }

    public static class UserBuilder {
        private int id;
        private String email;
        private String phoneNumber;
        private int password;
        private String firstName;
        private String surname;
        private DetailInformation detailInformation;

        UserBuilder() {
        }

        public UserBuilder id(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder password(int password) {
            this.password = password;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder detailInformation(DetailInformation detailInformation) {
            this.detailInformation = detailInformation;
            return this;
        }

        public User build() {
            return new User(this.id, this.email, this.phoneNumber, this.password, this.firstName, this.surname, this.detailInformation);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", email=" + this.email + ", phoneNumber=" + this.phoneNumber + ", password=" + this.password + ", firstName=" + this.firstName + ", surname=" + this.surname + ", detailInformation=" + this.detailInformation + ")";
        }
    }
}
