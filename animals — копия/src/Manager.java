import javax.swing.*;
import java.awt.*;

public class Manager implements Consts{
    String[] roles = {"Mенеджер", "Pабочий"};
    JFrame frame = new JFrame("Менеджер");
    JTextField nameField = new JTextField("ФИО");
    JTextField loginField = new JTextField("Логин");
    JComboBox roleBox = new JComboBox(roles);
    JPasswordField passwordField = new JPasswordField("Пароль");
    JPasswordField repeatPassField = new JPasswordField("Повторите пароль");
    JButton register = new JButton("Зарегистрировать");
    JButton delete = new JButton("Удалить");
    JButton change = new JButton("Удалить");
    JButton check = new JButton("Проверить");
    JButton changePassButton = new JButton("Изменить пароль");
    GridBagConstraints where = new GridBagConstraints();
    Manager(ActionsWithDB menu, int id){
        where.gridy=0;
        where.gridx=1;
        nameField.setPreferredSize(DEFAULT_FIELD_SIZE);
        loginField.setPreferredSize(DEFAULT_FIELD_SIZE);
        passwordField.setPreferredSize(DEFAULT_FIELD_SIZE);
        repeatPassField.setPreferredSize(DEFAULT_FIELD_SIZE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(300, 260);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(changePassButton, where);
        where.gridx=0;
        frame.add(nameField,where);
        where.gridy=1;
        frame.add(loginField, where);
        where.gridy=2;
        frame.add(passwordField, where);
        where.gridy=3;
        frame.add(repeatPassField, where);
        where.gridy=4;
        frame.add(change, where);
        where.gridx=1;
        frame.add(register, where);
        frame.add(delete, where);
        delete.setVisible(false);
        where.gridy=1;
        frame.add(roleBox, where);
        frame.add(check, where);
        check.setVisible(false);
        frame.setVisible(true);
        changePassButton.addActionListener(e -> new ChangePass(menu, id));
        change.addActionListener(e->{
            boolean actionTool = check.isVisible();
            nameField.setVisible(actionTool);
            passwordField.setVisible(actionTool);
            repeatPassField.setVisible(actionTool);
            register.setVisible(actionTool);
            delete.setVisible(!actionTool);
            check.setVisible(!actionTool);
            roleBox.setVisible(actionTool);
            if (actionTool)
                change.setText("Удалить");
            else
                change.setText("Зарегистрировать");
        });
        delete.addActionListener(e -> {
            menu.removeData("persons", Integer.parseInt(menu.getStringFromDB("persons", "id" , "login", loginField.getText())));
            JOptionPane.showMessageDialog(null, "Удалено");
        });
        check.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, menu.getStringFromDB("persons", "name", "login", loginField.getText()));
        });
        register.addActionListener(e -> {
            if (String.valueOf(passwordField.getPassword()).equals(String.valueOf(repeatPassField.getPassword()))) {
                String role = String.valueOf(roleBox.getSelectedItem());
                menu.addUser(role.toCharArray()[0], loginField.getText(), String.valueOf(passwordField.getPassword()).hashCode(), nameField.getText(), id);
                JOptionPane.showMessageDialog(null,roleBox.getSelectedItem()+" добавлен");
            } else
                JOptionPane.showMessageDialog(null,"Пароли не совпадают");
        });
    }
}
