package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task1_1_FunctionalTest {

	private Parser parser;
	
	@Before
	public void setUp() {
		parser = new Parser();
	}
	
	@Test
	public void testOne() {
		parser.addOption(new Option("output", Type.INTEGER), "o");
		assertEquals(parser.optionExists("output"), true);
	}

}
