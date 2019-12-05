package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ControllerCB
{
    @FXML private TextField tf1111;
    @FXML private Label l1111;
    static Bank f1=new Bank();

    public void submit3(ActionEvent event)
    {
        try
        {
            if (Double.parseDouble(tf1111.getText()) > 0)
            {
                Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
                SessionFactory factory = cfg.buildSessionFactory();
                Session session = factory.openSession();

                Bank f = new Bank();
                f.setId(1);
                f.setFunds(Double.parseDouble(tf1111.getText()));
                session.beginTransaction();
                session.saveOrUpdate(f);
                session.getTransaction().commit();
                session.close(); factory.close();
                l1111.setText("Success!");
            }
            else l1111.setText("Enter a value bigger than 0!");
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

