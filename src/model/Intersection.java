package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Intersection {

	public static ArrayList<Intersection> lIntersections = new ArrayList<Intersection>();

	private int intersectionIdx;
	private ArrayList<Street> incomingStreets;
	private ArrayList<Street> outcomingStreets;
	private HashMap<Street, Integer> instruction;
	
	public int getIntersectionIdx() {
		return intersectionIdx;
	}
	
	public void setIntersectionIdx(int intersectionIdx) {
		this.intersectionIdx = intersectionIdx;
	}
	
	public ArrayList<Street> getIncomingStreets() {
		return incomingStreets;
	}
	
	public void setIncomingStreets(ArrayList<Street> incomingStreets) {
		this.incomingStreets = incomingStreets;
	}
	
	public ArrayList<Street> getOutcomingStreets() {
		return outcomingStreets;
	}
	
	public void setOutcomingStreets(ArrayList<Street> outcomingStreets) {
		this.outcomingStreets = outcomingStreets;
	}
	
	public HashMap<Street, Integer> getInstruction() {
		return instruction;
	}

	public void setInstruction(HashMap<Street, Integer> instruction) {
		this.instruction = instruction;
	}

	@Override
	public String toString() {
		
		String str = "";
		
		str += this.intersectionIdx + " ";
		
		str += "IS: ";
		for (Street street : incomingStreets) {
			str += street.getName() + " ";
		}
		
		str += "OS: ";
		for (Street street : outcomingStreets) {
			str += street.getName() + " ";
		}
		
		return str;
	}

	/**
	 * 
	 * @param intersectionIdx
	 */
	public Intersection(int intersectionIdx) {
		super();
		this.intersectionIdx = intersectionIdx;
		this.incomingStreets = new ArrayList<Street>();
		this.outcomingStreets = new ArrayList<Street>();
		this.instruction = new HashMap<Street, Integer>();
	}
	
	/**
	 * 
	 */
	public void unitWork(int unitIdx) {
		
		int currIdx = unitIdx % instruction.size();
		int idx = 0;

		for(Map.Entry<Street, Integer> entry : instruction.entrySet()) {
			
		    Street key = entry.getKey();
		    Integer value = entry.getValue();

			if (idx == currIdx) {
				key.setLedStatus(true);
			}
			else {
				key.setLedStatus(false);
			}
        
            idx += value;
        }
	}
	
	/**
	 * 
	 * @param street
	 * @param unitIdx
	 * @return
	 */
	public boolean isRoadAvailable(Street street, int unitIdx) {
		
		int currIdx = unitIdx % instruction.size();
		int idx = 0;

		for(Map.Entry<Street, Integer> entry : instruction.entrySet()) {
			
		    Street key = entry.getKey();
		    Integer value = entry.getValue();

		    if (idx == currIdx) {
			    if ((key.equals(street))) {
			    	return true;
			    }
			}
        
            idx += value;
        }
	
		return false;
	}
}
