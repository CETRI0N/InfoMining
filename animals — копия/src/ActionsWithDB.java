import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActionsWithDB implements Consts{
    Connection conn;
    JFrame frame = new JFrame();
    JButton showData = new JButton("Вывести");
    ActionsWithDB(Connection currentConnection){
        conn = currentConnection;
        showData.addActionListener(e -> {
            try{
            String result = "";
            ResultSet rs = conn.prepareStatement("SELECT `car_id` FROM `cars` ORDER BY `car_id` DESC LIMIT 1").executeQuery();
            rs.next();
            int maxid = rs.getInt(1);
            int id = 1;
            while (id<maxid){
                String car = getCarNameByID(id);
                if(car.equals(" года") || car.equals(""))
                    break;
                car = "Автомобиль "+car;
                boolean anything = false;
                rs = conn.prepareStatement("SELECT power FROM ac WHERE car_id = " + id).executeQuery();
                String extra = "";
                if(rs.next()) {
                    anything = true;
                    extra = "кондиционером мощностью "+rs.getString(1)+"Вт";
                }
                rs = conn.prepareStatement("SELECT number, manufacturer FROM srs WHERE car_id = " + id).executeQuery();
                if (rs.next()){
                    if (anything)
                            extra=extra+", ";
                    anything=true;
                    extra=extra+"подушками безопасности "+rs.getString(2)+" ("+rs.getString(1)+" шт)";
                }
                rs = conn.prepareStatement("SELECT power, number FROM seats WHERE car_id = " + id).executeQuery();
                if (rs.next()){
                    if (anything)
                        extra=extra+", ";
                    anything=true;
                    extra=extra+"сиденьями с подогревом мощностью "+rs.getString(1)+"Вт ("+rs.getString(2)+" шт)";
                }
                if (anything)
                    result+=car+" Оборудован "+extra;
                else
                    result+=car;
                result+="\n";
                id++;
            }
            JOptionPane.showMessageDialog(null, result);
            } catch (SQLException sqlException){
                JOptionPane.showMessageDialog(null, sqlException.getMessage());
            }
        });
    }
    public String[] getCarArray(int id){
        String[] toReturn = new String[0];
        String[] cars = new String[MAX_CARS];
        try{
            ResultSet rs = conn.prepareStatement("SELECT id, header FROM tasks WHERE idWorker="+id).executeQuery();
            int i = 0;
            while(rs.next()){
                cars[i]=rs.getString(0);
                i++;
            }
            toReturn = new String[i];
            for (i = 0; i < toReturn.length; i++) {
                toReturn[i]=cars[i];
            }
        } catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
        return toReturn;
    }
    public String[] getWorkersArray(int id) {
        String[] toReturn = new String[0];
        String[] cars = new String[MAX_CARS];
        try {
            ResultSet rs = conn.prepareStatement("SELECT id, name FROM persons WHERE idMaster="+id).executeQuery();
            int i = 0;
            while (rs.next()) {
                cars[i] = rs.getString(0)+ " " + rs.getString(1);
                i++;
            }
            toReturn = new String[i];
            for (i = 0; i < toReturn.length; i++) {
                toReturn[i] = cars[i];
            }
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
        return toReturn;
    }

    public String getCarNameByID(int id){
        String toReturn="";
        try {
            ResultSet rs = conn.prepareStatement("SELECT make, model, year FROM cars WHERE car_id = " + id).executeQuery();
            rs.next();
            toReturn = rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" года";
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
        return toReturn;
    }
    public int addNewCar(String make, String model, int year, int ownerId){
        int id = addNewCar(make, model, year);
        try {
            conn.prepareStatement("UPDATE cars SET person_id = " + ownerId + " WHERE car_id = " + id).executeUpdate();
        } catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
        return id;
    }
    public int addNewCar(String make, String model, int year){
        int id=-1;
        try{
            conn.setAutoCommit(false);
            ResultSet rs = conn.prepareStatement("SELECT `car_id` FROM `cars` ORDER BY `car_id` DESC LIMIT 1").executeQuery();
            rs.next();
            id = rs.getInt(1)+1;
            conn.prepareStatement("INSERT INTO cars(car_id, make, model, year, status) VALUES("+id+", '"+make+"', '"+model+"', "+year+", 'Машина привезена')").executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
        return id;
    }
    public void addAc(int id, int power){
        try{
        conn.prepareStatement("INSERT INTO ac(car_id, power) VALUES("+id+", "+power+")").executeUpdate();
        } catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
    }
    public void addAirbag(int id, int num, String manufacturer){
        try{
            conn.prepareStatement("INSERT INTO srs(car_id, number, manufacturer) VALUES("+id+", "+num+", '"+manufacturer+"')").executeUpdate();
        } catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
    }
    public void addSeats(int id, int num, int power){
        try{
            conn.prepareStatement("INSERT INTO seats(car_id, power, number) VALUES("+id+", "+power+", "+num+")").executeUpdate();
        } catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
    }
    public void setValue(String table, int id, String column, String value){
        try{
            if (table.equals("persons")) {
                conn.prepareStatement("UPDATE "+table+" SET "+column+" = '"+value+"' WHERE id = "+id).executeUpdate();
            }else{
                conn.prepareStatement("UPDATE "+table+" SET "+column+" = '"+value+"' WHERE car_id = "+id).executeUpdate();
            }
        } catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
    }
    public String removeCar(int id){
        String toReturn = getCarNameByID(id);
        try{
            conn.setAutoCommit(false);
            String[] array = {"ac", "seats", "srs", "cars"};
            for (String i:array) {
                removeData(i, id);
            }
            conn.commit();
            conn.setAutoCommit(true);
            toReturn = "Машина " + toReturn + " удалена";
        } catch (SQLException sqlException){
            toReturn = "Ошибка!";
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
        return toReturn;
    }
    public void deleteString(String table, int id){
        try {
                conn.prepareStatement("DELETE FROM " + table + " WHERE car_id = " + id).executeUpdate();
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    public void removeData(String table, int id){
        try {
           if (table.equals("persons")) {
                conn.prepareStatement("DELETE FROM " + table + " WHERE id = " + id).executeUpdate();
                conn.prepareStatement("UPDATE " + table + " SET id = id-1 WHERE id > " + id).executeUpdate();
            } else {
               conn.prepareStatement("DELETE FROM " + table + " WHERE car_id = " + id).executeUpdate();
               conn.prepareStatement("UPDATE " + table + " SET car_id = car_id-1 WHERE car_id > " + id).executeUpdate();
           }
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    public String getStringFromDB(String table, String column, String where, String value) {
        try {
            ResultSet rs = conn.prepareStatement("SELECT " + column + " FROM " + table + " WHERE " + where + " = '" + value+"'").executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } else
                throw new NullPointerException();
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
        return "Ошибка!";
    }
    public String[] getArrayFromDB(String table, String[] equipment, int id){
        String equipmentToDB = "";
        String[] result = new String[equipment.length];
        for (int i = 0; i < equipment.length; i++) {
            equipmentToDB+=equipment[i];
            if (i+1<equipment.length){
                equipmentToDB+=", ";
            }
        }
        try{
        ResultSet rs = conn.prepareStatement("SELECT "+equipmentToDB+" FROM "+table+" WHERE car_id = " + id).executeQuery();
        if(rs.next()){
            for (int i = 0; i < equipment.length; i++) {
                result[i] = rs.getString(i+1);
            }
        }
        else {
            throw new NullPointerException();
        }
        } catch (SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
        return result;
    }
    public List<String[]> getArrayFromDB(String table, String[] equipment, String where, int id){
        String equipmentToDB = "";
        List<String[]> toReturn = new ArrayList<>();
        for (int i = 0; i < equipment.length; i++) {
            equipmentToDB+=equipment[i];
            if (i+1<equipment.length){
                equipmentToDB+=", ";
            }
        }
        try{
            ResultSet rs = conn.prepareStatement("SELECT "+equipmentToDB+" FROM "+table+" WHERE " + where + " = " + id).executeQuery();
            while (rs.next()){
                String[] result = new String[equipment.length];
                for (int i = 0; i < equipment.length; i++) {
                    result[i] = rs.getString(i+1);
                }
                toReturn.add(result);
            }
        } catch (SQLException error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
        return toReturn;
    }
    public void addUser(char role, String login, int hash, String name, int idMaster){
        try {
            int id;
            ResultSet rs = conn.prepareStatement("SELECT id FROM persons ORDER BY id DESC LIMIT 1").executeQuery();
            rs.next();
            id = rs.getInt(1) + 1;
            if(role=='M')
            conn.prepareStatement("INSERT INTO persons(id, person, login, pass, `name`) VALUES(" + id + ", '"+role+"', '"+login+"', "+hash+", '"+name+"')").executeUpdate();
            else
                conn.prepareStatement("INSERT INTO persons(id, person, login, pass, `name`, idMaster) VALUES(" + id + ", '"+role+"', '"+login+"', "+hash+", '"+name+"', "+idMaster+")").executeUpdate();
        } catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, sqlException.getMessage());
        }
    }
}



//PreparedStatement obrashen = connection.prepareStatement("SELECT name, COUNT(praktika.minions_villains.villain_id) AS count FROM praktika.villains, praktika.minions_villains WHERE praktika.villains.id = praktika.minions_villains.villain_id GROUP BY villain_id HAVING COUNT(villain_id ) > 3 ORDER BY count DESC");