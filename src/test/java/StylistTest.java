import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class StylistTest {
  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, "melvin");
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteClientsQuery = "DELETE FROM clients *;";
      String deleteStylistsQuery = "DELETE FROM stylists *;";
      con.createQuery(deleteClientsQuery).executeUpdate();
      con.createQuery(deleteStylistsQuery).executeUpdate();
    }
  }
  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Stylist firstStylist = new Stylist("Household chores", 2, 3, 4);
    Stylist secondStylist = new Stylist("Household chores", 2, 3, 4);
    assertTrue(firstStylist.equals(secondStylist));
  }
  @Test
  public void save_savesIntoDatabase_true() {
    Stylist myStylist = new Stylist("Household chores", 2, 3, 4);
    myStylist.save();
    assertTrue(Stylist.all().get(0).equals(myStylist));
  }
  @Test
  public void all_returnsAllInstancesOfCategory_true() {
    Stylist firstStylist = new Stylist("Home", 2 , 3, 4);
    firstStylist.save();
    Stylist secondStylist = new Stylist("Work", 2, 3, 4);
    secondStylist.save();
    assertEquals(true, Stylist.all().get(0).equals(firstStylist));
    assertEquals(true, Stylist.all().get(1).equals(secondStylist));
  }
  @Test
  public void save_assignsIdToObject() {
    Stylist myStylist = new Stylist("Household chores", 2, 3, 4);
    myStylist.save();
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(myStylist.getId(), savedStylist.getId());
  }
  @Test
  public void getId_categoriesInstantiateWithAnId_1() {
    Stylist testStylist = new Stylist("Home",2 ,3 ,4);
    testStylist.save();
    assertTrue(testStylist.getId() > 0);
  }
  @Test
  public void find_returnsCategoryWithSameId_secondCategory() {
    Stylist firstStylist = new Stylist("Home",2 , 3, 4);
    firstStylist.save();
    Stylist secondStylist = new Stylist("Work", 2, 3, 4);
    secondStylist.save();
    assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
  }
  @Test
  public void getTasks_retrievesALlTasksFromDatabase_tasksList() {
    Stylist myStylist = new Stylist("Household chores", 2, 3, 4);
    myStylist.save();
    Client firstClient = new Client("Mow the lawn", 2, 2, myStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Do the dishes", 2, 2, myStylist.getId());
    secondClient.save();
    Client[] clients = new Client[] { firstClient, secondClient };
    assertTrue(myStylist.getClients().containsAll(Arrays.asList(clients)));
  }
}
