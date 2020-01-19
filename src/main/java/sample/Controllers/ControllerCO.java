package sample.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sample.Tables.Users;

public class ControllerCO
{
    @FXML private TextField tf2, tf3, tf4, tf5, tf6;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public void submit()
    {
        try
        {
            if(tf2.getText().equals("") || tf3.getText().equals("") || tf4.getText().equals("") || tf5.getText().equals("") || tf6.getText().equals(""))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not be empty!");
                errorAlert.showAndWait();
            }
            else if(tf2.getText().length()>20 || tf3.getText().length()>20 || tf4.getText().length()>20 || tf5.getText().length()>20 || tf6.getText().length()>20)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not contain more that 20 characters!");
                errorAlert.showAndWait();
            }
            else if(tf5.getText().equals("Admin1"))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("You can't use that name!");
                errorAlert.showAndWait();
            }
            else
            {
                Configuration cfg = new Configuration();
                cfg.configure("hibernate.cfg.xml");
                SessionFactory factory = cfg.buildSessionFactory();
                Session session = factory.openSession();

                Users u = new Users();
                u.setFname(tf2.getText());
                u.setMname(tf3.getText());
                u.setLname(tf4.getText());
                u.setUsername(tf5.getText());
                u.setPassword(tf6.getText());
                u.setRoleId(2);

                session.beginTransaction();
                session.save(u);
                session.getTransaction().commit();
                session.close(); factory.close();
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Success!");
                errorAlert.setContentText("Successfully created a new Operator!");
                errorAlert.showAndWait();
            }
        }
        catch (Exception e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("The input is invalid. Try again.");
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }
}