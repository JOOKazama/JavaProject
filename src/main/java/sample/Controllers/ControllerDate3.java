package sample.Controllers;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sample.Tables.D3;
import java.util.List;
import java.util.function.Predicate;

public class ControllerDate3
{
    @FXML DatePicker date1, date2;
    @FXML Button but;
    @FXML TextField search;
    @FXML TableView<D3> table;
    @FXML private TableColumn<D3, String> date;
    @FXML private TableColumn<D3, String> money;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);


    public void showw()
    {
        try
        {
            if(date1.getValue()==null || date2.getValue()==null || date2.getValue().isBefore(date1.getValue()))
            {
                errorAlert.setHeaderText("Error!");
                errorAlert.setContentText("The field must not be empty or have a invalid value!");
                errorAlert.showAndWait();
            }
            else
            {
                Configuration cfg = new Configuration();
                cfg.configure("hibernate.cfg.xml");
                SessionFactory factory = cfg.buildSessionFactory();
                Session session = factory.openSession();

                table.getItems().clear();
                table.refresh();

                Query qry = session.createQuery("SELECT e.date, l.smoney FROM Sales e inner join CashDesks1 l on e.id=l.saleid where e.date between :d1 and :d2");
                qry.setParameter("d1", java.sql.Date.valueOf(date1.getValue()));
                qry.setParameter("d2", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]> r = qry.getResultList();

                final ObservableList<D3> d=FXCollections.observableArrayList();

                date.setCellValueFactory(new PropertyValueFactory<>("date"));
                money.setCellValueFactory(new PropertyValueFactory<>("money"));

                for(Object[] elements: r)
                {
                    D3 a = new D3();
                    a.setDate(""+elements[0]);
                    a.setMoney("Money from a sale: "+elements[1]);
                    d.add(a);
                }

                Query qry1 = session.createQuery("SELECT e.date, l.rmoney FROM Deliveries e inner join CashDesks l on e.id=l.deliveryid where e.date between :d1 and :d2");
                qry1.setParameter("d1", java.sql.Date.valueOf(date1.getValue()));
                qry1.setParameter("d2", java.sql.Date.valueOf(date2.getValue()));
                List<Object[]> r1 = qry1.getResultList();

                for(Object[] elements: r1)
                {
                    D3 a = new D3();
                    a.setDate(""+elements[0]);
                    a.setMoney("Money from a delivery: "+elements[1]);
                    d.add(a);
                }
                table.getItems().addAll(d);
                but.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));

                FilteredList<D3> fd=new FilteredList<>(d, e-> true);
                search.setOnKeyReleased(e -> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
                {
                    fd.setPredicate((Predicate<? super D3>) d3->
                    {
                        if (newValue == null || newValue.isEmpty()) { return true; }
                        if (d3.getDate().contains(newValue)) { return true; }
                        else if (d3.getMoney().contains(newValue)) { return true; }
                        return false;
                    });
                });

                    SortedList<D3> sd=new SortedList<>(fd);
                    sd.comparatorProperty().bind(table.comparatorProperty());
                    table.setItems(sd); });
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}