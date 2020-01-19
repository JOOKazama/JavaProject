package sample.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sample.Tables.Products;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSees implements Initializable
{
    @FXML TableView<Products> table;
    @FXML private TableColumn<Products, String> name;
    @FXML private TableColumn<Products, Double> price;
    @FXML private TableColumn<Products, Integer> stock;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();

        Query qry = session.createQuery("from Products where stock>0");
        ObservableList l = FXCollections.observableArrayList(qry.list());

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table.getItems().addAll(l);
        session.close(); factory.close();
    }
}