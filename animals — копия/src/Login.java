import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Properties;

public class Login {
    JFrame frame = new JFrame("Подключение к БД");
    JLabel defisLabel = new JLabel("-");
    JLabel passLabel = new JLabel("Пароль:");
    JLabel serLabel = new JLabel("Адрес:");
    JLabel portLabel = new JLabel(":");
    JTextField adressField = new JTextField("localhost");
    JPasswordField passField = new JPasswordField("aaaaaaaaa");
    JButton access = new JButton("Войти");
    JPasswordField numPassField = new JPasswordField("00000000");
    JTextField portField = new JTextField("3306");
    GridBagConstraints where = new GridBagConstraints();
    Connection connection;
    Properties props = new Properties();
    private String coder(String passB, int passN) {
        final int codeC = 1597;
        String result = "";
        int[] code = {-30, 0, 18, 18, 0, 13, 3, 17, 0};
        for (int i = 0; i < passB.length(); i++) {
            result+=(char)(passB.charAt(i) + code[i%code.length]);
        }
        if (passN == 0){
            return result;
        }
        passN -= codeC;
        return result+Integer.toString(passN);
    }
    Login() {
        Font font = new Font("Segoe script", Font.ITALIC, 13);
        adressField.setFont(font);
        portField.setFont(font);
        Dimension size = new Dimension(70 ,20);
        frame.setSize(260, 120);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        where.gridx=0;
        where.gridy=0;
        where.gridwidth=3;
        frame.add(serLabel, where);
        where.gridy=2;
        frame.add(passLabel, where);
        where.gridx=3;
        where.gridwidth=6;
        passField.setPreferredSize(size);
        adressField.setPreferredSize(size);
        frame.add(passField, where);
        where.gridx=9;
        where.gridwidth=1;
        frame.add(defisLabel, where);
        where.gridx=12;
        where.gridwidth=6;
        frame.add(numPassField,where);
        where.gridy=0;
        where.gridwidth=4;
        frame.add(portField,where);
        where.gridwidth=1;
        where.gridx=9;
        frame.add(portLabel,where);
        where.gridx=8;
        frame.add(adressField,where);
        where.gridy=3;
        frame.add(access, where);
        access.addActionListener(e -> {
            try {
                props.setProperty("user", "root");
                props.setProperty("password", coder(String.valueOf(passField.getPassword()), Integer.parseInt(String.valueOf(numPassField.getPassword()))));
                connection = DriverManager.getConnection("jdbc:mysql://"+adressField.getText()+":"+portField.getText()+"/cars?serverTimezone=UTC", props);
                new Authorization(connection);
                frame.setVisible(false);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, throwables.getMessage());
            }
        });
        frame.setVisible(true);
    }
}