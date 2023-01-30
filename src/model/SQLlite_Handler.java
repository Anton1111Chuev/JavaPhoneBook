package model;

import java.sql.*;
import java.util.*;

public class SQLlite_Handler {
    private Connection conn;
    private final static String url = "jdbc:sqlite:db/phone.db3";

    private HashMap<String, Integer> levels = new HashMap<>();

    private static SQLlite_Handler instance = null;

    public static synchronized SQLlite_Handler getInstance() throws SQLException {
        if (instance == null)
            instance = new SQLlite_Handler();
        return instance;
    }

    private Connection connection;

    private SQLlite_Handler() throws SQLException {

        this.connection = DriverManager.getConnection(url);
    }


    public List<PhoneNumber> getAllContact() {

        try (Statement statement = this.connection.createStatement()) {

            List<PhoneNumber> numbers = new ArrayList<PhoneNumber>();

            ResultSet resultSet = statement.executeQuery("select a.number, a.description, a.person, b.name from numbers as a inner join person as b on a.person = b.id");
            // Проходимся по нашему resultSet и заносим данные
            while (resultSet.next()) {
                numbers.add(new PhoneNumber( new Person(resultSet.getInt("person"), resultSet.getString("name")),
                        resultSet.getString("number"),
                        resultSet.getString("description")));
            }
            // Возвращаем наш список
            return numbers;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Person> getAllPerson(){
        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("select id, name from person");

            List<Person> persons = new ArrayList<Person>();
            while (resultSet.next()) {
               persons.add(new Person(resultSet.getInt("id"),
                       resultSet.getString("name")));
            }
            return persons;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Integer getIDPersonByName(String name){

        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("select id from person where name = '" + name + "'" );

            while (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    public void addNumber(PhoneNumber model) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO numbers('number', 'person', 'description') " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, model.getNumber());
            statement.setObject(2, model.getPerson().getId());
            statement.setObject(3, model.getDescription());
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
