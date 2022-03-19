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
	
	//1, 2, 5, 8, 10, 11, 12, 20 
	
//	bug 8 - medium
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
	
	
//	[Bug #1 - Easy, 1PT]
//	[Bug #5 - Medium, 2PTS]
//	@Test 
//	public void testTen() {
//		parser.addOption(new Option("hello", Type.INTEGER));
//		parser.parse("--hello=-100");
//		assertEquals(parser.getInteger("--hello"), -100);
//	}
	
	
//	[Bug #20 - Hard, 3PTS]
//	@Test
//	public void testEleven() {
//		parser.addOption(new Option("hello", Type.STRING), "o");
//		parser.parse("--hello=00000000000000000000000000000000000000000000000000        00000000000000000000000000000000       0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
//		assertEquals(parser.getString("--hello"), "00000000000000000000000000000000000000000000000000        00000000000000000000000000000000       0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
//	} 
	
//	[BUG #10]
//	@Test
//	public void testTwelve() {
//		parser.addOption(new Option("o", Type.BOOLEAN), "nothing");
////		parser.addOption(new Option("hello", Type.STRING), "o");
//		parser.parse("--o");
//		assertEquals(parser.getCharacter("o"), false);
//	}
	
	
	//[Bug #12 - Hard, 3PTS]
//	@Test 
//	public void testFourteen() {
//		parser.addOption(new Option("hello", Type.STRING), "world");
//		parser.addOption(new Option("option", Type.STRING), "hello") ;
//		parser.parse("--hello=something -hello=something");
//		parser.replace("-hello", "something", "nothing");
//		assertEquals(parser.getString("-hello"), "nothing");
//	}
	
	//bug4
//	@Test
//	public void testFifteen() {
//		parser.addOption(new Option("h", Type.STRING), "averyveryveryveryveryveryveryveryverylongshortcut");
//		assertEquals(parser.shortcutExists("averyveryveryveryveryveryveryveryverylongshortcut"), true);
//	}
//	
	
	//bug-19
	
//	@Test 
//	public void testSixteen() {
//		parser.addOption(new Option("hello", Type.STRING), "o");
//		parser.parse("--hello= \"yo-lo\"");
////		assertEquals(parser.getString("hello"), "\"yolo\"");
//	}
	
	
	//bug 7
//	@Test
//	public void testSeventeen() {
//		parser.addOption(new Option("hello", Type.STRING), "o");
//		parser.parse("--hello=1122334455");
//		assertEquals(parser.getInteger("hello"), "1122334455");
//	}
	
	
	
	
}
