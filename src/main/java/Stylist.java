import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Stylist{
  private String name;
  private int age;
  private int year;
  private int rate;
  private int id;

  public Stylist(String name, int age, int year, int rate){
    this.name = name;
    this.age = age;
    this.year = year;
    this.rate = rate;
  }
  public String getName() {
    return name;
  }
  public int getAge() {
    return age;
  }
  public int getYear() {
    return year;
  }
  public int getRate() {
    return rate;
  }
  public int getId() {
    return id;
  }
  public static List<Stylist> all() {
    String sql = "SELECT id, name, age, year, rate FROM stylists";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }
  @Override
  public boolean equals(Object otherStylist) {
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getName().equals(newStylist.getName()) &&
      this.getId() == newStylist.getId();
    }
  }
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists (name, age, year, rate) VALUES (:name, :age, :year, :rate)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("age", this.age)
      .addParameter("year", this.year)
      .addParameter("rate", this.rate)
      .executeUpdate()
      .getKey();
    }
  }
  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylists where id=:id";
      Stylist stylist = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }
  public List<Client> getClients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where stylistId=:id";
      return con.createQuery(sql)
      .addParameter("id", this.id)
      .executeAndFetch(Client.class);
    }
  }
}
