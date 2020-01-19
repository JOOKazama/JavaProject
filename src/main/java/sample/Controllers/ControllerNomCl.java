package sample.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sample.Tables.Clients;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ControllerNomCl implements Initializable
{
    @FXML TextField search;
    @FXML TableView<Clients> table;
    @FXML private TableColumn<Clients, Integer> id;
    @FXML private TableColumn<Clients, String> first;
    @FXML private TableColumn<Clients, String> middle;
    @FXML private TableColumn<Clients, String> last;
    @FXML private TableColumn<Clients, String> company;
    @FXML private TableColumn<Clients, Integer> vat;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Query qry1 = session.createQuery("from Clients"); List<Clients> r = qry1.getResultList();
        final ObservableList<Clients>d=FXCollections.observableArrayList(r);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        first.setCellValueFactory(new PropertyValueFactory<>("fname"));
        middle.setCellValueFactory(new PropertyValueFactory<>("mname"));
        last.setCellValueFactory(new PropertyValueFactory<>("lname"));
        company.setCellValueFactory(new PropertyValueFactory<>("cname"));
        vat.setCellValueFactory(new PropertyValueFactory<>("vatnumber"));
        table.getItems().addAll(d);

        FilteredList<Clients> fd=new FilteredList<>(d, e-> true);
        search.setOnKeyReleased(e -> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
        {
            fd.setPredicate((Predicate<? super Clients>) cl->
            {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lower = newValue.toLowerCase();
                if ((""+cl.getId()).contains(newValue)) { return true; }
                else if (cl.getFname().toLowerCase().contains(lower)) { return true; }
                else if (cl.getMname().toLowerCase().contains(lower)) { return true; }
                else if (cl.getLname().toLowerCase().contains(lower)) { return true; }
                else if (cl.getCname().toLowerCase().contains(lower)) { return true; }
                else if ((""+cl.getVatnumber()).contains(lower)) { return true; }
                return false;
            });
        });

            SortedList<Clients> sd=new SortedList<>(fd);
            sd.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sd); });
            session.close(); factory.close();
    }
}
