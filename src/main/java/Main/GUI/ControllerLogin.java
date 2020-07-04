package Main.GUI;
import Main.DatabaseValidators.Database;
import Main.DatabaseValidators.Validators;
import Main.Tables.Products;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Main.Tables.Bank;
import Main.Tables.Users;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unchecked")
public class ControllerLogin extends Application
{
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label label;
    Validators validator=new Validators();
    Alert alert_error=new Alert(Alert.AlertType.ERROR);
    static Stage primary_stage=new Stage();
    static Stage admin=new Stage();
    static Stage operator=new Stage();
    private static Logger logger=LogManager.getLogger(ControllerLogin.class.getName());
    public static int admin_or_operator=0;

    @Override public void start(Stage primstage) throws Exception
    {
        Parent root=FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primary_stage.setResizable(false);
        primary_stage.setTitle("Login");
        primary_stage.setScene(new Scene(root, 290, 230));
        primary_stage.show();
    }

   public static void main(String[] args) { launch(args); }

    public void submitButton()
    {
        try
        {
            validator.ValidatorLogin(username.getText(), password.getText());

            if(!validator.getError().equals(""))
            {
                alert_error.setHeaderText("Error!");
                alert_error.setContentText(validator.getError());
                alert_error.showAndWait();
                validator.setError("");
            }
            else
            {
                Database db=new Database();
                List<Users>list_users=db.getSession().createQuery("from Users").list();
                List<Products>list_products=db.getSession().createQuery("from Products where stock>0").list();
                Bank b=db.getSession().get(Bank.class, 1);

                if(b.getFunds()==0) { logger.info("No funds in the bank!"); }
                else if(b.getFunds()<=100) { logger.warn("Critical minimum of funds in the bank!"); }

                if(list_products.isEmpty()) { logger.warn("No products in the warehouse!"); }
                else if(list_products.size()<=2) { logger.warn("Critical minimum of products in the warehouse!"); }

                for (Users users:list_users)
                {
                    if(users.getId()==1 && users.getUsername().equals(username.getText()) && users.getPassword().equals(password.getText()))
                    {
                        admin_or_operator=1;
                        Parent root=new FXMLLoader(getClass().getResource("/fxml/main.fxml")).load();
                        admin.setResizable(false);
                        admin.setTitle("Main window for admin");
                        admin.setScene(new Scene(root, 1000, 550));
                        admin.show();
                    }
                    else if(users.getId()>1 && users.getUsername().equals(username.getText()) && users.getPassword().equals(password.getText()))
                    {
                        admin_or_operator=users.getId();
                        Parent root=new FXMLLoader(getClass().getResource("/fxml/main.fxml")).load();
                        operator.setResizable(false);
                        operator.setTitle("Main window for operator");
                        operator.setScene(new Scene(root, 1000, 550));
                        operator.show();
                    }
                    else { label.setText("Invalid input!"); }
                }

                db.getSession().close();
                db.getFactory().close();
            }
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}