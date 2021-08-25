package osu.cse2123;
/**
 * Concrete class for state data
 * 
 * @author ChristianBarrett
 * @version 4.24.2021
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class State2 implements State{

	private String stateName;
	private List<County> countyList = new ArrayList<>();

	/**
	 * Sets the name of the state
	 * @param name the state name
	 * @postcond this state name is set to name
	 */
	public void setName(String name) {
		this.stateName = name;
	}

	/**
	 * Returns the name of the state
	 * @return the name of this state
	 */
	public String getName() { return this.stateName; }

	/**
	 * Adds a county to this state
	 * @param county county to add to this state
	 * @postcond county is added to the state's collection of counties
	 */
	public void addCounty(County county) {
		this.countyList.add(county);
	}

	/**
	 * Returns a county based on its name
	 * @param name name of the county to retrieve
	 * @precond a county by the name provided is in the state
	 * @return the county associated with name
	 */
	public County getCounty(String name) {
		County countyObj = new County2();
		int county = -1;
		for (int i = 0; i < countyList.size(); i++) {
			if (this.countyList.get(i).getName().equals(name)) {
				county = i;
			}
		}
		
		if (county == -1) {} //do nothing if county is still = -1
		else {
			countyObj = this.countyList.get(county);	
		}
		return countyObj;
	}

	/**
	 * Returns the total population of the state for a given year
	 * @param year year to get the population for
	 * @return total population for the state in the year year
	 */
	public int getPopulation(int year) {
		int sum = 0;
		for (int i = 0; i < countyList.size(); i++) {
			sum += countyList.get(i).getPopulation(year);
		}
		return sum;
	}

	/**
	 * Returns a list of counties in alphabetical order by county name
	 * @return a list of counties in the state
	 */
	public List<County> getCounties() {
		CountyComparatorByName cmp = new CountyComparatorByName();
		PriorityQueue<County> pq = new PriorityQueue<>(this.countyList.size(), cmp);
		//System.out.println(countyList.size()); //TEST
		//System.out.println(pq.size()); //TEST
		for (int i = 0; i < this.countyList.size(); i++) {
			pq.add(this.countyList.get(i));
			//System.out.println(countyList.get(i).getName()); //TEST
		}
		
		List<County> list = new ArrayList<>(pq);
		
		return list;

	}

	/**
	 * Returns a list of counties in order of descending population
	 * @param year year to use to order counties by population
	 * @return list of counties in order of descending population
	 */
	public List<County> getCountiesByPopulation(int year) {
		CountyComparatorByPopulation cmp = new CountyComparatorByPopulation(year);
		List<County> list = new ArrayList<>();
		list.addAll(this.countyList);
		Collections.sort(list, cmp);
		return list;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(stateName + System.lineSeparator());
		for (int i = 0; i < countyList.size(); i++) {
			sb.append(countyList.get(i).getName());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}



}