package sample.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sample.Tables.Clients;
import java.util.regex.Pattern;

public class ControllerCC
{
    @FXML private TextField tf222, tf333, tf444, tf555, tf666;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private Pattern pattern = Pattern.compile("\\d+");

    public void submit2()
    {
        try
        {
            if(tf222.getText().equals("") || tf333.getText().equals("") || tf444.getText().equals("") || tf555.getText().equals("") || tf666.getText().equals(""))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not be empty!");
                errorAlert.showAndWait();
            }
            else if(tf222.getText().length()>20 || tf333.getText().length()>20 || tf444.getText().length()>20 || tf555.getText().length()>20 || tf666.getText().length()>20)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not contain more that 20 characters!");
                errorAlert.showAndWait();
            }
            else if(!pattern.matcher(tf666.getText()).matches() || Integer.parseInt(tf666.getText())<=0)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid  Vatnumber!");
                errorAlert.showAndWait();
            }
            else
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

                session.beginTransaction();
                session.save(u11);
                session.getTransaction().commit();
                session.close(); factory.close();
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Success!");
                errorAlert.setContentText("Successfully created a new Client!");
                errorAlert.showAndWait();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}