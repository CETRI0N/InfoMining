import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authorization {
    JLabel loginLabel = new JLabel("Логин:");
    JLabel passLabel = new JLabel("Пароль:");
    JTextField loginField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JFrame frame = new JFrame("Авторизация");
    JButton confirm = new JButton("Войти");
    Authorization(Connection connection) {
        frame.setSize(250,160);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints where = new GridBagConstraints();
        where.gridx=0;
        where.gridy=0;
        frame.add(loginLabel, where);
        where.gridy=1;
        frame.add(passLabel, where);
        where.gridx=1;
        Dimension size = new Dimension(60,20);
        passwordField.setPreferredSize(size);
        loginField.setPreferredSize(size);
        frame.add(passwordField,where);
        where.gridy=0;
        frame.add(loginField, where);
        where.gridy=2;
        frame.add(confirm, where);
        frame.setVisible(true);
        confirm.addActionListener(e->{
            try {
                ResultSet rs = connection.prepareStatement("SELECT `id`, `person` FROM `persons` WHERE `login` = '" + loginField.getText() + "' AND `pass` = " + String.valueOf(passwordField.getPassword()).hashCode()).executeQuery();
                if (rs.next()) {
                    ActionsWithDB actionsWithDB = new ActionsWithDB(connection);
                    String role = String.valueOf(rs.getString("person"));
                    if (role.equals("P")) {
                        new Worker(actionsWithDB, rs.getInt("id"));
                    } else
                    if (role.equals("M")) {
                        new Manager(actionsWithDB, rs.getInt("id"));
                    } else
                    if (role.equals("A")) {
                        new Admin(actionsWithDB, rs.getInt("id"));
                    }
                    frame.setVisible(false);
                } else{
                    JOptionPane.showMessageDialog(null, "Неверный логин или пароль!");
                }
            }
            catch (SQLException error){
                JOptionPane.showMessageDialog(null,error.getMessage());
            }
        });
    }
}
