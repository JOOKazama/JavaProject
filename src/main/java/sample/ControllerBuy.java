package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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

public class ControllerBuy
{
    @FXML private TextField tfield1, tfield2, tfield3, tfield4, tfield5;
    @FXML private Label ls;

    public void buy1(ActionEvent event)
    {
        try
        {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();

            Query qry = session.createQuery("from Products where stock>0");
            List l = qry.list();

            Sales a=new Sales(); int idd=0;
            ProductSales aa=new ProductSales();

            DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
            Date date = (Date) format.parse(tfield4.getText());

            for(int i=0; i<l.size(); i++)
            {
                if ((((Products) l.get(i)).getName()).equals(tfield2.getText()))
                { idd=((Products) l.get(i)).getId(); }
            }

            session.beginTransaction();
            a.setClientId(Integer.parseInt(tfield1.getText()));
            a.setDate(date);
            session.save(a);
            session.getTransaction().commit();
            session.close();

            Session session1 = factory.openSession();
            session1.beginTransaction();
            Query qry1 = session1.createQuery("from Sales"); List l1 = qry1.list();
            Query qry2 = session1.createQuery("from Products where price=:a and name=:aa and stock>0");
            qry2.setParameter("a", Double.parseDouble(tfield5.getText()));
            qry2.setParameter("aa", tfield2.getText()); List l2 = qry2.list();
            Products p = (Products) l2.get(0);

            Bank b = (Bank) session1.get(Bank.class, 1);
            Query bank = session1.createQuery("update Bank set funds=:a where id=1");
            bank.setParameter("a", b.getFunds()+(p.getPrice()*Double.parseDouble(tfield3.getText())));
            int resultbank = bank.executeUpdate();

            aa.setProductId(idd);
            aa.setQuantity(Integer.parseInt(tfield3.getText()));
            aa.setCbId(ControllerFirst.aoo);
            aa.setSaleId((((Sales) l1.get(l1.size() - 1)).getId()));
            session1.save(aa);

            if(Integer.parseInt(tfield3.getText())>p.getStock())
            { ls.setText("Enter new value for quantity"); }
            else if(Integer.parseInt(tfield3.getText())==p.getStock())
            {
                Query qry31 = session1.createQuery("update Products set stock=:a where id=:aa");
                qry31.setParameter("a", 0);
                qry31.setParameter("aa", p.getId());
                int result31 = qry31.executeUpdate();
            }
            else if(Integer.parseInt(tfield3.getText())<p.getStock())
            {
                Query qry4 = session1.createQuery("update Products set stock=:a where id=:aa");
                qry4.setParameter("a", p.getStock()-Integer.parseInt(tfield3.getText()));
                qry4.setParameter("aa", p.getId());
                int result1 = qry4.executeUpdate();
            }
            session1.close(); factory.close();
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