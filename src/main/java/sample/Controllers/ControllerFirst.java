package sample.Controllers;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sample.Tables.Users;

import java.util.List;

public class ControllerFirst extends Application
{
    static Stage pr=new Stage();
    static Stage admin=new Stage();
    static Stage operator=new Stage();


    @Override public void start(Stage primstage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/first.fxml"));
        pr.setResizable(false);
        pr.setTitle("Login");
        pr.setScene(new Scene(root, 290, 230));
        pr.show();
    }

   public static void main(String[] args) { launch(args); }

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label label;
    static int aoo=0;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public void submitButton(ActionEvent event)
    {
        try
        {
            if(username.getText().equals("") || password.getText().equals(""))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not be empty!");
                errorAlert.showAndWait();
            }
            else if(username.getText().length()>20 || password.getText().length()>20)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not contain more that 20 characters!");
                errorAlert.showAndWait();
            }
            else
            {
                Configuration cfg = new Configuration();
                cfg.configure("hibernate.cfg.xml");
                SessionFactory factory = cfg.buildSessionFactory();
                Session session = factory.openSession();
                Query qry = session.createQuery("from Users");
                List l = qry.list();

                for (int i = 0; i < l.size(); i++)
                {
                    if (((Users) l.get(i)).getId() == 1 && ((Users) l.get(i)).getUsername().equals(username.getText()) && ((Users) l.get(i)).getPassword().equals(password.getText()))
                    {
                        aoo = 1;
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
                        Parent root = fxmlLoader.load();
                        admin.setResizable(false);
                        admin.setTitle("Main window for admin");
                        admin.setScene(new Scene(root, 1000, 600)); admin.show();

                    }
                    else if (((Users) l.get(i)).getId() > 1 && ((Users) l.get(i)).getUsername().equals(username.getText()) && ((Users) l.get(i)).getPassword().equals(password.getText()))
                    {
                        aoo = ((Users) l.get(i)).getId();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
                        Parent root = fxmlLoader.load();
                        operator.setResizable(false);
                        operator.setTitle("Main window for operator");
                        operator.setScene(new Scene(root, 1000, 600)); operator.show();
                    }
                    else { label.setText("Invalid input!"); }
                }
                session.close(); factory.close();
            }
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}