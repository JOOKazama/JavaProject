package sample.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sample.Tables.Providers;

import java.util.regex.Pattern;

public class ControllerCP
{
    @FXML private TextField tf22, tf33, tf44, tf55, tf66;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private Pattern pattern = Pattern.compile("\\d+");

    public void submit1()
    {
        try
        {
            if(tf22.getText().equals("") || tf33.getText().equals("") || tf44.getText().equals("") || tf55.getText().equals("") || tf66.getText().equals(""))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not be empty!");
                errorAlert.showAndWait();
            }
            else if(tf22.getText().length()>20 || tf33.getText().length()>20 || tf44.getText().length()>20 || tf66.getText().length()>20 || tf66.getText().length()>20)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not contain more that 20 characters!");
                errorAlert.showAndWait();
            }
            else if(!pattern.matcher(tf66.getText()).matches() || Integer.parseInt(tf66.getText())<=0)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid  Vatnumber!");
                errorAlert.showAndWait();
            }
            else
            {
                Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
                SessionFactory factory = cfg.buildSessionFactory(); Session session = factory.openSession();

                Providers u1 = new Providers();
                u1.setFname(tf22.getText());
                u1.setMname(tf33.getText());
                u1.setLname(tf44.getText());
                u1.setCompany(tf55.getText());
                u1.setVatnumber(Integer.parseInt(tf66.getText()));

                session.beginTransaction();
                session.save(u1);
                session.getTransaction().commit();
                session.close(); factory.close();
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Success!");
                errorAlert.setContentText("Successfully created a new Provider!");
                errorAlert.showAndWait();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}
