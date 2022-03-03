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
	
//	@Test
//	public void testOne() {
//		parser.addOption(new Option("output", Type.INTEGER), "O");
//		parser.addOption(new Option("output", Type.INTEGER), "K");
//		assertEquals(parser.optionOrShortcutExists("O"), true);
//	}
//	
//	@Test
//	public void testTwo() {
//		parser.addOption(new Option("he%llo", Type.BOOLEAN), "k");
//		assertEquals(parser.optionExists("he%llo"), true);
//	}

//	@Test
//	public void testThree() {
//		parser.addOption(new Option("Hello", Type.BOOLEAN), "k");
//		parser.addOption(new Option("k", Type.INTEGER), "f");
//		assertEquals(parser.optionOrShortcutExists("k"), true);
//	}
}
