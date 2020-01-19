package sample.Tables;
import java.io.Serializable;
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
    @Column(name = "\"CreatedByID\"", nullable=false) private int cbid;
    @Column(name = "\"ReceiveMoney\"", nullable=false) private double rmoney;
    @Column(name = "\"DeliveryID\"", nullable=false) private int deliveryid;

    public int getId() {return id;} public void setId(int id) {this.id=id;}
    public int getCbId() {return cbid;} public void setCbId(int cbid) {this.cbid=cbid;}
    public double getRMoney() {return rmoney;} public void setRmoney(double rmoney) {this.rmoney=rmoney;}
    public int getDeliveryId() {return deliveryid;} public void setDeliveryid(int deliveryid) {this.deliveryid=deliveryid;}
}
