package Main.Controllers.Operations;
import Main.GUI.ControllerLogin;
import Main.DatabaseValidators.Database;
import Main.DatabaseValidators.Validators;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.query.Query;
import Main.Tables.*;
import java.util.List;

@SuppressWarnings("unchecked")
public class ControllerReceiveProducts
{
    public Button button_recieve;
    @FXML private TextField id, name, delivery_price, sale_price, quantity;
    @FXML private DatePicker date;
    Validators validator=new Validators();
    Alert alert_error=new Alert(Alert.AlertType.ERROR);
    Alert information_alert=new Alert(Alert.AlertType.INFORMATION);

    public void receive()
    {
        try
        {
            Database db=new Database();
            List<Integer>list_provider_ids=db.getSession().createQuery("select p.id from Providers p").list();

            int if_updated=0, provider_id=0;
            Products product=new Products();
            Deliveries delivery=new Deliveries();
            ProviderProducts p_products=new ProviderProducts();
            ProductDeliveries p_deliveries=new ProductDeliveries();
            CashDesksDelivery cd_deliveries=new CashDesksDelivery();
            db.getSession().beginTransaction();
            Bank bank=db.getSession().get(Bank.class, 1);

            validator.ValidatorReceiveProducts(id.getText(), list_provider_ids, name.getText(), delivery_price.getText(), sale_price.getText(), quantity.getText(), date, bank);

            if(!validator.getError().equals(""))
            {
                alert_error.setContentText(validator.getError());
                alert_error.showAndWait();
                validator.setError("");
            }
            else
            {
                bank.setId(1);
                bank.setFunds(bank.getFunds()-(Double.parseDouble(delivery_price.getText())*Double.parseDouble(quantity.getText())));

                for(Object provider:db.getSession().createQuery("from Providers").list())
                {
                    if((((Providers)provider).getId())==Integer.parseInt(id.getText()))
                    { provider_id=((Providers)provider).getId(); }
                }

                for(Object product1:db.getSession().createQuery("from Products").list())
                {
                    if((((Products)product1).getName()).equals(name.getText()) && ((Products)product1).getPrice()==Double.parseDouble(sale_price.getText()))
                    {
                        if((((Products)product1).getStock()==0)) { delivery.setFirst("Yes"); }
                        else delivery.setFirst("No");

                        Query<Products>query_products=db.getSession().createQuery("UPDATE Products set stock=:first where id=:second");
                        query_products.setParameter("first", ((Products)product1).getStock()+Integer.parseInt(quantity.getText()));
                        query_products.setParameter("second", ((Products)product1).getId());
                        query_products.executeUpdate();
                        if_updated++;
                    }
                }

                if(if_updated!=1)
                {
                    product.setName(name.getText());
                    product.setPrice(Double.parseDouble(sale_price.getText()));
                    product.setStock(Integer.parseInt(quantity.getText()));
                    delivery.setFirst("Yes");
                    db.getSession().save(product);
                }

                delivery.setProviderId(provider_id);
                delivery.setCbId(ControllerLogin.admin_or_operator);
                delivery.setDate(java.sql.Date.valueOf(date.getValue()));
                db.getSession().save(delivery);
                db.getSession().update(bank);
                db.getSession().getTransaction().commit();
                db.getSession().close();

                Session session=db.getFactory().openSession();
                List<Deliveries>list_deliveries=session.createQuery("from Deliveries").list();
                List<Products>list_products=session.createQuery("from Products").list();

                session.beginTransaction();
                p_products.setProviderId(provider_id);
                p_products.setProductId((list_products.get(list_products.size()-1)).getId());
                session.save(p_products);

                cd_deliveries.setCbId(ControllerLogin.admin_or_operator);
                cd_deliveries.setDeliveryid((list_deliveries.get(list_deliveries.size()-1)).getId());
                cd_deliveries.setRmoney(Double.parseDouble(delivery_price.getText())*Double.parseDouble(quantity.getText()));
                session.save(cd_deliveries);

                p_deliveries.setProductId((list_products.get(list_products.size()-1)).getId());
                p_deliveries.setDeliveryId((list_deliveries.get(list_deliveries.size()-1)).getId());
                p_deliveries.setQuantity(Integer.parseInt(quantity.getText()));
                p_deliveries.setDeliveryprice(Integer.parseInt(delivery_price.getText()));
                p_deliveries.setProviderId(provider_id);
                session.save(p_deliveries);
                session.getTransaction().commit();
                session.close();
                db.getFactory().close();

                information_alert.setContentText("Successfully created a new Delivery!");
                information_alert.showAndWait();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}