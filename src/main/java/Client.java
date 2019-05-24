import java.util.List;
import java.util.ArrayList;

public class Client{
  private String name;
  private String visit;
  private int number;
  private int id;

  public Client(String name, int visit, int number){
    this.name = name;
    this.visit = visit;
    this.number = number;
  }
  public String getName() {
    return name;
  }
  public int visit() {
    return visit;
  }
  public int number() {
    return number;
  }
  public int getId() {
    return id;
  }
  public static Client find(int id) {
  }
