import java.util.List;
import java.util.ArrayList;

public class Stylist{
  private String name;
  private int age;
  private int year;
  private String rate;
  private int id;

  public Stylist(String name, int age, int year, String rate){
    this.name = name;
    this.age = age;
    this.year = year;
    this.rate = rate;
  }
  public String getName() {
    return name;
  }
  public id getAge() {
    return age;
  }
  public int getYear() {
    return year;
  }
  public String getRate() {
    return rate;
  }
  public int getId() {
    return id;
  }
  public static Stylist find(int id) {
  }

}
