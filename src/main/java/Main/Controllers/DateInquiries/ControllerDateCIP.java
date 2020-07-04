package Main.Controllers.DateInquiries;
import Main.DatabaseValidators.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import org.hibernate.query.Query;
import java.util.List;

@SuppressWarnings("unchecked")
public class ControllerDateCIP
{
    public Button button;
    @FXML DatePicker date1, date2;
    @FXML ListView<String>list_view=new ListView<>();
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
                double income=0, costs=0, profits;

                Query<Double>query_objects=db.getSession().createQuery
                ("SELECT l.smoney FROM Sales e inner join CashDesksSales l on e.id=l.saleid where e.date between :first and :second");
                query_objects.setParameter("first", java.sql.Date.valueOf(date1.getValue()));
                query_objects.setParameter("second", java.sql.Date.valueOf(date2.getValue()));
                List<Double>list_objects=query_objects.getResultList();
                ObservableList<String>list_observable=FXCollections.observableArrayList();

                for(Double value:list_objects) { income=income+value; }
                list_observable.add("Income: "+income);

                Query<Double>query_objects1=db.getSession().createQuery
                ("SELECT l.rmoney FROM Deliveries e inner join CashDesksDelivery l on e.id=l.deliveryid where e.date between :first and :second");
                query_objects1.setParameter("first", java.sql.Date.valueOf(date1.getValue()));
                query_objects1.setParameter("second", java.sql.Date.valueOf(date2.getValue()));
                List<Double>list_objects1=query_objects1.getResultList();

                for(Double object:list_objects1) { costs=costs+object; }
                list_observable.add("Costs: "+costs);
                profits=income-costs;
                list_observable.add("Profits(or lack of): "+profits);
                list_view.setItems(list_observable);

                db.getSession().close();
                db.getFactory().close();
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}