package controller;

import model.Person;
import model.PhoneNumber;
import model.SQLlite_Handler;
import view.BaseView;
import view.VeiwInterface;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class Controller {


    private VeiwInterface dialog;

    public void  addNumber(String name, String number, String description) throws SQLException, ParseException {

        SQLlite_Handler handler = SQLlite_Handler.getInstance();

        Person person = new Person( handler.getIDPersonByName(name), name);

        PhoneNumber phoneNumber = new PhoneNumber(person, number, description);

        handler.addNumber(phoneNumber);

        this.getAllData();
    }

    public void  getAllData() throws SQLException {

        SQLlite_Handler handler = SQLlite_Handler.getInstance();

        List<PhoneNumber> numbers = handler.getAllContact();

        String text = "";

        for ( PhoneNumber number: numbers) {
            text = text + number.toString() + "\n";
        }
        this.dialog.setTextPanel(text);

    }

    public void setText(String text){
        this.dialog.setTextPanel(text);
    }

    public Controller() throws SQLException, ParseException {

        dialog = new BaseView(this);

        this.getAllData();

        SQLlite_Handler handler = SQLlite_Handler.getInstance();

        List<Person> persons = handler.getAllPerson();

        String text = "";

        for ( Person person: persons) {
            dialog.addItem(person.toString());
        }

        dialog.showFinalAction();
    }
}
