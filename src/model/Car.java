package model;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class Car {

	public static int TOTAL_CAR;
	public static int BONUS_POINT;
	public static ArrayList<Car> lCars = new ArrayList<Car>();

	private int carIdx;
	private int totalStreet;
	private ArrayList<Street> routes;
	private int currentUnit;

	
	public int getCarIdx() {
		return carIdx;
	}

	public void setCarIdx(int carIdx) {
		this.carIdx = carIdx;
	}

	public int getTotalStreet() {
		return totalStreet;
	}
	
	public void setTotalStreet(int totalStreet) {
		this.totalStreet = totalStreet;
	}
	
	public ArrayList<Street> getRoutes() {
		return routes;
	}
	
	public void setRoute(ArrayList<Street> routes) {
		this.routes = routes;
	}

	
	public int getCurrentUnit() {
		return currentUnit;
	}

	public void setCurrentUnit(int currentUnit) {
		this.currentUnit = currentUnit;
	}

	@Override
	public String toString() {
		
		String str = "";
		
		str += this.carIdx + " ";
		str += this.totalStreet + " ";
		
		for (Street route : routes) {
			str += route.getName() + " ";
		}
		
		return str;
	}

	/**
	 * 
	 * @param totalStreet
	 * @param route
	 */
	public Car(int carIdx, int totalStreet, ArrayList<Street> routes) {
		super();
		this.carIdx = carIdx;
		this.totalStreet = totalStreet;
		this.routes = routes;
		this.currentUnit = 0;
	}
	
	/**
	 * 
	 */
	public ImmutablePair<Street, Car> unitWork(int unitIdx) {
		
		int idx = 0;
		
		for (Street street : routes) {
			if ((street.getTime() + idx) == currentUnit) {
				if (Intersection.lIntersections.get(street.getEndIntersectionIdx()).isRoadAvailable(street, unitIdx)) {
					if (street.getQueue().peek().equals(this)) {
						currentUnit++;
						ImmutablePair<Street, Car> retval = new ImmutablePair<Street, Car>(street, this);
						return retval;
					}
				}
				break;
			}
			else if ((street.getTime() + idx) < currentUnit) {
				currentUnit++;
				break;
			}
			idx += street.getTime();
		}
		
		return null;
	}

}
