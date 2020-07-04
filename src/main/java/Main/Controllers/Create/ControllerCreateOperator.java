package Main.Controllers.Create;
import Main.DatabaseValidators.Database;
import Main.DatabaseValidators.Validators;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Main.Tables.Users;
import java.util.List;

@SuppressWarnings("unchecked")
public class ControllerCreateOperator
{
    public Button button_create;
    public Label new_operator;
    @FXML private TextField first_name, middle_name, last_name, username, password;
    Validators validator=new Validators();
    Alert alert_error=new Alert(Alert.AlertType.ERROR);
    Alert information_alert=new Alert(Alert.AlertType.INFORMATION);

    public void create_operator()
    {
        validator.ValidatorCreateOperator(first_name.getText(), middle_name.getText(), last_name.getText(), username.getText(), password.getText());
        alert_error.setHeaderText("Error!");
        information_alert.setHeaderText("Success!");

        try
        {
            if(!validator.getError().equals(""))
            {
                alert_error.setContentText(validator.getError());
                alert_error.showAndWait();
                validator.setError("");
            }
            else
            {
                Database db=new Database();
                List<String>list_usernames=db.getSession().createQuery("select username from Users").list();

                for(String username1:list_usernames)
                {
                    if(username1.equals(username.getText()))
                    {
                        alert_error.setContentText("There is an operator with that username!");
                        alert_error.showAndWait();
                        return;
                    }
                }

                Users user=new Users();
                user.setFname(first_name.getText());
                user.setMname(middle_name.getText());
                user.setLname(last_name.getText());
                user.setUsername(username.getText());
                user.setPassword(password.getText());
                user.setRoleId(2);

                db.getSession().beginTransaction();
                db.getSession().save(user);
                db.getSession().getTransaction().commit();
                db.getSession().close();

                information_alert.setContentText("Successfully created a new Operator!");
                information_alert.showAndWait();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}