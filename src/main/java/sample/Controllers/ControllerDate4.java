package sample.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;

public class ControllerDate4
{
    @FXML DatePicker date1, date2;
    @FXML ListView<String> lv = new ListView();
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public void showw()
    {
        try
        {
            if(date1.getValue()==null || date2.getValue()==null || date2.getValue().isBefore(date1.getValue()))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field must not be empty or have a invalid value!");
                errorAlert.showAndWait();
            }
            else
            {
                Configuration cfg = new Configuration();
                cfg.configure("hibernate.cfg.xml");
                SessionFactory factory = cfg.buildSessionFactory();
                Session session = factory.openSession();

                double aa=0, aa1=0, aa2=0;

                Query qry = session.createQuery("SELECT l.smoney FROM Sales e inner join CashDesks1 l on e.id=l.saleid where e.date between :d1 and :d2");
                qry.setParameter("d1", java.sql.Date.valueOf(date1.getValue()));
                qry.setParameter("d2", java.sql.Date.valueOf(date2.getValue()));
                List<Double> r = qry.getResultList();
                ObservableList l = FXCollections.observableArrayList();

                for(int i=0; i<r.size(); i++) { aa=aa+r.get(i); }
                l.add("Income: "+aa);

                Query qry1 = session.createQuery("SELECT l.rmoney FROM Deliveries e inner join CashDesks l on e.id=l.deliveryid where e.date between :d1 and :d2");
                qry1.setParameter("d1", java.sql.Date.valueOf(date1.getValue()));
                qry1.setParameter("d2", java.sql.Date.valueOf(date2.getValue()));
                List<Double> r1 = qry1.getResultList();

                for(int i=0; i<r1.size(); i++) { aa1=aa1+r1.get(i); }
                l.add("Costs: "+aa1);

                aa2=aa-aa1; l.add("Profits(or lack of): "+aa2); lv.setItems(l);
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}