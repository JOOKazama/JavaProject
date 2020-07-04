package Main.Controllers.Operations;
import Main.DatabaseValidators.Database;
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
import Main.Tables.Deliveries;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ControllerListDeliveries implements Initializable
{
    @FXML TextField search;
    @FXML TableView<Deliveries>table;
    @FXML private TableColumn<Deliveries, Integer>id;
    @FXML private TableColumn<Deliveries, Integer>providerid;
    @FXML private TableColumn<Deliveries, Date>date;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Database db=new Database();
        final ObservableList<Deliveries>observable_list=FXCollections.observableArrayList(db.getSession().createQuery("from Deliveries").list());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        providerid.setCellValueFactory(features-> features.getValue().nameProperty());
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.getItems().addAll(observable_list);

        FilteredList<Deliveries>filtered_list=new FilteredList<>(observable_list, e-> true);
        search.setOnKeyReleased(e-> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
        filtered_list.setPredicate((Predicate<?super Deliveries>) delivery->
        {
            if(newValue==null || newValue.isEmpty()) { return true; }
            if((""+delivery.getId()).contains(newValue)) { return true; }
            else if((""+delivery.getProviderId()).contains(newValue)) { return true; }
            else return(""+delivery.getDate()).contains(newValue);
        }));

            SortedList<Deliveries>sorted_list=new SortedList<>(filtered_list);
            sorted_list.comparatorProperty().bind(table.comparatorProperty());
            table.getItems().clear();
            table.getItems().addAll(sorted_list);
        });

        db.getSession().close();
        db.getFactory().close();
    }
}