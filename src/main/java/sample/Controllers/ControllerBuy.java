package sample.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sample.Tables.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class ControllerBuy
{
    @FXML private TextField tfield1, tfield2, tfield3, tfield5;
    @FXML private DatePicker dp1;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private Pattern pattern = Pattern.compile("\\d+");
    private static final Pattern pattern1 = Pattern.compile(
            "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
                    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
                    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
                    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

    public void buy1()
    {
        try
        {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();

            Query qry = session.createQuery("from Products where stock>0"); List l = qry.list();
            Query qryif = session.createQuery("select p.id from Clients p"); List lif = qryif.list();

            int idd = 0; Sales a = new Sales();
            ProductSales aa = new ProductSales();
            CashDesks1 a4 = new CashDesks1();
            Products p; session.beginTransaction();

            Query qry2 = session.createQuery("from Products where price=:a and name=:aa and stock>0");
            qry2.setParameter("a", Double.parseDouble(tfield5.getText()));
            qry2.setParameter("aa", tfield2.getText());
            List l2 = qry2.list();

            if (tfield1.getText().equals("") || tfield2.getText().equals("") || tfield3.getText().equals("") || tfield5.getText().equals(""))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not be empty!");
                errorAlert.showAndWait();
            }
            else if (tfield1.getText().length() > 20 || tfield2.getText().length() > 20 || tfield3.getText().length() > 20 || tfield5.getText().length() > 20)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not contain more that 20 characters!");
                errorAlert.showAndWait();
            }
            else if (!pattern.matcher(tfield1.getText()).matches())
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid Client ID!");
                errorAlert.showAndWait();
            }
            else if (!pattern1.matcher(tfield5.getText()).matches())
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid Price!");
                errorAlert.showAndWait();
            }
            else if (dp1.getValue() == null)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The date field must not be empty or have a invalid value!");
                errorAlert.showAndWait();
            }
            else if (!lif.contains(Integer.parseInt(tfield1.getText())))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("No such Client found!");
                errorAlert.showAndWait();
            }
            else if (!pattern.matcher(tfield3.getText()).matches())
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid  Quantity number!");
                errorAlert.showAndWait();
            }
            else if (Integer.parseInt(tfield3.getText()) <= 0 || Integer.parseInt(tfield3.getText()) > 30)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid  Quantity (from 1 to 30)!");
                errorAlert.showAndWait();
            }
            else if (l2.isEmpty())
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("No such Product exists!");
                errorAlert.showAndWait();
            }
            else
            {
                p = (Products) l2.get(0);
                for (int i = 0; i < l.size(); i++)
                {
                    if ((((Products) l.get(i)).getName()).equals(tfield2.getText()) && (((Products) l.get(i)).getPrice()) == Double.parseDouble(tfield5.getText()))
                    { idd = ((Products) l.get(i)).getId(); }
                }

                Query del = session.createQuery("select d.date from Deliveries d inner join ProductDeliveries p on d.id=p.deliveryid inner join Products p1 on p1.id=p.productid where p1.id=:a and d.first=:aa");
                del.setParameter("a", idd);
                del.setParameter("aa", "Yes");
                List<Date> del1 = del.list();

                DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                Date d1 = format.parse(String.valueOf(dp1.getValue()));

                for (int i = 0; i < del1.size(); i++)
                {
                    if ((format.parse(String.valueOf(del1.get(i))).after(d1)))
                    {
                        errorAlert.setHeaderText("Error!");
                        errorAlert.setContentText("Invalid date!");
                        errorAlert.showAndWait();
                        return;
                    }
                }
                int wrong=0;

                //products
                if (Integer.parseInt(tfield3.getText()) == p.getStock())
                {
                    Query qry31 = session.createQuery("update Products set stock=0 where id=:aa");
                    qry31.setParameter("aa", p.getId());
                    qry31.executeUpdate();
                }
                else if (Integer.parseInt(tfield3.getText()) < p.getStock())
                {
                    Query qry4 = session.createQuery("update Products set stock=:a where id=:aa");
                    qry4.setParameter("a", p.getStock() - Integer.parseInt(tfield3.getText()));
                    qry4.setParameter("aa", p.getId());
                    qry4.executeUpdate();
                }
                else if (Integer.parseInt(tfield3.getText()) > p.getStock())
                { wrong++; } session.getTransaction().commit();

                if(wrong==0)
                {
                    session.beginTransaction();

                    //bank
                    Bank b = session.get(Bank.class, 1);
                    Query bank = session.createQuery("update Bank set funds=:a where id=1");
                    bank.setParameter("a", b.getFunds() + (p.getPrice() * Double.parseDouble(tfield3.getText())));
                    bank.executeUpdate();

                    //sales
                    a.setClientId(Integer.parseInt(tfield1.getText()));
                    a.setDate(java.sql.Date.valueOf(dp1.getValue()));
                    a.setCbId(ControllerFirst.aoo);
                    session.save(a);
                    session.getTransaction().commit();
                    session.close();

                    Session session1 = factory.openSession();
                    session1.beginTransaction();

                    Query sales1 = session1.createQuery("from Sales");
                    List sales11 = sales1.list();

                    //cashdesk1
                    a4.setCbId(ControllerFirst.aoo);
                    a4.setSaleId(((Sales) sales11.get(sales11.size() - 1)).getId());
                    a4.setSmoney(Double.parseDouble(tfield3.getText()) * Double.parseDouble(tfield5.getText()));
                    session1.save(a4);

                    //productsales
                    aa.setProductId(idd);
                    aa.setQuantity(Integer.parseInt(tfield3.getText()));
                    aa.setSaleId((((Sales) sales11.get(sales11.size() - 1)).getId()));
                    session1.save(aa);

                    session1.close();
                    factory.close();
                    Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                    errorAlert.setHeaderText("Success!");
                    errorAlert.setContentText("Successfully bought an Product!");
                    errorAlert.showAndWait();
                }
                else
                {
                    errorAlert.setHeaderText("Error!");
                    errorAlert.setContentText("Enter new value for Quantity!");
                    errorAlert.showAndWait();
                }
            }
        }
        catch(Exception e){ e.printStackTrace(); }
    }
}