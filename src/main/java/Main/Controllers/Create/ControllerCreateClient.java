package Main.Controllers.Create;
import Main.DatabaseValidators.Database;
import Main.DatabaseValidators.Validators;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Main.Tables.Clients;

public class ControllerCreateClient
{
    public Label new_client;
    public Button button_create;
    @FXML private TextField first_name, middle_name, last_name, company_name, vat_number;
    Validators validator=new Validators();
    Alert alert_error=new Alert(Alert.AlertType.ERROR);
    Alert information_alert=new Alert(Alert.AlertType.INFORMATION);

    public void create_client()
    {
        validator.ValidatorCreateClient(first_name.getText(), middle_name.getText(), last_name.getText(), company_name.getText(), vat_number.getText());
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

                Clients client=new Clients();
                client.setFname(first_name.getText());
                client.setMname(middle_name.getText());
                client.setLname(last_name.getText());
                client.setCname(company_name.getText());
                client.setVatnumber(Integer.parseInt(vat_number.getText()));

                db.getSession().beginTransaction();
                db.getSession().save(client);
                db.getSession().getTransaction().commit();
                db.getSession().close();
                db.getFactory().close();

                information_alert.setContentText("Successfully created a new Client!");
                information_alert.showAndWait();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}