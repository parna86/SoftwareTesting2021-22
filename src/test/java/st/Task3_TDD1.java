package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task3_TDD1 {
	private Parser parser;
	
	@Before
	public void setup() {
		parser = new Parser();
	}
	
//	@Test
//	public void test() {
//		parser.addAll("option1 option2 option3 option4","o1 o2 o3 o4", "String Integer Boolean Character");
//		assertTrue(parser.optionExists("option1"));
//	}
	
	@Test
	public void test1() {
		parser.addAll("option1 option2", "o", "String Boolean Character Integer");

	}
	
//	@Test
//	public void test() {
//		parser.addAll("option1 option2 option3 option4","o1", "String Integer Boolean Character");
//	}

}
