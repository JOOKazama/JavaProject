package sample.Tables;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="\"ProductSales\"")
public class ProductSales implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "\"ID\"", unique=true) @Id private int id;
    @Column(name = "\"ProductID\"", nullable=false) private int productid;
    @Column(name = "\"Quantity\"", nullable=false) private int quantity;
    @Column(name = "\"SaleID\"", nullable=false) private int saleid;

    public int getId() {return id;} public void setId(int id) {this.id=id;}
    public int getProductId() {return productid;} public void setProductId(int productid) {this.productid=productid;}
    public int getQuantity() {return quantity;} public void setQuantity(int quantity) {this.quantity=quantity;}
    public int getSaleId() {return saleid;} public void setSaleId(int saleid) {this.saleid=saleid;}
}
