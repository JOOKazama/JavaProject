package Main.Controllers.Operations;
import Main.DatabaseValidators.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import Main.Tables.Bank;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerShowFunds implements Initializable
{
    @FXML private Label label, amount_funds;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            Database db=new Database();
            label.setText(Double.toString(((Bank)db.getSession().createQuery("from Bank").list().get(0)).getFunds()));
        }
        catch(Exception e) { amount_funds.setText("No money in the bank."); }
    }
}