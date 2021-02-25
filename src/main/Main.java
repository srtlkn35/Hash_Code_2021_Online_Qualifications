package main;

import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import model.Car;
import model.Intersection;
import model.Street;

public class Main {

	private static final int ARG_IDX_INPUT_FILE_NAME = 0;
	private static final int ARG_IDX_OUTPUT_FILE_NAME = 1;
	
	private static final String DEFAULT_INPUT_DATA_FILE_NAME = "a.txt";
	private static final String DEFAULT_OUTPUT_DATA_FILE_PREFIX = "report_of_";

	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		
		String inputFileName = parseCommandLine(args).get(ARG_IDX_INPUT_FILE_NAME);
		String outputFileName = parseCommandLine(args).get(ARG_IDX_OUTPUT_FILE_NAME);
				
		FileOps.processInputFile(inputFileName);
		
		System.out.println("*** Cars: ");
		for (Car car : Car.lCars) {
			System.out.println(car.toString());
		}

		System.out.println("*** Streets: ");
		for (Street street : Street.lStreets) {
			System.out.println(street.toString());
		}

		System.out.println("*** Intersections: ");
		for (Intersection intersection : Intersection.lIntersections) {
			System.out.println(intersection.toString());
		}
		
		Scheduler.initialize();
		
		Scheduler.process();

		FileOps.processOutputFile(outputFileName);						
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	private static ArrayList<String> parseCommandLine(String args[]) {
		
		ArrayList<String> argList = new ArrayList<String>();
        Options options = new Options();

		/* Return Default Args */
		if (args.length == 0) {
	        argList.clear();
	        argList.add(DEFAULT_INPUT_DATA_FILE_NAME);	
	        argList.add(DEFAULT_OUTPUT_DATA_FILE_PREFIX + DEFAULT_INPUT_DATA_FILE_NAME);
	        return argList;
		}

        Option oInput = new Option("i", "input", true, "the input data");
        oInput.setRequired(true);
        options.addOption(oInput);

        Option oOutput = new Option("o", "output", true, "the input data");
        oOutput.setRequired(true);
        options.addOption(oOutput);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("hashcode2021_final_task", options);
            System.exit(1);
        }

        argList.clear();
        argList.add(cmd.getOptionValue("input"));
        argList.add(cmd.getOptionValue("output"));

        System.out.println(String.format("Arguman List: "));
        System.out.println(String.format("\t the input data : %s", cmd.getOptionValue("input")));
        System.out.println(String.format("\t the output data: %s", cmd.getOptionValue("output")));
        System.out.println();
        
        return argList;
	}
}
