import javax.swing.*;
import java.awt.*;

public class AddDialog implements Consts {
    JFrame frame = new JFrame();
    JButton confirm = new JButton("Добавить");
    JTextField description = new JTextField("Описание");
    JTextField header = new JTextField("Заголовок");

    GridBagConstraints where = new GridBagConstraints();
    AddDialog(ActionsWithDB menu, int userId, boolean rights){
        Dimension size = new Dimension(100, 20);
        description.setPreferredSize(DEFAULT_FIELD_SIZE);
        header.setPreferredSize(DEFAULT_FIELD_SIZE);
        JComboBox menushka = new JComboBox(menu.getWorkersArray(userId));
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        where.gridx=0;
        where.gridy=0;
        frame.add(description, where);
        where.gridx=5;
        frame.add(header, where);
        where.gridx=10;
        frame.add(menushka, where);
        where.gridx=12;
        where.gridy=6;
        frame.add(confirm, where);
        if(rights){
            JButton checkId = new JButton("найти");
            JCheckBox checkOwner = new JCheckBox("Владелец", true);
            checkOwner.addActionListener(e -> {

                checkId.setVisible(checkOwner.isSelected());
            });
            where.gridy=5;
            where.gridx=0;
            frame.add(checkOwner, where);

            where.gridx=10;
            frame.add(checkId, where);
            checkId.addActionListener(e -> {});
        }

        confirm.addActionListener(e -> {
            try {
                int id;



                if (userId < 0) {
                     throw new IllegalArgumentException("Ошибка при работе c БД");
                }

                JOptionPane.showMessageDialog(null, "Автомобиль добавлен");
            } catch (IllegalArgumentException exception){
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        });
        frame.setVisible(true);
    }
}