package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ControllerCC
{
    @FXML private TextField tf111, tf222, tf333, tf444, tf555, tf666, tf777;
    @FXML private Label l111;

    public void submit2(ActionEvent event)
    {
        try
        {
            Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();

            Clients u11 = new Clients();
            u11.setFname(tf222.getText());
            u11.setMname(tf333.getText());
            u11.setLname(tf444.getText());
            u11.setCname(tf555.getText());
            u11.setVatnumber(Integer.parseInt(tf666.getText()));
            l111.setText("Successfully created a new one.");

            session.beginTransaction();
            session.save(u11);
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

