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
import Main.Tables.Providers;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ControllerListProviders implements Initializable
{
    @FXML TextField search;
    @FXML TableView<Providers>table;
    @FXML private TableColumn<Providers, Integer>id;
    @FXML private TableColumn<Providers, String>first;
    @FXML private TableColumn<Providers, String>middle;
    @FXML private TableColumn<Providers, String>last;
    @FXML private TableColumn<Providers, String>company;
    @FXML private TableColumn<Providers, Integer>vat;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Database db=new Database();
        final ObservableList<Providers>observable_list=FXCollections.observableArrayList(db.getSession().createQuery("from Providers").list());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        first.setCellValueFactory(new PropertyValueFactory<>("fname"));
        middle.setCellValueFactory(new PropertyValueFactory<>("mname"));
        last.setCellValueFactory(new PropertyValueFactory<>("lname"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        vat.setCellValueFactory(new PropertyValueFactory<>("vatnumber"));
        table.getItems().addAll(observable_list);

        FilteredList<Providers>filtered_list=new FilteredList<>(observable_list, e-> true);
        search.setOnKeyReleased(e-> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
        {
            filtered_list.setPredicate((Predicate<?super Providers>) provider->
            {
                if(newValue==null || newValue.isEmpty()) { return true; }
                String lower=newValue.toLowerCase();
                if((""+provider.getId()).contains(newValue)) { return true; }
                else if(provider.getFname().toLowerCase().contains(lower)) { return true; }
                else if(provider.getMname().toLowerCase().contains(lower)) { return true; }
                else if(provider.getLname().toLowerCase().contains(lower)) { return true; }
                else if(provider.getCompany().toLowerCase().contains(lower)) { return true; }
                else return(""+provider.getVatnumber()).contains(lower);
            });
        });

            SortedList<Providers>sorted_list=new SortedList<>(filtered_list);
            sorted_list.comparatorProperty().bind(table.comparatorProperty());
            table.getItems().clear();
            table.getItems().addAll(sorted_list);
        });

        db.getSession().close();
        db.getFactory().close();
    }
}