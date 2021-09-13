/**
 * This class is given
 * @author Tengzikun A0199547H
 */



package cs2030.simulator;

import java.util.Random;

public class RandomGenerator {
	private final Random rngArrival;
	private final Random rngService;
	private final Random rngRest;
	private final Random rngRestPeriod;
	private final Random rngTimeoutPeriod;
	private final Random rngCustomerType;
	private final double customerArrivalRate;
	private final double customerServiceRate;
	private final double serverRestingRate;

	RandomGenerator(final int n, final double customerArrivalRate,                  
			final double customerServiceRate,
			final double serverRestingRate) {
		this.rngArrival = new Random(n);
		this.rngService = new Random(n + 1);
		this.rngRest = new Random(n + 2);
		this.rngRestPeriod = new Random(n + 3);
		this.rngCustomerType = new Random(n + 4);
		this.rngTimeoutPeriod = new Random(n + 5);
		this.customerArrivalRate = customerArrivalRate;
		this.customerServiceRate = customerServiceRate;
		this.serverRestingRate = serverRestingRate;
	}

	double genInterArrivalTime() {
		return -Math.log(this.rngArrival.nextDouble()) / this.customerArrivalRate;
	}

	double genServiceTime() {
		return -Math.log(this.rngService.nextDouble()) / this.customerServiceRate;
	}

	double genRandomRest() {
		return this.rngRest.nextDouble();
	}

	double genRestPeriod() {
		return -Math.log(this.rngRestPeriod.nextDouble()) / this.serverRestingRate;
	}

	double genCustomerType() {
		return this.rngCustomerType.nextDouble();
	}
}

