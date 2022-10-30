import javax.swing.*;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

public class RemoveDialog {
    JFrame frame = new JFrame();
    JButton confirm = new JButton("Удалить");
    JComboBox carToDel;
    RemoveDialog(ActionsWithDB menu, int id){
        frame.setSize(200, 100);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        if (id==0) {
            carToDel = new JComboBox(menu.getCarArray(id));
            confirm.addActionListener(e -> JOptionPane.showMessageDialog(null, menu.removeCar(carToDel.getSelectedIndex() + 1)));
        }
        else{
            String[] info = {"make", "model", "car_id"};
            List<String[]> idshki = menu.getArrayFromDB("cars", info, "person_id", id);
            info = new String[idshki.size()];
            for (int i = 0; i < info.length; i++) {
                info[i] = idshki.get(i)[0]+" "+idshki.get(i)[1];
            }
            carToDel = new JComboBox(info);
            confirm.addActionListener(e -> JOptionPane.showMessageDialog(null, menu.removeCar(Integer.parseInt(idshki.get(carToDel.getSelectedIndex())[2]))));
        }
        frame.add(carToDel);
        frame.add(confirm);
        frame.setVisible(true);
    }
}
