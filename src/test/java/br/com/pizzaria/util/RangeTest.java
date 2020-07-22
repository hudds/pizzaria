package br.com.pizzaria.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RangeTest {
	
	@Test
	public void inRangeTest() {
		Range<Integer> range = new Range<Integer>(0, 10);
		int low = 0;
		int high = 10;
		for(int i = low; i < high; i++) {
			assertThat(range.inRange(i)).isTrue();
		}
		assertThat(range.inRange(low-1)).isFalse();
		assertThat(range.inRange(high)).isFalse();
	}
	
	@Test
	public void intListTest() {
		int low = 0;
		int high = 10;
		Range<Integer> range = new Range<Integer>(low, high);
		List<Integer> intList = Range.intList(low, high);
		intList.forEach(i -> assertThat(range.inRange(i)).isTrue());
	}
	
}
