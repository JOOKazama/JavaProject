package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ControllerDate1
{
    @FXML TextField date1, date2;
    @FXML ListView<String> lv = new ListView();

    public void showw(ActionEvent event)
    {
        try
        {
            Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();

            DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
            Date datee = format.parse(date1.getText());
            Date dateee = format.parse(date2.getText());

            Query qry = session.createQuery("SELECT e.date, p.fname, p.mname, p.lname FROM Deliveries e inner join Providers p on e.providerid=p.id where e.date between :datee and :dateee");
            qry.setParameter("datee", datee);
            qry.setParameter("dateee", dateee);
            List<Object[]> r = qry.getResultList();
            ObservableList l = FXCollections.observableArrayList();

            for(Object[] elements: r)
            {
                String a,b,c;
                Timestamp d= (Timestamp) elements[0];
                Date d1=format.parse(String.valueOf(d)); l.add("Date: "+d1);
                a= (String) elements[1]; l.add("First name: "+a);
                b= (String) elements[2]; l.add("Middle name: "+b);
                c= (String) elements[3]; l.add("Last name: "+c);
                String str=" "; l.add(str);
            }
            lv.setItems(l);
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}
