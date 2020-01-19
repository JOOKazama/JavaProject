package sample.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sample.Tables.Products;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ControllerNomProdd implements Initializable
{
    @FXML TextField search;
    @FXML TableView<Products> table;
    @FXML private TableColumn<Products, Integer> id;
    @FXML private TableColumn<Products, String> name;
    @FXML private TableColumn<Products, Double> price;
    @FXML private TableColumn<Products, Integer> stock;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Query qry1 = session.createQuery("from Products"); List<Products> r = qry1.getResultList();
        final ObservableList<Products> d= FXCollections.observableArrayList(r);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table.getItems().addAll(d);

        FilteredList<Products> fd=new FilteredList<>(d, e-> true);
        search.setOnKeyReleased(e -> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
        {
            fd.setPredicate((Predicate<? super Products>) prodd->
            {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lower = newValue.toLowerCase();
                if ((""+prodd.getId()).contains(newValue)) { return true; }
                else if (prodd.getName().toLowerCase().contains(lower)) { return true; }
                else if ((""+prodd.getPrice()).contains(newValue)) { return true; }
                else if ((""+prodd.getStock()).contains(newValue)) { return true; }
                return false;
            });
        });

            SortedList<Products> sd=new SortedList<>(fd);
            sd.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sd); });
            session.close(); factory.close();
    }
}
