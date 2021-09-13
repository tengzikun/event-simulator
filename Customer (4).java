
/**
 * This class creates a customer to be served
 * @author Tengzikun A0199547H
 */
package cs2030.simulator;

public class Customer {
	public final int id;
	public double arrTime;
	public int state = ARRIVES;
	public int serverId = -1;
	public double serveTime = 0;
	public boolean selfCheckout = false;
	public boolean greedy = false;
	public static final int ARRIVES = 1;
	public static final int SERVED = 2;
	public static final int LEAVES = 3;
	public static final int DONE = 4;
	public static final int WAITS = 5;

	/**
	 * Constructor that creates a customer
	 * @param id - the id of the customer
	 * @param arrTime - the time which the customer arrives
	 */
	public Customer(int id,double arrTime) {
		this.id = id;
		this.arrTime = arrTime;
	}

	/**
	 * Constructor that creates a customer
	 * @param id - the id of the customer
	 * @param arrTime - the time which the customer arrives
	 * @param serverId - the id of the server related to the customer
	 */
	public Customer(int id,double arrTime,int serverId) {
		this.id = id;
		this.arrTime = arrTime;
		this.serverId = serverId;
	}

	/**
	 * @param id - the id of the customer	
	 * @param arrTime - the time when the customer arrives
	 * @param serverId - the id of the server
	 * @param greedy - whether the customer is greedy
	 */
	public Customer(int id,double arrTime,int serverId,boolean greedy) {
		this.id = id;
		this.arrTime = arrTime;
		this.serverId = serverId;
		this.greedy = greedy;
	}

	public double getArrivalTime() {
		return this.arrTime;
	}

	public double getServiceTime() {
		return this.serveTime;
	}

	public int getServerId() {
		return this.serverId;
	}


	public int getId() {
		return this.id;
	}

	/**
	 * change the state of the customer
	 * @param state - if the customer arrives,waits,leaves,served or done
	 */
	public void changeState(int state) {
		this.state = state;
	}

	/**
	 * to see if the customer gets served by self checkout or human server
	 * @param server 
	 * @param number
	 */
	public void whoServe(int server,int number) {
		if (server > number) {
			this.selfCheckout = true;
			this.serverId = server;
		} else {
			this.serverId = server;
		}
	}

	/**
	 * @param time- time served
	 */
	public void timeServed (double time) {
		this.serveTime = time;
	}

	/**
	 * @param customer - takes in a 2D customer array
	 * @return  the customer in the correct pair 
	 */
	public Pair ifGreedyQueueWhere(Customer[][] customer) {
		if (this.greedy) {
			return this.greedyQueueWhere(customer);
		} else {
			return this.queueWhere(customer);
		}
	}

	/**
	 * @param customer -  the 2D array of customers
	 * @return the place where the customer queue 
	 */
	public Pair queueWhere (Customer[][] customer) {
		int size = customer.length;
		int size2 = customer[0].length;
		for (int row = 0;row < size;row++) {
			for (int col = 0;col < size2;col++) {
				if (customer[row][col] == null) {    
					return new Pair(row,col);
				}
			}
		}
		return null;
	}

	/**
	 * @param customer - takes in a 2D array of customer
	 * @return the place where the customer queue if the customer is greedy
	 */
	public Pair greedyQueueWhere(Customer[][] customer) {
		int shortestL = customer[0].length + 1;
		Pair content = null;
		for (int i = 0;i < customer.length;i++) {
			int qSize = 0;
			for (int j = 0;j < customer[0].length;j++) {
				if (customer[i][j] != null) {
					qSize++;
				} else if (qSize < shortestL) {
					shortestL = qSize;
					content = new Pair(i,j);
					break;
				} else {
					break;
				}
			}
		}
		return content;
	}

	/**
	 * @param customer - takes in a 2D array of customer
	 * @param server - the server
	 * @return the customer
	 */
	public static Customer getWaiting(Customer[][] customer, int server){
		int size = customer.length;
		int size2 = customer[0].length;
		if (size > server) {
			for (int j = 0;j < size2;j++) {
				if (customer[server-1][j] != null) {
					return customer[server-1][j];
				}
			}
		}
		else {
			for (int i = 0;i < size2 ;i++) {
				if (customer[size-1][i] != null) {
					return customer[size-1][i];
				}
			}
		}
		return null;
	}

	/**
	 * @param customer - takes in a 2D array of customer
	 * @param server - takes in a server number
	 */
	public static void next(Customer[][] customer,int server) {
		int size = customer.length;
		int size2 = customer[0].length;
		if (size > server) {
			for (int j = 0;j < size2-1;j++) {
				customer[server-1][j] = customer[server-1][j+1];
			}
			customer[server-1][size2-1] = null;
		} else {
			for (int i = 0;i < size2-1;i++) {
				customer[size - 1][i] = customer[size - 1][i + 1];
			}
			customer[size - 1][size2 - 1] = null;
		}
	}

	/**
	 * if the customer use the self checkout
	 */
	public void checkOut(int n) {
		if (this.serverId > n) {
			this.selfCheckout = true;
		}
	}

	/**
	 * all the Strings used to produce the correct statement depending
	 * on the state of the customer
	 */
	@Override
	public String toString() {
		if (this.greedy) {
			if (!this.selfCheckout) {
				if (this.state == SERVED) {            
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + "(greedy) served by server " + 
						this.serverId;
				} else if (this.state == ARRIVES) {
					return String.format("%.3f", this.arrTime) + " " +
						this.id + "(greedy) arrives";
				} else if (this.state == LEAVES) {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + "(greedy) leaves";
				} else if (this.state == DONE) {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + "(greedy) done serving by server " +
						this.serverId;
				} else {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + "(greedy) waits to be served by server " +
						this.serverId;
				}
			} else {
				if (this.state == SERVED) {            
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + "(greedy) served by self-check " + 
						this.serverId;
				} else if (this.state == ARRIVES) {
					return String.format("%.3f", this.arrTime) + " " +
						this.id + "(greedy) arrives";
				} else if (this.state == LEAVES) {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + "(greedy) leaves";
				} else if (this.state == DONE) {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + "(greedy) done serving by self-check " +
						this.serverId;
				} else {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + "(greedy) waits to be served by self-check " +
						this.serverId;
				}
			}
		} else {
			if ( !this.selfCheckout) {
				if (this.state == SERVED) {            
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + " served by server " + 
						this.serverId;
				} else if (this.state == ARRIVES) {
					return String.format("%.3f", this.arrTime) + " " +
						this.id + " arrives";
				} else if (this.state == LEAVES) {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + " leaves";
				} else if (this.state == DONE) {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + " done serving by server " +
						this.serverId;
				} else {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + " waits to be served by server " +
						this.serverId;
				}
			} else {
				if (this.state == SERVED) {            
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + " served by self-check " + 
						this.serverId;
				} else if (this.state == ARRIVES) {
					return String.format("%.3f", this.arrTime + " " +
							this.id) + " arrives";
				} else if (this.state == LEAVES) {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + " leaves";
				} else if (this.state == DONE) {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + " done serving by self-check " +
						this.serverId;
				} else {
					return String.format("%.3f", this.arrTime) + " " + 
						this.id + " waits to be served by self-check " +
						this.serverId;
				}
			}
		}
	}
}



