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

public class ControllerDate2
{
    @FXML TextField date11, date22;
    @FXML ListView<Clients> lv1 = new ListView();

    public void showww(ActionEvent event)
    {
        try
        {
            Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();

            DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
            Date datee = format.parse(date11.getText());
            Date dateee = format.parse(date22.getText());

            Query qry = session.createQuery("SELECT a.date, b.fname, b.mname, b.lname, c.quantity, d.name" +
                    " FROM Sales a inner join Clients b on a.clientid=b.id  " +
                    "inner join ProductSales c on a.id=c.saleid " +
                    "inner join Products d on d.id=c.productid" +
                    " where a.date between :datee and :dateee");
            qry.setParameter("datee", datee);
            qry.setParameter("dateee", dateee);

            List<Object[]> r = qry.getResultList();
            ObservableList l = FXCollections.observableArrayList();

            for(Object[] elements: r)
            {
                Timestamp a= (Timestamp) elements[0];
                Date d1=format.parse(String.valueOf(a)); l.add("Date: "+d1);
                String b= (String) elements[1]; l.add("First name: "+b);
                String c= (String) elements[2]; l.add("Middle name: "+c);
                String d= (String) elements[3]; l.add("Last name: "+d);
                int e=(int) elements[4]; l.add("Quantity: "+e);
                String f= (String) elements[5]; l.add("Name of products: "+f);
                String str=" "; l.add(str);
            }
            lv1.setItems(l);
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}
