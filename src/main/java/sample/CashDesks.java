package sample;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Table(name="\"CashDesks\"")

public class CashDesks implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "\"ID\"", unique=true) @Id private int id;
    @Column(name = "\"Cash\"", nullable=false) private double cash;
    @Column(name = "\"Date\"", nullable=false, columnDefinition = "DATE") private Date date;
    @Column(name = "\"CreatedByID\"", nullable=false) private int cbid;
    @Column(name = "\"SaleID\"", nullable=false) private int saleid;
    @Column(name = "\"DeliveryID\"", nullable=false) private int deliveryid;

    public int getId() {return id;} public void setId(int id) {this.id=id;}
    public double getCash() {return cash;} public void setCash(double cash) {this.cash=cash;}
    public Date getDate() {return date;} public void setDate(Date date) {this.date=date;}
    public int getCbId() {return cbid;} public void setCbId(int cbid) {this.cbid=cbid;}
    public int getSaleId() {return saleid;} public void setSaleId(int saleid) {this.saleid=saleid;}
    public int getDeliveryId() {return deliveryid;} public void setDeliveryid(int deliveryid) {this.deliveryid=deliveryid;}

}
