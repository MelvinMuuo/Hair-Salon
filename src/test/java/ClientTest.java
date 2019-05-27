import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {
  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, "melvin");
  }
  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients *;";
      con.createQuery(sql).executeUpdate();
    }
  }
  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Client firstClient = new Client("Mow the lawn", 2, 2, 1);
    Client secondClient = new Client("Mow the lawn", 2, 2, 1);
    assertTrue(firstClient.equals(secondClient));
  }
  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Client myClient = new Client("Mow the lawn", 2, 2, 1);
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }
  @Test
  public void all_returnsAllInstancesOfClient_true() {
    Client firstClient = new Client("Mow the lawn", 3, 3, 1);
    firstClient.save();
    Client secondClient = new Client("Buy groceries", 3, 4, 1);
    secondClient.save();
    assertEquals(true, Client.all().get(0).equals(firstClient));
    assertEquals(true, Client.all().get(1).equals(secondClient));
  }
  @Test
  public void save_assignsIdToObject() {
    Client myClient = new Client("Mow the lawn", 3, 3, 1);
    myClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(myClient.getId(), savedClient.getId());
  }
  @Test
  public void getId_clientsInstantiateWithAnID() {
    Client myClient = new Client("Mow the lawn", 2, 2, 1);
    myClient.save();
    assertTrue(myClient.getId() > 0);
  }
  @Test
  public void find_returnsTaskWithSameId_secondTask() {
    Client firstClient = new Client("Mow the lawn", 2, 2, 1);
    firstClient.save();
    Client secondClient = new Client("Buy groceries", 2, 2, 1);
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()), secondClient);
  }
  @Test
  public void save_savesCategoryIdIntoDB_true() {
    Stylist myStylist = new Stylist("Household chores", 2, 3, 4);
    myStylist.save();
    Client myClient = new Client("Mow the lawn",2 ,2, myStylist.getId());
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertEquals(savedClient.getStylistId(), myStylist.getId());

  }
  @Test
  public void update_updatesClientName_true() {
    Client myClient = new Client("Mow the lawn", 2, 2, 1);
    myClient.save();
    myClient.update("Take a nap", 2, 2);
    assertEquals("Take a nap", Client.find(myClient.getId()).getName());
  }
  @Test
  public void delete_deletesClient_true() {
    Client myClient = new Client("Mow the lawn", 2, 2, 1);
    myClient.save();
    int myClientId = myClient.getId();
    myClient.delete();
    assertEquals(null, Client.find(myClientId));
  }

}
