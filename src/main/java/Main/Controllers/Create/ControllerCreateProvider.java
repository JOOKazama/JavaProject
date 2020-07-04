package Main.Controllers.Create;
import Main.DatabaseValidators.Database;
import Main.DatabaseValidators.Validators;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Main.Tables.Providers;

public class ControllerCreateProvider
{
    public Button button_create;
    public Label new_provider;
    @FXML private TextField first_name, middle_name, last_name, company_name, vat_number;
    Validators validator=new Validators();
    Alert alert_error=new Alert(Alert.AlertType.ERROR);
    Alert information_alert=new Alert(Alert.AlertType.INFORMATION);

    public void create_provider()
    {
        validator.ValidatorCreateProvider(first_name.getText(), middle_name.getText(), last_name.getText(), company_name.getText(), vat_number.getText());
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

                Providers provider=new Providers();
                provider.setFname(first_name.getText());
                provider.setMname(middle_name.getText());
                provider.setLname(last_name.getText());
                provider.setCompany(company_name.getText());
                provider.setVatnumber(Integer.parseInt(vat_number.getText()));

                db.getSession().beginTransaction();
                db.getSession().save(provider);
                db.getSession().getTransaction().commit();
                db.getSession().close();
                db.getFactory().close();

                information_alert.setContentText("Successfully created a new Provider!");
                information_alert.showAndWait();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}