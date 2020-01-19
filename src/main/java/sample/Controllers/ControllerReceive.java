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
import java.util.List;
import java.util.regex.Pattern;

public class ControllerReceive
{
    @FXML private TextField textf1, textf2, textf3, textf4, textf5;
    @FXML private DatePicker dp;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private Pattern pattern = Pattern.compile("\\d+");
    private static final Pattern pattern1 = Pattern.compile(
            "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
                    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
                    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
                    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

    public void create1()
    {
        try
        {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();
            Session session = factory.openSession();

            Query qryif = session.createQuery("select p.id from Providers p"); List lif = qryif.list();

            int o = 0, idd = 0;
            Deliveries a = new Deliveries();
            Products a1 = new Products();
            ProviderProducts a2 = new ProviderProducts();
            ProductDeliveries a3 = new ProductDeliveries();
            CashDesks a4 = new CashDesks();
            session.beginTransaction();

            Bank b = session.get(Bank.class, 1);

            if(!pattern.matcher(textf1.getText()).matches())
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid Provider ID!");
                errorAlert.showAndWait();
            }
            else if(!lif.contains(Integer.parseInt(textf1.getText())))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("No such Provider found!");
                errorAlert.showAndWait();
            }
            else if(textf1.getText().equals("") || textf2.getText().equals("") || textf3.getText().equals("") || textf4.getText().equals("") || textf5.getText().equals(""))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not be empty!");
                errorAlert.showAndWait();
            }
            else if(textf1.getText().length()>20 || textf2.getText().length()>20 || textf3.getText().length()>20 || textf4.getText().length()>20 || textf5.getText().length()>20)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field(s) must not contain more that 20 characters!");
                errorAlert.showAndWait();
            }
            else if(dp.getValue()==null)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The date field must not be empty or have a invalid value!");
                errorAlert.showAndWait();
            }
            else if(!pattern.matcher(textf5.getText()).matches())
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid  Quantity number!");
                errorAlert.showAndWait();
            }
            else if(Integer.parseInt(textf5.getText())<=0 || Integer.parseInt(textf5.getText())>30)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid  Quantity (from 1 to 30)!");
                errorAlert.showAndWait();
            }
            else if(!pattern1.matcher(textf3.getText()).matches())
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid Delivery price!");
                errorAlert.showAndWait();
            }
            else if(!pattern1.matcher(textf4.getText()).matches())
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Please enter a valid Sale price!");
                errorAlert.showAndWait();
            }
            else if (Double.parseDouble(textf3.getText()) <= 0 || Double.parseDouble(textf3.getText()) > 5000)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Enter a value bigger than 0 or smaller or equal to 5000 for Delivery price!");
                errorAlert.showAndWait();
            }
            else if (Double.parseDouble(textf4.getText()) <= 0 || Double.parseDouble(textf4.getText()) > 5000)
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Enter a value bigger than 0 or smaller or equal to 5000 for Sale price!");
                errorAlert.showAndWait();
            }
            else if (Double.parseDouble(textf3.getText()) * Double.parseDouble(textf5.getText()) > b.getFunds())
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("Not enough funds in the bank.");
                errorAlert.showAndWait();
            }
            else
            {
                b.setId(1);
                b.setFunds(b.getFunds() - (Double.parseDouble(textf3.getText()) * Double.parseDouble(textf5.getText())));

                Query qry = session.createQuery("from Providers"); List l = qry.list();

                for(int i=0; i<l.size(); i++)
                {
                    if ((((Providers) l.get(i)).getId()) == Integer.parseInt(textf1.getText()))
                    { idd = ((Providers) l.get(i)).getId(); }
                }

                //products
                Query q = session.createQuery("from Products"); List p = q.list();

                for (int i = 0; i < p.size(); i++)
                {
                    if ((((Products) p.get(i)).getName()).equals(textf2.getText()) && ((Products) p.get(i)).getPrice() == Double.parseDouble(textf4.getText()))
                    {
                        if ((((Products) p.get(i)).getStock() == 0))
                        { a.setFirst("Yes"); } else a.setFirst("No");

                        Query up = session.createQuery("UPDATE Products set stock=:a where id=:aa");
                        up.setParameter("a", ((Products) p.get(i)).getStock() + Integer.parseInt(textf5.getText()));
                        up.setParameter("aa", ((Products) p.get(i)).getId());
                        up.executeUpdate(); o++;
                    }
                }

                if (o != 1)
                {
                    a1.setName(textf2.getText());
                    a1.setPrice(Double.parseDouble(textf4.getText()));
                    a1.setStock(Integer.parseInt(textf5.getText()));
                    a.setFirst("Yes"); session.save(a1);
                }

                //deliveries
                a.setProviderId(idd);
                a.setCbId(ControllerFirst.aoo);
                a.setDate(java.sql.Date.valueOf(dp.getValue()));
                session.save(a); session.update(b);
                session.getTransaction().commit(); session.close();

                Session session1 = factory.openSession();
                Query qry1 = session1.createQuery("from Deliveries"); List l1 = qry1.list();
                Query qry2 = session1.createQuery("from Products"); List l2 = qry2.list();

                //provider products
                session1.beginTransaction();
                a2.setProviderId(idd);
                a2.setProductId(((Products) l2.get(l2.size() - 1)).getId());
                session1.save(a2);

                //cashdesk
                a4.setCbId(ControllerFirst.aoo);
                a4.setDeliveryid(((Deliveries) l1.get(l1.size() - 1)).getId());
                a4.setRmoney(Double.parseDouble(textf3.getText()) * Double.parseDouble(textf5.getText()));
                session1.save(a4);

                //product deliveries
                a3.setProductId(((Products) l2.get(l2.size() - 1)).getId());
                a3.setDeliveryId(((Deliveries) l1.get(l1.size() - 1)).getId());
                a3.setQuantity(Integer.parseInt(textf5.getText()));
                a3.setDeliveryprice(Integer.parseInt(textf3.getText()));
                a3.setProviderId(idd); session1.save(a3);
                session1.getTransaction().commit();
                session1.close(); factory.close();

                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Success!");
                errorAlert.setContentText("Successfully created a new Delivery!");
                errorAlert.showAndWait();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}