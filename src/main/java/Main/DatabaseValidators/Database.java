package Main.DatabaseValidators;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database
{
    private Session session;
    private SessionFactory factory;

    public Database()
    {
        Configuration cfg=new Configuration();
        cfg.configure("hibernate.cfg.xml");
        factory=cfg.buildSessionFactory();
        session=factory.openSession();
    }

    public Session getSession() { return session; }
    public void setSession(Session session) { this.session=session; }
    public SessionFactory getFactory() { return factory; }
    public void setFactory(SessionFactory factory) { this.factory=factory; }
}
