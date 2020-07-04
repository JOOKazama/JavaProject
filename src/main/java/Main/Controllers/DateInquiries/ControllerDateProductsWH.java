package Main.Controllers.DateInquiries;
import Main.DatabaseValidators.Database;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.query.Query;
import Main.InquirieClasses.DateProductsWHClass;
import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ControllerDateProductsWH
{
    @FXML DatePicker date1, date2;
    @FXML Button button;
    @FXML TextField search;
    @FXML TableView<DateProductsWHClass>table;
    @FXML private TableColumn<DateProductsWHClass, String>name;
    @FXML private TableColumn<DateProductsWHClass, String>price;
    Alert alert_error=new Alert(Alert.AlertType.ERROR);

    public void show()
    {
        try
        {
            if(date1.getValue()==null || date2.getValue()==null || date2.getValue().isBefore(date1.getValue()))
            {
                alert_error.setHeaderText("Error!");
                alert_error.setContentText("The field must not be empty or have a invalid value!");
                alert_error.showAndWait();
            }
            else
            {
                Database db=new Database();
                table.getItems().clear();
                table.refresh();

                Query<Object[]>query_objects=db.getSession().createQuery
                ("SELECT e.name, m.deliveryprice FROM Products e inner join ProductDeliveries m on e.id=m.productid "+
                "inner join Deliveries o on o.id=m.deliveryid where o.date between :first and :second");
                query_objects.setParameter("first", java.sql.Date.valueOf(date1.getValue()));
                query_objects.setParameter("second", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]>list_objects=query_objects.getResultList();
                final ObservableList<DateProductsWHClass>observable_list=FXCollections.observableArrayList();

                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                price.setCellValueFactory(new PropertyValueFactory<>("price"));

                for(Object[]object:list_objects)
                {
                    DateProductsWHClass element=new DateProductsWHClass();
                    element.setName(""+object[0]);
                    element.setPrice(""+object[1]);

                    if(!observable_list.contains(element)) { observable_list.add(element); }
                }

                table.getItems().addAll(observable_list);
                button.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));

                FilteredList<DateProductsWHClass>filtered_list=new FilteredList<>(observable_list, e-> true);
                search.setOnKeyReleased(e-> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
                {
                    filtered_list.setPredicate((Predicate<?super DateProductsWHClass>) product->
                    {
                        if(newValue==null || newValue.isEmpty()) { return true; }
                        String lower_case=newValue.toLowerCase();
                        if(product.getName().toLowerCase().contains(lower_case)) { return true; }
                        else return product.getPrice().contains(newValue);
                    });
                });

                    SortedList<DateProductsWHClass>sorted_list=new SortedList<>(filtered_list);
                    sorted_list.comparatorProperty().bind(table.comparatorProperty());
                    table.getItems().clear();
                    table.getItems().addAll(sorted_list);
                });

                db.getSession().close();
                db.getFactory().close();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}