package Main.Controllers.Operations;
import Main.GUI.ControllerLogin;
import Main.DatabaseValidators.Database;
import Main.DatabaseValidators.Validators;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import Main.Tables.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
public class ControllerBuyProduct
{
    public Button button_buy;
    public Label buy_warehouse;
    @FXML private TextField id_client, name, quantity, price;
    @FXML private DatePicker datepicker;
    Validators validator=new Validators();
    Alert alert_error=new Alert(Alert.AlertType.ERROR);
    Alert information_alert=new Alert(Alert.AlertType.INFORMATION);

    public void buy_product()
    {
        try
        {
            Database db=new Database();
            List<Products>list_products=db.getSession().createQuery("from Products where stock>0").list();
            List<Integer>list_client_ids=db.getSession().createQuery("select p.id from Clients p").list();

            int product_id=0;
            Products product;
            Sales sale=new Sales();
            ProductSales p_sales=new ProductSales();
            CashDesksSales cd_sales=new CashDesksSales();
            db.getSession().beginTransaction();

            validator.ValidatorBuy(id_client.getText(), name.getText(), quantity.getText(), price.getText(), datepicker, list_client_ids);
            alert_error.setHeaderText("Error!");
            information_alert.setHeaderText("Success!");

            if(!validator.getError().equals(""))
            {
                alert_error.setContentText(validator.getError());
                alert_error.showAndWait();
                validator.setError("");
            }
            else
            {
                Query<Products>query_products=db.getSession().createQuery("from Products where price=:first and name=:second and stock>0");
                query_products.setParameter("first", Double.parseDouble(price.getText()));
                query_products.setParameter("second", name.getText());
                List<Products>list_products_available=query_products.list();

                if (list_products_available.isEmpty())
                {
                    alert_error.setContentText("No such Product exists!");
                    alert_error.showAndWait();
                    return;
                }

                product=list_products_available.get(0);

                for(Products products:list_products)
                {
                    if((products.getName()).equals(name.getText()) && (products.getPrice())==Double.parseDouble(price.getText()))
                    { product_id=products.getId(); }
                }

                Query<Date>query_date=db.getSession().createQuery("select d.date from Deliveries d inner join ProductDeliveries p on d.id=p.deliveryid inner join Products p1 on p1.id=p.productid where p1.id=:first and d.first=:second");
                query_date.setParameter("first", product_id);
                query_date.setParameter("second", "Yes");
                List<Date>list_dates=query_date.list();

                DateFormat format=new SimpleDateFormat("yyyy-mm-dd");
                Date date=format.parse(String.valueOf(datepicker.getValue()));

                for(Date date1:list_dates)
                {
                    if((format.parse(String.valueOf(date1)).after(date)))
                    {
                        alert_error.setContentText("Invalid date!");
                        alert_error.showAndWait();
                        return;
                    }
                }
                int wrong=0;

                if(Integer.parseInt(quantity.getText())==product.getStock())
                {
                    Query<Products>query_products_update=db.getSession().createQuery("update Products set stock=0 where id=:first");
                    query_products_update.setParameter("first", product.getId());
                    query_products_update.executeUpdate();
                }
                else if(Integer.parseInt(quantity.getText())<product.getStock())
                {
                    Query<Products>query_products_update=db.getSession().createQuery("update Products set stock=:first where id=:second");
                    query_products_update.setParameter("first", product.getStock()-Integer.parseInt(quantity.getText()));
                    query_products_update.setParameter("second", product.getId());
                    query_products_update.executeUpdate();
                }
                else if(Integer.parseInt(quantity.getText())>product.getStock()) { wrong++; }
                db.getSession().getTransaction().commit();

                if(wrong==0)
                {
                    db.getSession().beginTransaction();

                    Bank bank=db.getSession().get(Bank.class, 1);
                    Query<Bank>query_bank=db.getSession().createQuery("update Bank set funds=:a where id=1");
                    query_bank.setParameter("a", bank.getFunds()+(product.getPrice()*Double.parseDouble(quantity.getText())));
                    query_bank.executeUpdate();

                    sale.setClientId(Integer.parseInt(id_client.getText()));
                    sale.setDate(java.sql.Date.valueOf(datepicker.getValue()));
                    sale.setCbId(ControllerLogin.admin_or_operator);
                    db.getSession().save(sale);
                    db.getSession().getTransaction().commit();
                    db.getSession().close();

                    Session session1=db.getFactory().openSession();
                    session1.beginTransaction();

                    List<Sales>list_sales=session1.createQuery("from Sales").list();

                    cd_sales.setCbId(ControllerLogin.admin_or_operator);
                    cd_sales.setSaleId((list_sales.get(list_sales.size()-1)).getId());
                    cd_sales.setSmoney(Double.parseDouble(quantity.getText())*Double.parseDouble(price.getText()));
                    session1.save(cd_sales);

                    p_sales.setProductId(product_id);
                    p_sales.setQuantity(Integer.parseInt(quantity.getText()));
                    p_sales.setSaleId(((list_sales.get(list_sales.size()-1)).getId()));
                    session1.save(p_sales);

                    session1.close();
                    db.getFactory().close();

                    information_alert.setContentText("Successfully bought an Product!");
                    information_alert.showAndWait();
                }
                else
                {
                    alert_error.setContentText("Enter new value for Quantity!");
                    alert_error.showAndWait();
                }
            }
        }
        catch(Exception e){ e.printStackTrace(); }
    }
}