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
import Main.Tables.Products;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ControllerListProducts implements Initializable
{
    @FXML TextField search;
    @FXML TableView<Products>table;
    @FXML private TableColumn<Products, Integer>id;
    @FXML private TableColumn<Products, String>name;
    @FXML private TableColumn<Products, Double>price;
    @FXML private TableColumn<Products, Integer>stock;

    @Override public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Database db=new Database();
        final ObservableList<Products>observable_list=FXCollections.observableArrayList(db.getSession().createQuery("from Products").list());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table.getItems().addAll(observable_list);

        FilteredList<Products>filtered_list=new FilteredList<>(observable_list, e-> true);
        search.setOnKeyReleased(e-> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
        filtered_list.setPredicate((Predicate<?super Products>) product->
        {
            if(newValue==null || newValue.isEmpty()) { return true; }
            if((""+product.getId()).contains(newValue)) { return true; }
            else if(product.getName().toLowerCase().contains(newValue.toLowerCase())) { return true; }
            else if((""+product.getPrice()).contains(newValue)) { return true; }
            else return(""+product.getStock()).contains(newValue);
        }));

            SortedList<Products>sorted_list=new SortedList<>(filtered_list);
            sorted_list.comparatorProperty().bind(table.comparatorProperty());
            table.getItems().clear();
            table.getItems().addAll(sorted_list);
        });

        db.getSession().close();
        db.getFactory().close();
    }
}