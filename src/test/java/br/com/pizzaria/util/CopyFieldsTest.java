package br.com.pizzaria.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CopyFieldsTest {
	
	@Test
	public void copyTest() {
		ExClass exClass = new ExClass();
		exClass.setIntField(10);
		exClass.setStringField("a");
		ExClass2 exClass2 = new ExClass2();
		CopyFields.copy(exClass, exClass2);
		assertThat(exClass.getIntField()).isEqualTo(exClass2.getIntField());
		assertThat(exClass.getStringField()).isEqualTo(exClass2.getStringField());
	}

}


class ExClass{
	private Integer intField;
	private String stringField;
	private Integer noGetterField;
	public Integer getIntField() {
		return intField;
	}
	public void setIntField(Integer intField) {
		this.intField = intField;
	}
	public String getStringField() {
		return stringField;
	}
	public void setStringField(String stringField) {
		this.stringField = stringField;
	}
}

class ExClass2{
	private Integer intField;
	private String stringField;
	private Integer noGetterField;
	public Integer getIntField() {
		return intField;
	}
	public void setIntField(Integer intField) {
		this.intField = intField;
	}
	public String getStringField() {
		return stringField;
	}
	public void setStringField(String stringField) {
		this.stringField = stringField;
	}
}