package Main.Tables;
import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Table(name="\"CashDesks1\"")

public class CashDesksSales implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="\"ID\"", unique=true) @Id private int id;
    @Column(name="\"CreatedByID\"", nullable=false) private int cbid;
    @Column(name="\"SaleID\"", nullable=false) private int saleid;
    @Column(name="\"SaleMoney\"", nullable=false) private double smoney;

    public int getId() { return id; }
    public void setId(int id) {this.id=id;}
    public int getCbId() { return cbid; }
    public void setCbId(int cbid) { this.cbid=cbid; }
    public int getSaleId(int id) { return saleid; }
    public void setSaleId(int saleid) { this.saleid=saleid; }
    public double getSMoney() { return smoney; }
    public void setSmoney(double smoney) { this.smoney=smoney; }
}