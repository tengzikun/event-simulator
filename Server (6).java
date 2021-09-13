
/**
 * This class creates a server to serve customers
 * if the server is free it will serve, else it is idle till the customer arrives
 * @author Tengzikun A0199547H
 */


package cs2030.simulator;

public class Server {
	final int id;
	public boolean serve =  false;
	public double serveTime = 0;
	public boolean resting = false;
	public double restTime = 0;

	/**
	 * Constructor that creates a server
	 * @param id - the id of the server
	 * @param serve - if the server is serving anyone
	 * @param serveTime - Time taken to serve
	 */
	Server(int id , boolean serve , double serveTime)  {
		this.id = id;
		this.serve = serve;
		this.serveTime = serveTime;
	}

	public int getId() {
		return this.id;
	}

	/**
	 * check if the server is free
	 * @param Customer -  takes in an customer
	 * @param Server[] - check the array of server for availablity
	 */
	public static Server isFree(Customer g,Server[] arrS) {
		for (Server s : arrS) {
			if (!s.resting && (s.serve == false||s.serveTime <= g.getArrivalTime())) {
				return s;
			}
		}
		return null;
	}

	/**
	 * This method configurate the boolean serve
	 * and update the serveTime
	 * @param g -  a customer
	 */
	public void serve(Customer g) {
		this.serve = true;
		this.serveTime = g.getServiceTime() + g.getArrivalTime();
	}

	/**
	 * This method is called when 
	 * the server done serving the 
	 * customer
	 */
	public void done() {
		this.serveTime = 0;
		this.serve = false;
	}

	/**
	 * This method creates and return a 
	 * server array
	 * @param no - the size of the sever array
	 * @return a 1D server array
	 */
	public static Server[] createArrS(int no) {
		Server[] arrS = new Server[no];
		for (int j = 0;j < no;j++) {
			arrS[j] = new Server(j + 1, false , 0);
		}
		return arrS;
	}

	/**
	 * to let the server rest
	 * @param t - time for the server to rest
	 */
	public void rest(double t) {
		this.resting = true;
		this.restTime = t;
	}

	/**
	 * This method is to 
	 * bring the server back
	 * from rest
	 */
	public void restFinish() {
		this.resting = false;
		this.restTime = 0;
	}
}





