package sample;

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
import java.util.List;

public class ControllerFirst extends Application
{
    @Override public void start(Stage primstage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/first.fxml"));
        primstage.setResizable(false);
        primstage.setTitle("Login");
        primstage.setScene(new Scene(root, 290, 230));
        primstage.show();
    }

   public static void main(String[] args) { launch(args); }

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label label;
    static int aoo=0;

    public void submitButton(ActionEvent event)
    {
        try
        {
            Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();
            Query qry = session.createQuery("from Users u");
            List l = qry.list();

                for(int i = 0; i < l.size(); i++)
                {
                    if (((Users) l.get(i)).getId() == 1 && ((Users) l.get(i)).getUsername().equals(username.getText()) && ((Users) l.get(i)).getPassword().equals(password.getText()))
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        stage.setTitle("Main window for admin");
                        stage.setScene(new Scene(root));
                        stage.show();
                        aoo=1;
                    }
                    else if (((Users) l.get(i)).getId() > 1 && ((Users) l.get(i)).getUsername().equals(username.getText()) && ((Users) l.get(i)).getPassword().equals(password.getText()))
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main1.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        stage.setTitle("Main window for operator");
                        stage.setScene(new Scene(root));
                        stage.show();
                        aoo=((Users) l.get(i)).getId();
                    }
                }
            session.close(); factory.close();
        }
        catch(Exception e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("The input is invalid. Try again.");
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }
}
