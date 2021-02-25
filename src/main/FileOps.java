package main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Car;
import model.Intersection;
import model.Street;

public class FileOps {

	public static boolean PRINT_INPUT_FILE_CONTENT = false;
	public static boolean PRINT_OUTPUT_FILE_CONTENT = true;
	
	/**
	 * 
	 * @param fileName
	 */
	public static void processInputFile(String fileName) {
		
		List<String> fileContent = null;
		int numOfLine = 0;
		
		int totalSeconds = 0;
		int numOfIntersections = 0;
		int numOfStreets = 0;
		int numOfCars = 0;
		int numOfBonusPointsForEachCar = 0;		
		
        try {
            fileContent = Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
		
		if (PRINT_INPUT_FILE_CONTENT == true) {
			for (String string : fileContent) {
				System.out.println(string);
			}
			System.out.println();
		}

        /* Read Variables From Line1: '<totalSeconds> <numOfIntersections> <numOfStreets> <numOfCars> <numOfBonusPointsForEachCar>' */
		Pattern pattern = Pattern.compile("(.*) (.*) (.*) (.*) (.*)");
		Matcher matcher = pattern.matcher(fileContent.get(numOfLine));
		numOfLine++;
        if(matcher.matches()) {
        	totalSeconds = Integer.parseInt(matcher.group(1));
        	numOfIntersections = Integer.parseInt(matcher.group(2));
        	numOfStreets = Integer.parseInt(matcher.group(3));
        	numOfCars = Integer.parseInt(matcher.group(4));
        	numOfBonusPointsForEachCar = Integer.parseInt(matcher.group(5));
        	
        	Scheduler.TOTAL_SECOND = totalSeconds;
        	Car.BONUS_POINT = numOfBonusPointsForEachCar;
        	Car.TOTAL_CAR = numOfCars;
        }

        /* Read Streets <startIntersectionIdx> <endIntersectionIdx> <name> <time> */
		for (int index=0; index<numOfIntersections; index++) {
			
        	Intersection intersection = new Intersection(index);
        	Intersection.lIntersections.add(intersection);
		}

        /* Read Streets <startIntersectionIdx> <endIntersectionIdx> <name> <time> */
		for (int index=0; index<numOfStreets; index++) {
			pattern = Pattern.compile("(.*) (.*) (.*) (.*)");
			matcher = pattern.matcher(fileContent.get(numOfLine));
			numOfLine++;
	        if(matcher.matches()) {
	        	int startIntersectionIdx = Integer.parseInt(matcher.group(1));
	        	int endIntersectionIdx = Integer.parseInt(matcher.group(2));
	        	
	        	String name = matcher.group(3);
	        	int time = Integer.parseInt(matcher.group(4));
	        	
	        	Street street = new Street(startIntersectionIdx, endIntersectionIdx, name, time);
	        	Street.lStreets.add(street);

	        	ArrayList<Street> outcomingStreets = Intersection.lIntersections.get(startIntersectionIdx).getOutcomingStreets();
	        	outcomingStreets.add(street);
	        	Intersection.lIntersections.get(startIntersectionIdx).setOutcomingStreets(outcomingStreets);
	        	
	        	ArrayList<Street> incomingStreets = Intersection.lIntersections.get(endIntersectionIdx).getIncomingStreets();
	        	incomingStreets.add(street);
	        	Intersection.lIntersections.get(endIntersectionIdx).setIncomingStreets(incomingStreets);

	        	//System.out.println(street.toString());
	        }
		}

		int carIdx = 0;
        for (int i = numOfLine; i < fileContent.size(); i++) {
			        	
        	String[] inputs = fileContent.get(numOfLine).split(" ");
        	numOfLine++;
        	
        	int totalStreet = Integer.parseInt(inputs[0]);
        	ArrayList<Street> route = new ArrayList<Street>();

        	Car car = new Car(carIdx, totalStreet, route);
        	Car.lCars.add(car);
        	carIdx++;
        	
    		for (Street street : Street.lStreets) {
				if (street.getName().equals(inputs[1])) {
					route.add(street);
					Queue<Car> queue = street.getQueue();
					queue.add(car);
					break;
				}
			}        		
        	
        	//System.out.println(car.toString());
        }
	}

	/**
	 * 
	 * @param fileName
	 */
	public static void processOutputFile(String fileName) {
		
		Path pOutput = Paths.get(fileName);
		ArrayList<String> fileContent = new ArrayList<String>();

		fileContent.add(Integer.toString(Intersection.lIntersections.size()));
		
		for (Intersection intersection : Intersection.lIntersections) {
			
			fileContent.add(Integer.toString(intersection.getIntersectionIdx()));

			fileContent.add(Integer.toString(intersection.getInstruction().size()));

			HashMap<Street, Integer> instruction = intersection.getInstruction();
			
			for(Map.Entry<Street, Integer> entry : instruction.entrySet()) {
				
			    Street key = entry.getKey();
			    Integer value = entry.getValue();

				fileContent.add(key.getName() + " " + Integer.toString(value));
	        }
		}

		if (PRINT_OUTPUT_FILE_CONTENT == true) {
			for (String string : fileContent) {
				System.out.println(string);
			}
			System.out.println();
		}
		
		try {
			Files.write(pOutput, fileContent, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
            System.exit(1);
		}
	}
}
