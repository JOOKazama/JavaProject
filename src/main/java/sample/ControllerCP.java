package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ControllerCP
{
    @FXML private TextField tf11, tf22, tf33, tf44, tf55, tf66;
    @FXML private Label l11;

    public void submit1(ActionEvent event)
    {
        try
        {
            Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();

            Providers u1 = new Providers();
            u1.setFname(tf22.getText());
            u1.setMname(tf33.getText());
            u1.setLname(tf44.getText());
            u1.setCompany(tf55.getText());
            u1.setVatnumber(Integer.parseInt(tf66.getText()));
            l11.setText("Successfully created a new one.");

            session.beginTransaction();
            session.save(u1);
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
