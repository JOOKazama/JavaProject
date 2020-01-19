package sample.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sample.Tables.Bank;
import java.util.regex.Pattern;

public class ControllerCB
{
    @FXML private TextField tf1111;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private static final Pattern pattern = Pattern.compile(
            "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
            "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
            "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
            "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

    public void submit3()
    {
        try
        {
            if(tf1111.getText().equals(""))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field must not be empty!");
                errorAlert.showAndWait();
            }
            else if(!pattern.matcher(tf1111.getText()).matches())
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a number!");
                errorAlert.showAndWait();
            }
            else if (Double.parseDouble(tf1111.getText()) <= 0 || Double.parseDouble(tf1111.getText()) > 500000)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Enter a value bigger than 0 or smaller or equal to 500000!");
                errorAlert.showAndWait();
            }
            else
            {
                Configuration cfg = new Configuration();
                cfg.configure("hibernate.cfg.xml");
                SessionFactory factory = cfg.buildSessionFactory();
                Session session = factory.openSession();

                Bank f = new Bank();
                f.setId(1);
                f.setFunds(Double.parseDouble(tf1111.getText()));
                session.beginTransaction();
                session.saveOrUpdate(f);
                session.getTransaction().commit();
                session.close(); factory.close();
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Success!");
                errorAlert.setContentText("Successfully created a new Bank!");
                errorAlert.showAndWait();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}