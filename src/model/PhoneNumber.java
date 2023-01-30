package model;

public class PhoneNumber {
    protected Person person;

    protected String number;

    protected String description;



    public PhoneNumber(Person person, String number, String description) {
        this.person = person;
        this.number = number;
        this.description = description;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Номер %s | Имя: %s | Описание: %s |",
                this.number, this.person, this.description);
    }
}
