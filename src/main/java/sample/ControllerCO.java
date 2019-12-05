package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ControllerCO
{
    @FXML private TextField tf1, tf2, tf3, tf4, tf5, tf6;
    @FXML private Label l1;

    public void submit(ActionEvent event)
    {
        try
        {
            Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();

            Users u = new Users();
            u.setFname(tf2.getText());
            u.setMname(tf3.getText());
            u.setLname(tf4.getText());
            u.setUsername(tf5.getText());
            u.setPassword(tf6.getText());
            u.setRoleId(2);
            l1.setText("Successfully created a new one.");

            session.beginTransaction();
            session.save(u);
            session.getTransaction().commit();
            session.close();
            factory.close();
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
