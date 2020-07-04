package Main.Controllers.Create;
import Main.DatabaseValidators.Database;
import Main.DatabaseValidators.Validators;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Main.Tables.Bank;

public class ControllerCreateBank
{
    public Button button_bank;
    public Label label_bank;
    @FXML private TextField starting_money;
    Validators validator=new Validators();
    Alert alert_error=new Alert(Alert.AlertType.ERROR);
    Alert information_alert=new Alert(Alert.AlertType.INFORMATION);

    public void create_bank()
    {
        validator.ValidatorCreateBank(starting_money.getText());
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

                Bank bank=new Bank();
                bank.setId(1);
                bank.setFunds(Double.parseDouble(starting_money.getText()));

                db.getSession().beginTransaction();
                db.getSession().saveOrUpdate(bank);
                db.getSession().getTransaction().commit();
                db.getSession().close();
                db.getFactory().close();

                information_alert.setContentText("Successfully created a new Bank!");
                information_alert.showAndWait();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}