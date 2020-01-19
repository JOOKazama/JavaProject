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
import sample.Tables.Providers;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ControllerNomProd implements Initializable
{

    @FXML TextField search;
    @FXML TableView<Providers> table;
    @FXML private TableColumn<Providers, Integer> id;
    @FXML private TableColumn<Providers, String> first;
    @FXML private TableColumn<Providers, String> middle;
    @FXML private TableColumn<Providers, String> last;
    @FXML private TableColumn<Providers, String> company;
    @FXML private TableColumn<Providers, Integer> vat;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Query qry1 = session.createQuery("from Providers"); List<Providers> r = qry1.getResultList();
        final ObservableList<Providers> d= FXCollections.observableArrayList(r);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        first.setCellValueFactory(new PropertyValueFactory<>("fname"));
        middle.setCellValueFactory(new PropertyValueFactory<>("mname"));
        last.setCellValueFactory(new PropertyValueFactory<>("lname"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        vat.setCellValueFactory(new PropertyValueFactory<>("vatnumber"));
        table.getItems().addAll(d);

        FilteredList<Providers> fd=new FilteredList<>(d, e-> true);
        search.setOnKeyReleased(e -> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
        {
            fd.setPredicate((Predicate<? super Providers>) prod->
            {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lower = newValue.toLowerCase();
                if ((""+prod.getId()).contains(newValue)) { return true; }
                else if (prod.getFname().toLowerCase().contains(lower)) { return true; }
                else if (prod.getMname().toLowerCase().contains(lower)) { return true; }
                else if (prod.getLname().toLowerCase().contains(lower)) { return true; }
                else if (prod.getCompany().toLowerCase().contains(lower)) { return true; }
                else if ((""+prod.getVatnumber()).contains(lower)) { return true; }
                return false;
            });
        });

            SortedList<Providers> sd=new SortedList<>(fd);
            sd.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sd); });
        session.close(); factory.close();
    }
}