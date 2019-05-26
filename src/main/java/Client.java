import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Client{
  private String name;
  private int visit;
  private int number;
  private int id;
  private int stylistId;

  public Client(String name, int visit, int number, int stylistId){
    this.name = name;
    this.visit = visit;
    this.number = number;
    this.stylistId = stylistId;
  }
  public String getName() {
    return name;
  }
  public int getVisit() {
    return visit;
  }
  public int getNumber() {
    return number;
  }
  public int getId() {
    return id;
  }
  public int getStylistId() {
    return stylistId;
  }
  public static List<Client> all() {
    String sql = "SELECT id, name, visit, number, stylistId FROM clients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }
  @Override
  public boolean equals(Object otherClient) {
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getName().equals(newClient.getName()) &&
      this.getId() == newClient.getId() &&
      this.getStylistId() == newClient.getStylistId();
    }
  }
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (name, visit, number, stylistId) VALUES (:name, :visit, :number, :stylistId)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("visit", this.visit)
      .addParameter("number", this.number)
      .addParameter("stylistId", this.stylistId)
      .executeUpdate()
      .getKey();
    }
  }
  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where id=:id";
      Client client = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Client.class);
      return client;
    }
  }

}
