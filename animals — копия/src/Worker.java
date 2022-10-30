import javax.swing.*;
import java.awt.*;

public class Worker{
    ActionsWithDB menu;
    JFrame frame = new JFrame("Рабочий");
    String[] equipment = {"добыть руду", "починить проводку", "добыть уголь"};
    JButton continueButton = new JButton("Задача выполнена");

    JButton editComment = new JButton("Прочесть описание");
    JButton changePassButton = new JButton("Изменить пароль");

    JComboBox equipmentToChoose = new JComboBox(equipment);

    JButton statusLook = new JButton("Изменить пароль");

    GridBagConstraints where = new GridBagConstraints();
    Worker(ActionsWithDB menu, int id){
        this.menu = menu;
        ButtonGroup bg = new ButtonGroup();


        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        where.gridx=0;

        where.gridy=1;
        frame.add(equipmentToChoose, where);

        where.gridx=1;
        frame.add(editComment, where);
        where.gridy=0;
        where.gridx=3;
        frame.add(changePassButton, where);
        where.gridy=3;
        frame.add(continueButton, where);
        where.gridy=1;


        editComment.addActionListener(e -> {
            JFrame windowComment = new JFrame("Комментарий");
            JTextArea comment = new JTextArea();
            JButton button = new JButton("Подтвердить");
            windowComment.setSize(600, 400);
            windowComment.setLayout(new FlowLayout());
            comment.setLineWrap(true);
            comment.setWrapStyleWord(true);
            windowComment.add(comment);
            windowComment.add(button);
            button.addActionListener(e1 -> {

                windowComment.setVisible(false);
            });
            windowComment.setVisible(true);
        });

        changePassButton.addActionListener(e -> new ChangePass(menu, id));


        frame.setVisible(true);
    }
}



