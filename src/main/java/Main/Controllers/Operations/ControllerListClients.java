package Main.Controllers.Operations;
import Main.DatabaseValidators.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Main.Tables.Clients;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ControllerListClients implements Initializable
{
    @FXML TextField search;
    @FXML TableView<Clients>table;
    @FXML private TableColumn<Clients, Integer>id;
    @FXML private TableColumn<Clients, String>first;
    @FXML private TableColumn<Clients, String>middle;
    @FXML private TableColumn<Clients, String>last;
    @FXML private TableColumn<Clients, String>company;
    @FXML private TableColumn<Clients, Integer>vat;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Database db=new Database();
        final ObservableList<Clients>observable_list=FXCollections.observableArrayList(db.getSession().createQuery("from Clients").list());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        first.setCellValueFactory(new PropertyValueFactory<>("fname"));
        middle.setCellValueFactory(new PropertyValueFactory<>("mname"));
        last.setCellValueFactory(new PropertyValueFactory<>("lname"));
        company.setCellValueFactory(new PropertyValueFactory<>("cname"));
        vat.setCellValueFactory(new PropertyValueFactory<>("vatnumber"));
        table.getItems().addAll(observable_list);

        FilteredList<Clients>filtered_list=new FilteredList<>(observable_list, e-> true);
        search.setOnKeyReleased(e-> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
        filtered_list.setPredicate((Predicate<?super Clients>) client->
        {
            if(newValue==null || newValue.isEmpty()) { return true; }
            String lower_case=newValue.toLowerCase();
            if((""+client.getId()).contains(newValue)) { return true; }
            else if(client.getFname().toLowerCase().contains(lower_case)) { return true; }
            else if(client.getMname().toLowerCase().contains(lower_case)) { return true; }
            else if(client.getLname().toLowerCase().contains(lower_case)) { return true; }
            else if(client.getCname().toLowerCase().contains(lower_case)) { return true; }
            else return(""+client.getVatnumber()).contains(lower_case);
        }));

            SortedList<Clients>sorted_list=new SortedList<>(filtered_list);
            sorted_list.comparatorProperty().bind(table.comparatorProperty());
            table.getItems().clear();
            table.getItems().addAll(sorted_list);
        });

        db.getSession().close();
        db.getFactory().close();
    }
}