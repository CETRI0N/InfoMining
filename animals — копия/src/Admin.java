import javax.swing.*;

public class Admin extends Manager{
    JButton addCars = new JButton("Привезти машину");
    JButton removeCars = new JButton("Увезти машину");
    Admin(ActionsWithDB menu, int id){
        super(menu, id);
        frame.setTitle("Администратор");
        where.gridy=7;
        where.gridx=0;
        frame.add(addCars,where);
        where.gridx=1;
        frame.add(removeCars, where);
        addCars.addActionListener(e -> new AddDialog(menu, id, true));
        removeCars.addActionListener(e -> new RemoveDialog(menu, id));
    }
}