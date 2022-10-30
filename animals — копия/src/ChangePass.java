import javax.swing.*;
import java.awt.*;

public class ChangePass implements Consts{
    JFrame frame = new JFrame("Смена пароля");
    JLabel oldPassLabel = new JLabel("Введите старый пароль:");
    JLabel newPassLabel = new JLabel("Введите новый пароль:");
    JLabel repeatPassLabel = new JLabel("Повторите пароль:");
    JPasswordField oldPass = new JPasswordField();
    JPasswordField newPass = new JPasswordField();
    JPasswordField confPass = new JPasswordField();
    JButton confirm = new JButton("Подтвердить");
    GridBagConstraints where = new GridBagConstraints();
    ChangePass(ActionsWithDB menu, int id){
        frame.setSize(280,180);
        frame.setLayout(new GridBagLayout());
        where.gridy=0;
        where.gridx=0;
        frame.add(oldPassLabel,where);
        where.gridy=1;
        frame.add(newPassLabel,where);
        where.gridy=2;
        frame.add(repeatPassLabel,where);
        where.gridx=1;
        frame.add(confPass,where);
        where.gridy=1;
        frame.add(newPass, where);
        where.gridy=0;
        frame.add(oldPass,where);
        where.gridy=4;
        frame.add(confirm,where);
        newPass.setPreferredSize(DEFAULT_FIELD_SIZE);
        oldPass.setPreferredSize(DEFAULT_FIELD_SIZE);
        confPass.setPreferredSize(DEFAULT_FIELD_SIZE);
        confirm.addActionListener(e->{
            if(String.valueOf(newPass.getPassword()).equals(String.valueOf(confPass.getPassword())))
                if (String.valueOf(oldPass.getPassword()).hashCode()==Integer.parseInt(menu.getStringFromDB("persons","pass","id", Integer.toString(id)))) {
                    menu.setValue("persons", id, "pass", Integer.toString(String.valueOf(newPass.getPassword()).hashCode()));
                    JOptionPane.showMessageDialog(null, "Пароль изменён");
                    frame.setVisible(false);
                } else
                    JOptionPane.showMessageDialog(null, "Неправильный пароль");
            else
                JOptionPane.showMessageDialog(null, "Пароли не совпадают");
        });
        frame.setVisible(true);
    }
}
