package br.com.pizzaria.util;

import java.util.ArrayList;
import java.util.List;

public class Range<T extends Comparable<T>> {

	private T low;
	private T high;
	
	public Range(T low, T high) {
		this.low = low;
		this.high = high;
	}
	
	public boolean inRange(T obj) {
		return obj.compareTo(low) >= 0 && obj.compareTo(high) < 0;
	}
	
	public static List<Integer> intList(int low, int high) {
		Range<Integer> r = new Range<>(low, high);
		List<Integer> list = new ArrayList<Integer>();
		for(int i = low; r.inRange(i); i++) {
			list.add(i);
		}
		return list;
	}
}
