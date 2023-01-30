package model;

public class Person {
    protected Integer id;
    protected String name;

    public Integer getId() {
        return  this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return  this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
