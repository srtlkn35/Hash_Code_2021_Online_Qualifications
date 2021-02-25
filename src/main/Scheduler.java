package main;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;

import model.Car;
import model.Intersection;
import model.Street;

public class Scheduler {

	public static int TOTAL_SECOND;

	public static void initialize() {
		
		for (Intersection intersection : Intersection.lIntersections) {
			for (Street street : intersection.getIncomingStreets()) {
				HashMap<Street, Integer> instruction = intersection.getInstruction();
				instruction.put(street, 1);
			}			
		}		
	}

	public static void process() {
		
		ArrayList<ImmutablePair<Street, Car>> deleteQueues = new ArrayList<ImmutablePair<Street, Car>>();
		
		for (int i = 0; i < TOTAL_SECOND; i++) {
			
			for (Intersection intersection : Intersection.lIntersections) {
				intersection.unitWork(i);
			}
			
			for (Car car : Car.lCars) {
				
				ImmutablePair<Street, Car> retval = car.unitWork(i);
				if (retval != null) {
					deleteQueues.add(retval);
				}
			}
			
			for (int j = 0; j < deleteQueues.size(); j++) {
				Street street = deleteQueues.get(i).getLeft();
				Car car = deleteQueues.get(i).getRight();
				if (!street.getQueue().remove().equals(car)) {
					System.out.println("EXCEPTION!!!");
					System.out.println(street.toString());
					System.out.println(car.toString());
					System.exit(0);
				}
			}
		}
	}
}
