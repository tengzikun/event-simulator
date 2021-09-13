/**
 * This class is used to override the random generator class
 * as the generator class is private
 * The main purpose of this class is to call the random generator
 * @author Tengzikun A0199547H
 */


package cs2030.simulator;

public class Convert {
	int seed;
	double lamda;
	double mu;
	double restingRate;
	static RandomGenerator rgen;

	/**
	 * 
	 * @param seed - given  
	 * @param lamda - given
	 * @param mu - given
	 * @param restingRate - given
	 */
	public Convert (int seed,double lamda, double mu, double restingRate) {
		rgen = new RandomGenerator(seed,lamda,mu,restingRate);
	}

	public double getRandomArrivalTime() {
		double timeInterval = rgen.genInterArrivalTime(); 
		return timeInterval;  
	}

	public double getRandomServiceTime() {
		double timeInterval = rgen.genServiceTime();
		return timeInterval;
	}

	public double getRandomRest() {
		double rest = rgen.genRandomRest();
		return rest;
	}

	public double getRestPeriod() {
		double restT = rgen.genRestPeriod();
		return restT;
	}

	public double getGreedyCust() {
		double p = rgen.genCustomerType();
		return p;
	}
}

