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
import Main.Tables.Sales;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ControllerListSales implements Initializable
{
    @FXML TextField search;
    @FXML TableView<Sales>table;
    @FXML private TableColumn<Sales, Integer>id;
    @FXML private TableColumn<Sales, Integer>clientid;
    @FXML private TableColumn<Sales, Date>date;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Database db=new Database();
        final ObservableList<Sales>observable_list=FXCollections.observableArrayList(db.getSession().createQuery("from Sales").list());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientid.setCellValueFactory(features-> features.getValue().nameProperty());
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.getItems().addAll(observable_list);

        FilteredList<Sales>filtered_list=new FilteredList<>(observable_list, e-> true);
        search.setOnKeyReleased(e -> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
        filtered_list.setPredicate((Predicate<?super Sales>) sales->
        {
            if(newValue==null || newValue.isEmpty()) { return true; }
            if((""+sales.getId()).contains(newValue)) { return true; }
            else if((""+sales.getClientId()).contains(newValue)) { return true; }
            else return(""+sales.getDate()).contains(newValue);
        }));

            SortedList<Sales>sorted_list=new SortedList<>(filtered_list);
            sorted_list.comparatorProperty().bind(table.comparatorProperty());
            table.getItems().clear();
            table.getItems().addAll(sorted_list);
        });

        db.getSession().close();
        db.getFactory().close();
    }
}