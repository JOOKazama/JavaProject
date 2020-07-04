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
import Main.InquirieClasses.DateMovFundsClass;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ControllerDateMovFunds
{
    @FXML DatePicker date1, date2;
    @FXML Button button;
    @FXML TextField search;
    @FXML TableView<DateMovFundsClass>table;
    @FXML private TableColumn<DateMovFundsClass, String>date;
    @FXML private TableColumn<DateMovFundsClass, String>money;
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
                ("SELECT e.date, l.smoney FROM Sales e inner join CashDesksSales l on e.id=l.saleid where e.date between :first and :second");
                query_objects.setParameter("first", java.sql.Date.valueOf(date1.getValue()));
                query_objects.setParameter("second", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]>list_objects=query_objects.getResultList();
                final ObservableList<DateMovFundsClass>observable_list=FXCollections.observableArrayList();

                date.setCellValueFactory(new PropertyValueFactory<>("date"));
                money.setCellValueFactory(new PropertyValueFactory<>("money"));

                for(Object[]object:list_objects)
                {
                    DateMovFundsClass element=new DateMovFundsClass();
                    element.setDate(""+object[0]);
                    element.setMoney("Money from a sale: "+object[1]);
                    observable_list.add(element);
                }

                Query<Object[]>query_objects1=db.getSession().createQuery
                ("SELECT e.date, l.rmoney FROM Deliveries e inner join CashDesksDelivery l on e.id=l.deliveryid where e.date between :first and :second");
                query_objects1.setParameter("first", java.sql.Date.valueOf(date1.getValue()));
                query_objects1.setParameter("second", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]>list_objects1=query_objects1.getResultList();

                for(Object[] elements:list_objects1)
                {
                    DateMovFundsClass element=new DateMovFundsClass();
                    element.setDate(""+elements[0]);
                    element.setMoney("Money from a delivery: "+elements[1]);
                    observable_list.add(element);
                }

                table.getItems().addAll(observable_list);
                button.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));

                FilteredList<DateMovFundsClass>filtered_list=new FilteredList<>(observable_list, e-> true);
                search.setOnKeyReleased(e-> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
                filtered_list.setPredicate((Predicate<?super DateMovFundsClass>) funds->
                {
                    if(newValue==null || newValue.isEmpty()) { return true; }
                    if(funds.getDate().contains(newValue)) { return true; }
                    else return funds.getMoney().contains(newValue);
                }));

                    SortedList<DateMovFundsClass>sorted_list=new SortedList<>(filtered_list);
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