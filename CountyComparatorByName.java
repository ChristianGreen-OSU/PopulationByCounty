package osu.cse2123;
/**
 * A Comparator for County objects in ascending order of name
 * 
 * @author ChristianBarrett
 * @version 4.25.2021
 */

import java.util.Comparator;

public class CountyComparatorByName implements Comparator<County> {
	

	@Override
	public int compare(County o1, County o2) {
		int comparator = 0;
		if (o1.getName().compareTo(o2.getName()) > 0) {
			comparator = 1;
		}
		else if (o1.getName().compareTo(o2.getName()) < 0) {
			comparator = -1;
		}
		return comparator;
	}
	
}