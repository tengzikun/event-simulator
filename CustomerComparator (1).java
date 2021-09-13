package cs2030.simulator;
import java.util.Comparator;

/**
 * This class compares 2 Customers' arrival time and id
 * @author Tengzikun A0199547H
 */
public class CustomerComparator implements Comparator<Customer> {
  
  /**
   * @param Customer g1 - first Customer
   * @param Customer g2 - second Customer
   * compares their arrival time and id
   */
  @Override
  public int compare(Customer g1,Customer g2) {
    if (g1.getArrivalTime() < g2.getArrivalTime()) {
      return -1;
    }
    else if (g1.getArrivalTime() > g2.getArrivalTime()) {
      return 1;
    }
    else if (g1.getId() > g2.getId()) {
      return 1;
    }
    else if (g1.getId() < g2.getId()) {
      return -1;
    } else {
      return 0;
    }
  }
}

