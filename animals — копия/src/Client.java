import javax.swing.*;
import java.awt.*;

public class Client {
    JFrame window = new JFrame("Клиент");
    JButton buttonAdd = new JButton("Добавить машину");
    JButton buttonDel = new JButton("Удалить машину");
    JButton buttonPass = new JButton("Изменить пароль");
    GridBagConstraints where = new GridBagConstraints();
    Client(ActionsWithDB menu, int id){
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridBagLayout());
        window.setSize(500, 100);
        where.gridx = 0;
        where.gridy = 0;
        window.add(buttonAdd, where);
        where.gridx = 1;
        window.add(buttonDel, where);
        where.gridx = 2;
        window.add(buttonPass, where);
        window.setVisible(true);
        buttonAdd.addActionListener(e -> new AddDialog(menu, id, false));
        buttonDel.addActionListener(e -> new RemoveDialog(menu, id));
        buttonPass.addActionListener(e -> new ChangePass(menu, id));
    }
}
