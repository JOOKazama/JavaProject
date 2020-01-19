package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sample.Tables.Bank;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerSeem implements Initializable
{
    @FXML private Label ll2, labell;
    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();
            Query qry = session.createQuery("from Bank");
            List l = qry.list();
            ll2.setText(Double.toString(((Bank) l.get(0)).getFunds()));
        }
        catch(Exception e) { labell.setText("No money in the bank."); }
    }
}
