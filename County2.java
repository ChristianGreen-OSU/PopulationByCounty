package osu.cse2123;

import java.util.TreeMap;

/**
 * Concrete class for county population data
 * 
 * @author ChristianBarrett
 * @version 4.24.2021
 *
 */
public class County2 implements County{

	private String name;
	private TreeMap<Integer, Integer> ypMap;
	
	
	public County2() {
		this.name = "";
		this.ypMap = new TreeMap<>();
	}
	/**
	 * Sets the name of the county
	 * @param name the county name
	 * @postcond this county name is set to name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the county
	 * @return the name of this county
	 */
	public String getName() { return this.name; }

	/**
	 * Adds a population value for a specific year
	 * @param year the year to set the population for
	 * @param pop the population for the county in year
	 * @postcond the year/pop pair is added to this county
	 */
	public void addPopulation(int year, int pop) {
		this.ypMap.put(year, pop);
	}

	/**
	 * Returns the population for a given year
	 * @param year the year to retrieve the population for
	 * @return the population for this county in the year year
	 */
	public int getPopulation(int year) { 
		return this.ypMap.get(year);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.name + " ");
		for (int i = 2010; i < 2020; i++) {
			sb.append(getPopulation(i));;
			sb.append(" ");
		}
		
		return sb.toString();
	}

}