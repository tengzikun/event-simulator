/**
 * This class generate queues
 * @author Tengzikun A0199547H
 */

package cs2030.simulator;
import java.util.PriorityQueue;

public class QueueGenerator {

	/**
	 * This method generate a queue of customers which will be created in 
	 * the main class
	 * @param seed
	 * @param arrivalRate
	 * @param servingRate
	 * @param restingRate
	 * @param greedy
	 * @param total
	 * @return a queue of customers
	 */
	public static PriorityQueue<Customer> genPQueue (int seed,double arrivalRate,
			double servingRate,double restingRate,
			double greedy,int total) {
		RandomGenerator generator = new RandomGenerator (seed,arrivalRate,
				servingRate,restingRate);
		CustomerComparator compare = new CustomerComparator();
		PriorityQueue<Customer> queue = new PriorityQueue<Customer>(compare);
		double time = 0;
		int now = 0;
		while (total > now) {
			double greedyP = generator.genCustomerType();
			now = now + 1;
			if (now == 1 && (greedyP < greedy)) {
				Customer g = new Customer(now,time);
				g.greedy = true;
				queue.add(g);
			}
			else if (now == 1 && (greedyP >= greedy)) {
				Customer g = new Customer(now,time);
				queue.add(g);
			}
			else if (greedyP < greedy) {
				time = time + generator.genInterArrivalTime();
				Customer g = new Customer(now,time);
				g.greedy = true;
				queue.add(g);
			} else {
				time = time + generator.genInterArrivalTime();
				Customer g = new Customer(now,time);
				queue.add(g);
			}
		}
		return queue;
	}


	/**
	 * This method generate a array of customers
	 * which will be called in the main class
	 * @param server
	 * @param selfcheckout
	 * @param maxlength
	 * @return a 2D array of customers
	 */
	public static Customer[][] genCQueue(int server,int selfcheckout,int maxlength) {
		if (selfcheckout == 0) {
			Customer[][] arr = new Customer[server][maxlength];
			return arr;
		}
		else {
			Customer[][] arr = new Customer[server+1][maxlength];
			return arr;
		}
	}
}


