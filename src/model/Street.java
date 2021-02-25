package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Street {

	public static ArrayList<Street> lStreets = new ArrayList<Street>();
	
	private int startIntersectionIdx;
	private int endIntersectionIdx;
	private String name;
	private int time;
	private boolean ledStatus;
	private Queue<Car> queue; 
	
	public int getStartIntersectionIdx() {
		return startIntersectionIdx;
	}
	
	public void setStartIntersectionIdx(int startIntersectionIdx) {
		this.startIntersectionIdx = startIntersectionIdx;
	}
	
	public int getEndIntersectionIdx() {
		return endIntersectionIdx;
	}
	
	public void setEndIntersectionIdx(int endIntersectionIdx) {
		this.endIntersectionIdx = endIntersectionIdx;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}

	
	public boolean isLedStatus() {
		return ledStatus;
	}

	public void setLedStatus(boolean ledStatus) {
		this.ledStatus = ledStatus;
	}

	public Queue<Car> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Car> queue) {
		this.queue = queue;
	}

	@Override
	public String toString() {
		
		String str = "";
		
		str += this.startIntersectionIdx + " ";
		str += this.endIntersectionIdx + " ";
		str += this.name + " ";
		str += this.time + " ";
		str += this.ledStatus + " ";
		
		for (Car car : this.queue) {
			str += car.getCarIdx() + " ";
		}
		
		return str;
	}

	/**
	 * 
	 * @param startIntersectionIdx
	 * @param endIntersectionIdx
	 * @param name
	 * @param time
	 */
	public Street(int startIntersectionIdx, int endIntersectionIdx, String name, int time) {
		super();
		this.startIntersectionIdx = startIntersectionIdx;
		this.endIntersectionIdx = endIntersectionIdx;
		this.name = name;
		this.time = time;
		this.ledStatus = false;
		this.queue = new LinkedList<Car>();
	}
}
