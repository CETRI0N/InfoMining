import com.sun.jdi.connect.spi.Connection;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            props.setProperty("user", "root");
            props.setProperty("password", "pass");
            new Authorization(DriverManager.getConnection("jdbc:mysql://localhost:3306/cars?serverTimezone=UTC", props));
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, throwables.getMessage());
        }
    }
}
