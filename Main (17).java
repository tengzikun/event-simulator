import cs2030.simulator.CustomerComparator;
import cs2030.simulator.Convert;
import cs2030.simulator.Pair;
import cs2030.simulator.Customer;
import cs2030.simulator.QueueGenerator;
import cs2030.simulator.RandomGenerator;
import cs2030.simulator.Server;
import java.util.Scanner;
import java.util.PriorityQueue;


/**
 * Does the main computation of the events happening to each Customer
 * Scanner to scan inputs
 * base on their state, the customer class is updated
 * base on their state, they will be assigned to a human server or self checkout
 * @author Tengzikun A0199547H
 */ class Main {
	 public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in);
		 int seed = sc.nextInt();
		 int Servers = sc.nextInt();
		 int selfCheckout = sc.nextInt();
		 int qLength = sc.nextInt();
		 int total = sc.nextInt();
		 double arrivalRate = sc.nextDouble();
		 double serviceRate = sc.nextDouble();
		 double restRate = sc.nextDouble();
		 double pREST = sc.nextDouble();
		 double pGREEDY = sc.nextDouble();
		 sc.close();
		 int numServed = 0;
		 int numLeft = 0;
		 double waitingTime = 0;
		 Customer[][] arrC = QueueGenerator.genCQueue(Servers,
				 selfCheckout, qLength);
		 Server[] arrS = Server.createArrS(Servers + selfCheckout);
		 PriorityQueue<Customer> pQueue = QueueGenerator.genPQueue(seed,arrivalRate,
				 serviceRate,restRate,
				 pGREEDY, total);
		 Convert generator = new Convert(seed,arrivalRate,serviceRate,restRate);
		 while (pQueue.size() != 0) {
			 Customer c = pQueue.poll();
			 if (c.state == Customer.ARRIVES) {
				 System.out.println(c.toString());
				 Server s = Server.isFree(c,arrS);
				 Pair waitWhere = c.ifGreedyQueueWhere(arrC);
				 if (s != null) {
					 c.changeState(Customer.SERVED);
					 c.whoServe(s.getId(),Servers);
					 numServed = numServed+1;
					 pQueue.add(c);
					 System.out.println(c.toString());
				 }
				 else if (waitWhere != null) { 
					 c.changeState(Customer.WAITS);
					 c.whoServe(waitWhere.p1+1,Servers);
					 arrC[waitWhere.p1][waitWhere.p2] = c;
					 System.out.println(c.toString());
				 }
				 else {
					 c.changeState(Customer.LEAVES);
					 numLeft = numLeft+1;
					 System.out.println(c.toString());
				 }
			 }
			 else if (c.state == Customer.SERVED) {
				 double serviceTime = generator.getRandomServiceTime();
				 double newTime = c.getArrivalTime() + serviceTime;
				 Customer newc = new Customer(c.getId(),newTime,c.getServerId(),c.greedy);
				 newc.checkOut(Servers);
				 newc.timeServed(serviceTime);
				 newc.changeState(Customer.DONE);
				 arrS[newc.getServerId()-1].serve(newc);
				 pQueue.add(newc);
			 }
			 else if (c.state == Customer.DONE) {
				 arrS[c.getServerId()-1].done();
				 System.out.println(c.toString());
				 Customer newc2 = Customer.getWaiting(arrC,c.getServerId());
				 if (c.getServerId()>Servers) {
					 if (newc2 != null) {
						 Customer.next(arrC,c.getServerId());
						 Customer newc3 = new Customer(newc2.getId(),
								 c.getArrivalTime(),c.getServerId(),newc2.greedy);
						 newc3.checkOut(Servers);
						 newc3.changeState(Customer.SERVED);
						 numServed = numServed + 1;
						 waitingTime = waitingTime + (c.getArrivalTime()-newc2.getArrivalTime());
						 pQueue.add(newc3);
						 System.out.println(newc3.toString());
					 }
				 } else {
					 double chanceOfRest = generator.getRandomRest();
					 if (newc2 != null && chanceOfRest >= pREST) {
						 Customer.next(arrC,c.getServerId());
						 Customer newc3 = new Customer(newc2.getId(),
								 c.getArrivalTime(),c.getServerId(),newc2.greedy);
						 numServed = numServed+1;
						 newc3.changeState(Customer.SERVED);
						 waitingTime = waitingTime + (c.getArrivalTime() - newc2.getArrivalTime());
						 pQueue.add(newc3);
						 System.out.println(newc3.toString());
					 } else if (chanceOfRest<pREST) {
						 double restTime = generator.getRestPeriod();
						 arrS[c.getServerId()-1].rest(c.getArrivalTime() + restTime);
						 Customer newc = new Customer(c.getId(),
								 (c.getArrivalTime() + restTime),c.getServerId());
						 newc.changeState(Customer.WAITS);
						 pQueue.add(newc);
					 }
				 }
			 }
			 else {
				 arrS[c.getServerId()-1].restFinish();
				 Customer newc2 = Customer.getWaiting(arrC,c.getServerId());
				 if (newc2 != null) {
					 Customer.next(arrC,c.getServerId());
					 Customer newc3 = new Customer(newc2.getId(),c.getArrivalTime(),c.getServerId(),newc2.greedy);
					 newc3.changeState(Customer.SERVED);
					 numServed = numServed+1;
					 waitingTime = waitingTime+(c.getArrivalTime()-newc2.getArrivalTime());
					 pQueue.add(newc3);
					 System.out.println(newc3.toString());
				 }
			 }
		 }
		 if (arrS.length == 0) {
			 System.out.println("[" + String.format("%.3f", 0.000) + " " + 
					 numServed + " " + numLeft + "]");
		 } else {
			 System.out.println("[" + String.format("%.3f", waitingTime / numServed) + " " + 
					 numServed + " " + numLeft + "]");
		 }
	 }
 }






