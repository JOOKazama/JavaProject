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
import sample.Tables.Sales;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ControllerNomSales implements Initializable
{
    @FXML TextField search;
    @FXML TableView<Sales> table;
    @FXML private TableColumn<Sales, Integer> id;
    @FXML private TableColumn<Sales, Integer> clientid;
    @FXML private TableColumn<Sales, Date> date;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Query qry1 = session.createQuery("from Sales"); List<Sales> r = qry1.getResultList();
        final ObservableList<Sales> d= FXCollections.observableArrayList(r);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientid.setCellValueFactory(features -> features.getValue().nameProperty());
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.getItems().addAll(d);

        FilteredList<Sales> fd=new FilteredList<>(d, e-> true);
        search.setOnKeyReleased(e -> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
        {
            fd.setPredicate((Predicate<? super Sales>) sales->
            {
                if (newValue == null || newValue.isEmpty()) { return true; }
                if ((""+sales.getId()).contains(newValue)) { return true; }
                else if ((""+sales.getClientId()).contains(newValue)) { return true; }
                else if ((""+sales.getDate()).contains(newValue)) { return true; }
                return false;
            });
        });

            SortedList<Sales> sd=new SortedList<>(fd);
            sd.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sd); });
            session.close(); factory.close();
    }
}