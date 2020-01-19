package sample.Tables;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="\"Users\"")
public class Users implements Serializable
{
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "\"ID\"", unique=true) @Id private int id;
    @Column(name = "\"FirstName\"", nullable=false) private String fname;
    @Column(name = "\"MiddleName\"", nullable=false) private String mname;
    @Column(name = "\"LastName\"", nullable=false) private String lname;
    @Column(name = "\"Username\"", nullable=false)  private String username;
    @Column(name = "\"Password\"", nullable=false)  private String password;
    @Column(name = "\"RoleID\"", nullable=false) private int roleid;

    public int getId() {return id;} public void setId(int id) {this.id=id;}
    public String getFname() {return fname;} public void setFname(String fname) {this.fname=fname;}
    public String getMname() {return mname;} public void setMname(String mname) {this.mname=mname;}
    public String getLname() {return lname;} public void setLname(String lname) {this.lname=lname;}
    public String getUsername() {return username;} public void setUsername(String username) {this.username=username;}
    public String getPassword() {return password;} public void setPassword(String password) {this.password=password;}
    public int getRoleId() {return roleid;} public void setRoleId(int roleid) {this.roleid=roleid;}

}

