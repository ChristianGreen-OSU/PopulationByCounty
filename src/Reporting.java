package osu.cse2123;
/**
 * Main method that uses county and state classes in order to print out data 
 * 
 * @author ChristianBarrett
 * @version 4.25.2021
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Reporting {

	/**
	 * Reads initial file containing other file names and gets state names from it
	 * @return a list of State objects that contain county data from each state
	 */
	public static List<State> readFile (String fName, int sYear, int eYear) {
		List<State> states = new ArrayList<State>();
		try {
			Scanner sc = new Scanner(new File(fName));

			while (sc.hasNextLine())  {
				String split = sc.nextLine();
				String[] stateAndFile = split.split(",");
				State state = new State2();
				state.setName(stateAndFile[0]); //first is the state name
				String stateFileName = stateAndFile[1]; //second is the file name to find state data
				states.add(readStateFile(stateFileName, state, sYear, eYear)); //add state object result of readStateFile method
				//to the list of state objects
			}

			sc.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File was not found");
		}
		return states;
	}
	/**
	 * Looks into state specific file and reads that data into a State object
	 * @return a State object containing all county data
	 */
	public static State readStateFile (String stateFileName, State state, int sYear, int eYear) {
		try {
			Scanner stateFile = new Scanner(new File(stateFileName));
			//check if user input years are in data file
			String headers = stateFile.nextLine(); //get data headers
			String[] headersArr = headers.split(",");
			List<String> headersList = Arrays.asList(headersArr);
			
			//go line by line to get county data
			while (stateFile.hasNextLine()) {
				String[] splitArr = stateFile.nextLine().split(",");
				County county = new County2();
				county.setName(splitArr[0]);
				for (int i = 2010; i < 2020; i++) {
					county.addPopulation(i, Integer.parseInt(splitArr[i-2009]));;
				}
				state.addCounty(county); //once all population data is added to county, add county to state
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("State File not found");
		}
		return state;

	}
	/**
	 * Prints out transcript given the list of states and user entered years
	 */
	public static void transcript(List<State> states, int sYear, int eYear) {
		System.out.println("State/County            " + sYear + "         " 
				+ eYear + "       Growth");
		System.out.println("--------------- ------------ ------------ ------------");
		System.out.println();
		System.out.println("--------------- ------------ ------------ ------------"); 
		for (int i = 0; i < states.size(); i++) { //loop through states in list
			State currState = states.get(i);
			String sName = currState.getName();
			int sYearPop = currState.getPopulation(sYear);
			int eYearPop = currState.getPopulation(eYear);
			int growth = eYearPop - sYearPop;
			//PRINT OUT FOR STATE
			if (growth > 0) {
				System.out.printf("%-12s %,15d %,12d %+,12d", sName, sYearPop, eYearPop, growth);
			}
			else {
				System.out.printf("%-12s %,15d %,12d %,12d", sName, sYearPop, eYearPop, growth);
			}
			System.out.println();
			System.out.println("--------------- ------------ ------------ ------------");

			for (int j = 0; j < currState.getCounties().size(); j++) { //loop thru counties in state
				County currCounty = currState.getCountiesByPopulation(eYear).get(j);
				String cName = currCounty.getName();
				if (cName.length() > 12) { //in transcript, counties with names of size 13 or greater are cut off
					cName = cName.substring (0, 12);
				}
				sYearPop = currCounty.getPopulation(sYear);
				eYearPop = currCounty.getPopulation(eYear);
				growth = eYearPop - sYearPop;
				//PRINT OUT FOR EACH COUNTY
				if (growth >= 0) { //these if statements necessary to add a + sign if growth is positive
					System.out.printf("   %-14s %,10d %,12d %+,12d", cName, sYearPop, eYearPop, growth);
				}
				else if (growth < 0 ){
					System.out.printf("   %-14s %,10d %,12d %,12d", cName, sYearPop, eYearPop, growth);
				}
				System.out.println();
			}
			//PRINT OUT FOR AVERAGE AND MEDIAN
			System.out.println("--------------- ------------ ------------ ------------");
			System.out.printf("   %-9s %,12d %,12d", "Average pop.", avgPop(currState, sYear), avgPop(currState, eYear));
			System.out.println();
			System.out.printf("   %-9s %,13d %,12d", "Median pop.", medPop(currState, sYear), medPop(currState, eYear));
			System.out.println();
			//handles the very last read out of average and median where no lines come after
			if (i <(states.size() - 1)) {
				System.out.println();
				System.out.println("--------------- ------------ ------------ ------------");
			}
		}
	}
	/**
	 * Returns an int average of population for the entered state
	 * @param year year to get population averages from
	 * @return an int average of population for the entered state
	 */
	public static int avgPop (State currState, int year) {
		int average = 0;
		int pop = currState.getPopulation(year);
		average = pop / currState.getCounties().size();

		return average;
	}
	/**
	 * Returns an int median of population for the entered state
	 * @param year year to get population medians from
	 * @return an int median of population for the entered state
	 */
	public static int medPop (State currState, int year) {
		List<County> sortedList = new ArrayList<>();
		sortedList = currState.getCountiesByPopulation(year);
		int median = 0;
		int n = sortedList.size();

		if (n % 2 == 1) {
			median = sortedList.get(n/2).getPopulation(year);
		}
		else {
			median = (sortedList.get(n/2).getPopulation(year) + 
					sortedList.get((n/2) - 1).getPopulation(year)) / 2;
		}

		return median;
	}
	/**
	 * Executes non main methods and gets user inputs
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		//getting user input for years and population file
		System.out.print("Enter a list of population files: ");
		String file = s.nextLine();
		System.out.print("Enter a start year: ");
		int sYear = Integer.parseInt(s.nextLine());
		System.out.println("Enter an end year: ");
		int eYear = Integer.parseInt(s.nextLine());

		List<State> states = new ArrayList<State>();
		states = readFile(file, sYear, eYear); //call readFile method (String, int, int)

		transcript(states, sYear, eYear); //use list of states from earlier method and years found earlier

		s.close();
	}

}
