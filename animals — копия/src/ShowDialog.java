import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

public class ShowDialog {
    JFrame frame = new JFrame();
    JButton addField = new JButton("Добавить");
    JButton consolePrint = new JButton("Вывести в консоль");
    JButton messagePrint = new JButton("Вывести в виде сообщения");
    String[] fields = {"id", "год выпуска", "Модель", "Марка", "Кондиционер", "Подушки безопасности", "Подогрев сидений"};
    List<JComboBox> comboBoxList = new ArrayList<>();
    List<JTextField> textFieldList = new ArrayList<>();
    GridBagConstraints where = new GridBagConstraints();
    ShowDialog(ActionsWithDB menu){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        where.gridx=0;
        where.gridy=0;
        addFields();
        where.gridy=100;
        frame.add(addField,where);
        where.gridy=101;
        frame.add(consolePrint);
        where.gridx=5;
        frame.add(messagePrint);
        where.gridx=0;
        where.gridy=1;
        addField.addActionListener(e -> addFields());
        consolePrint.addActionListener(e -> {

        });
        messagePrint.addActionListener(e -> {

        });
        frame.setVisible(true);
    }
    private void addFields(){
        comboBoxList.add(new JComboBox(fields));
        textFieldList.add(new JTextField());
        frame.add(comboBoxList.get(comboBoxList.size()-1), where);
        where.gridx=5;
        frame.add(textFieldList.get(textFieldList.size()-1), where);
        where.gridx=0;
        where.gridy++;
        //addListener to comboBox
    }
}
