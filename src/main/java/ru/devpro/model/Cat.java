package ru.devpro.model;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
    @Table(name = "cats")
    public class Cat {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private  String name;
        private  String breed;
        private  String volunteer;
        private  String owner;

        public Cat() {
        }

        public Cat(String name, String breed, String volunteer, String owner) {
            this.name = name;
            this.breed = breed;
            this.owner = owner;
            this.volunteer = volunteer;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBreed() {
            return breed;
        }

        public void setBreed(String breed) {
            this.breed = breed;
        }

        public String getVolunteer() {
            return volunteer;
        }

        public void setVolunteer(String volunteer) {
            this.volunteer = volunteer;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return id == cat.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", volunteer='" + volunteer + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}

