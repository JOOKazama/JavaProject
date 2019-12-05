package sample;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="\"ProductDeliveries\"")
public class ProductDeliveries implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "\"ID\"", unique=true) @Id private int id;
    @Column(name = "\"ProductID\"", nullable=false) private int productid;
    @Column(name = "\"DeliveryPrice\"", nullable=false) private double deliveryprice;
    @Column(name = "\"Quantity\"", nullable=false) private int quantity;
    @Column(name = "\"DeliveryID\"", nullable=false) private int deliveryid;
    @Column(name = "\"ProviderID\"", nullable=false) private int providerid;

    public int getId() {return id;} public void setId(int id) {this.id=id;}
    public int getProductId() {return productid;} public void setProductId(int productid) {this.productid=productid;}
    public double getDeliveryprice() {return deliveryprice;} public void setDeliveryprice(double deliveryprice) {this.deliveryprice=deliveryprice;}
    public int getQuantity() {return quantity;} public void setQuantity(int quantity) {this.quantity=quantity;}
    public int getDeliveryId() {return deliveryid;} public void setDeliveryId(int deliveryid) {this.deliveryid=deliveryid;}
    public int getProviderId() {return providerid;} public void setProviderId(int providerid) {this.providerid=providerid;}
}
