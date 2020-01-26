package sample.Tables;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name="\"Deliveries\"")
public class Deliveries implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "\"ID\"", unique=true) @Id private int id;
    @Column(name = "\"ProviderID\"", nullable=false) private int providerid;
    @Column(name = "\"CreatedByID\"", nullable=false) private int cbid;
    @Column(name = "\"Date\"", nullable=false, columnDefinition = "DATE") private Date date;
    @Column(name = "\"First\"", nullable=false) private String first;

    public int getId() {return id;} public void setId(int id) {this.id=id;}
    public int getProviderId() {return providerid;} public void setProviderId(int providerid) {this.providerid=providerid;}
    public int getCbId() {return cbid;} public void setCbId(int cbid) {this.cbid=cbid;}
    public Date getDate() {return date;} public void setDate(Date date) {this.date=date;}
    public String getFirst() {return first;} public void setFirst(String first) {this.first=first;}

    public ObservableValue<Integer> nameProperty()
    { ObservableValue<Integer> o = new ReadOnlyObjectWrapper<>(providerid); return o; }
}