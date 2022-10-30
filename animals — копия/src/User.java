import javax.swing.*;
import java.awt.*;

public abstract class User {
    JFrame frame = new JFrame();
    private int id;
    private String login;
    private String password;
    User(){
        frame.setLayout(new GridBagLayout());
        frame.setSize(300, 260);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
