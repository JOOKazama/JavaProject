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
import sample.Tables.D2;
import java.util.List;
import java.util.function.Predicate;

public class ControllerDate2
{
    @FXML DatePicker date1, date2;
    @FXML Button but;
    @FXML TextField search;
    @FXML TableView<D2> table;
    @FXML private TableColumn<D2, String> date;
    @FXML private TableColumn<D2, String> first;
    @FXML private TableColumn<D2, String> middle;
    @FXML private TableColumn<D2, String> last;
    @FXML private TableColumn<D2, String> quantity;
    @FXML private TableColumn<D2, String> name;
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
                Configuration cfg = new Configuration(); cfg.configure("hibernate.cfg.xml");
                SessionFactory factory = cfg.buildSessionFactory();
                Session session = factory.openSession();

                table.getItems().clear();
                table.refresh();

                Query qry = session.createQuery("SELECT a.date, b.fname, b.mname, b.lname, c.quantity, d.name" +
                        " FROM Sales a inner join Clients b on a.clientid=b.id  " +
                        "inner join ProductSales c on a.id=c.saleid " +
                        "inner join Products d on d.id=c.productid" +
                        " where a.date between :d1 and :d2");
                qry.setParameter("d1", java.sql.Date.valueOf(date1.getValue()));
                qry.setParameter("d2", java.sql.Date.valueOf(date2.getValue()));

                List<Object[]> r = qry.getResultList();
                final ObservableList<D2> d=FXCollections.observableArrayList();

                date.setCellValueFactory(new PropertyValueFactory<>("date"));
                first.setCellValueFactory(new PropertyValueFactory<>("first"));
                middle.setCellValueFactory(new PropertyValueFactory<>("middle"));
                last.setCellValueFactory(new PropertyValueFactory<>("last"));
                quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));

                for(Object[] elements: r)
                {
                    D2 a = new D2();
                    a.setDate(""+elements[0]);
                    a.setFirst((String) elements[1]);
                    a.setMiddle((String) elements[2]);
                    a.setLast((String) elements[3]);
                    a.setQuantity(""+elements[4]);
                    a.setName((String) elements[5]);
                    d.add(a);
                }
                table.getItems().addAll(d);
                but.disableProperty().bind(Bindings.isNotEmpty(search.textProperty()));

                FilteredList<D2> fd=new FilteredList<>(d, e-> true);
                search.setOnKeyReleased(e -> { search.textProperty().addListener((observableValue, oldValue,  newValue) ->
                {
                    fd.setPredicate((Predicate<? super D2>) d2->
                    {
                        if (newValue == null || newValue.isEmpty()) { return true; }
                        String lower = newValue.toLowerCase();
                        if (d2.getDate().toLowerCase().contains(newValue)) { return true; }
                        else if (d2.getFirst().toLowerCase().contains(lower)) { return true; }
                        else if (d2.getMiddle().toLowerCase().contains(lower)) { return true; }
                        else if (d2.getLast().toLowerCase().contains(lower)) { return true; }
                        else if (d2.getQuantity().toLowerCase().contains(lower)) { return true; }
                        else if (d2.getName().toLowerCase().contains(lower)) { return true; }
                        return false;
                    });
                });

                    SortedList<D2> sd=new SortedList<>(fd);
                    sd.comparatorProperty().bind(table.comparatorProperty());
                    table.setItems(sd); });
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}