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
	
	//1, 2, 8, 10, 11
	
////	bug 8 - medium
//	@Test
//	public void testOne() { 
//		parser.addOption(new Option("output", Type.INTEGER), "O");
//		parser.addOption(new Option("output", Type.INTEGER), "K");
//		assertEquals(parser.optionOrShortcutExists("O"), false);
//	}
//	
////	bug 11 - hard
//	@Test
//	public void testTwo() {
//		parser.addOption(new Option("he%llo", Type.BOOLEAN), "k");
//		assertEquals(parser.optionExists("he%llo"), false);
//	}
	
	
//	//[Bug #10 - Easy, 1PT]
//	@Test 
//	public void testThree() {
//		parser.addOption(new Option("output", Type.CHARACTER), "o");
//		parser.parse("--output");
//		
//		assertEquals(parser.getCharacter("output") , "");		
//	}
	
//	//[Bug #2 - Easy, 1PT]s
//	@Test 
//	public void testFour() {
//		parser.addOption(new Option("output", Type.BOOLEAN), "o");
//		parser.parse("--output false");
//		assertEquals(parser.getBoolean("output"), true);
//	}
	
	
//	[Bug #1 - Easy, 1PT]
//	[Bug #8 - Medium, 2PTS]
//
//	@Test
//	public void testFive() {
//		parser.addOption(new Option("output", Type.INTEGER));
//		parser.addOption(new Option("output", Type.STRING));
//		parser.parse("--output 1");
//		assertEquals(parser.getInteger("output"), 1);
//	}
//	
	
//	//[Bug #10 - Easy, 1PT]
//	@Test
//	public void testSix() {
//		parser.addOption(new Option("output", Type.CHARACTER), "o");
//		parser.parse("--output \0");
//		
//		assertEquals(parser.getCharacter("output") , null);		
//	}
	
	
//	Bug 1
//	@Test
//	public void testSeven() {
//		parser.addOption(new Option("output", Type.INTEGER));
//		assertEquals(parser.parse("--output nfiaograeor"), -1);
//	}
//	
	
	//[Bug #2 - Easy, 1PT] AGAIN!
//	@Test
//	public void testEight() {
//		parser.addOption(new Option("k", Type.BOOLEAN), "hello");
//		parser.parse("--k");
//		assertEquals(parser.getBoolean("k"), false);
//		
//	}
	
//	@Test - BUG ONE AGAIN
//	public void testNine() {
//		parser.addOption(new Option("hello", Type.STRING));
//		parser.parse("--hello hello --hello world --hello Aparna");
//		assertEquals(parser.getString("hello"), "hello");
//	}
	
	
	@Test 
	public void testNine() {
		parser.addOption(new Option("hello", Type.BOOLEAN), "k");
		parser.setShortcut("hello", "o");
		assertEquals(parser.parse("-k true"), 1);
//		assertEquals(parser.getBoolean("-k"), false);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
