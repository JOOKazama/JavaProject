package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ControllerReceive
{
    @FXML private TextField textf1, textf2, textf3, textf4, textf5, textf6;

    public void create1(ActionEvent event)
    {
        try
        {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();

            Query qry = session.createQuery("from Providers"); List l = qry.list();
            Deliveries a=new Deliveries();   int o=0, oo=0, idd=0;
            Products a1=new Products();
            Products a11=new Products();
            ProviderProducts a2=new ProviderProducts();
            ProductDeliveries a3=new ProductDeliveries();

            DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
            Date date = (Date) format.parse(textf6.getText());

            Bank b = (Bank) session.get(Bank.class, 1);
            double bb=b.getFunds();
            double bbb=bb-(Double.parseDouble(textf3.getText())*Double.parseDouble(textf5.getText()));
            b.setId(1); b.setFunds(bbb);

            for(int i = 0; i < l.size(); i++)
            {
                if ((((Providers) l.get(i)).getId())==Integer.parseInt(textf1.getText()))
                {
                   idd=((Providers) l.get(i)).getId();
                }
            }

            //deliveries
            session.beginTransaction();
            a.setProviderId(idd);
            a.setCbId(ControllerFirst.aoo);
            a.setDate(date);
            session.save(a);
            session.update(b);

            //products
            Query q = session.createQuery("from Products"); List p = q.list();

            for(int i=0; i<p.size(); i++)
            {
                if((((Products) p.get(i)).getName()).equals(textf2.getText()) && ((Products) p.get(i)).getPrice()==Double.parseDouble(textf4.getText()))
                {
                    oo=(((Products) p.get(i)).getId());
                    Query up = session.createQuery("UPDATE Products set stock=:a where id=:aa");
                    up.setParameter("a", ((Products) p.get(i)).getStock()+Integer.parseInt(textf5.getText()));
                    up.setParameter("aa", oo);
                    int result = up.executeUpdate();
                    o++;
                }
            }

            if(o!=1)
            {
                a1.setName(textf2.getText());
                a1.setPrice(Double.parseDouble(textf4.getText()));
                a1.setStock(Integer.parseInt(textf5.getText()));
                session.save(a1);
            }
            session.getTransaction().commit();
            session.close();

            Session session1 = factory.openSession();
            Query qry1 = session1.createQuery("from Deliveries"); List l1 = qry1.list();
            Query qry2 = session1.createQuery("from Products"); List l2 = qry2.list();

            //provider products
            session1.beginTransaction();
            a2.setProviderId(idd);
            a2.setProductId(((Products) l2.get(l2.size() - 1)).getId());
            session1.save(a2);

            //product deliveries
            a3.setProductId(((Products) l2.get(l2.size() - 1)).getId());
            a3.setDeliveryId(((Deliveries) l1.get(l1.size() - 1)).getId());
            a3.setQuantity(Integer.parseInt(textf5.getText()));
            a3.setDeliveryprice(Integer.parseInt(textf3.getText()));
            a3.setProviderId(idd);
            session1.save(a3);
            session1.getTransaction().commit();
            session1.close();
            factory.close();
        }
        catch (Exception e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("The input is invalid. Try again.");
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }
}