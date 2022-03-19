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
	
	//1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20 
	
	
	/*
	 * BUG 1 - what is it
	 * */
//	@Test
//	public void bugOne() {
//		parser.addOption(new Option("hello", Type.STRING), "o");
//		parser.parse("-o=nothing");
//		assertEquals(parser.getString("hello"), "nothing");
//	}
//	
	
	/*
	 * 
	 * BUG 2 - the parser does not parse false correctly
	 * */
//	@Test 
//	public void bugTwo() {
//		parser.addOption(new Option("output", Type.BOOLEAN), "o");
//		parser.parse("--output false");
//		assertEquals(parser.getBoolean("output"), false);
//	}
	
	/*
	 * 
	 * BUG THREE - when passing a random string to an option of type boolean,
	 * it does not parse it as 1, which is present in the specification
	 * */
//	@Test
//	public void bugThree() {
//		parser.addOption(new Option("option", Type.BOOLEAN));
//		parser.parse("--option=avaluehere");
//		assertEquals(parser.getInteger("option"), 1);
//	}
	
	/*
	 * BUG 4 - there is no limit to the length of a shortcut, but it throws a bug when a
	 * very long shortcut name is used
	 * */
//	@Test
//	public void testFifteen() {
//		parser.addOption(new Option("h", Type.STRING), "averyveryveryveryveryveryveryveryverylongshortcut");
//		assertEquals(parser.shortcutExists("averyveryveryveryveryveryveryveryverylongshortcut"), true);
//	}
	
	
	/*
	 * BUG FIVE - Negative integers are not handled correctly
	 * */
//	@Test 
//	public void testTen() {
//		parser.addOption(new Option("hello", Type.INTEGER));
//		parser.parse("--hello=-100");
//		assertEquals(parser.getInteger("--hello"), -100);
//	}

	/*
	 * BUG 6 - The equals function does not correctly check if two 
	 * options are equal
	 * */
//	@Test
//	public void testTwentyOne() {
//		Option a = new Option("world", Type.STRING);
//		Option b = new Option("hello", Type.STRING);
//		assertEquals(a.equals(b), false);
//	}
	
	/*
	 * BUG 7 - String type does not correctly store integers
	 * 
	 * */
//	@Test
//	public void testSeventeen() {
//		parser.addOption(new Option("hello", Type.STRING), "o");
//		parser.parse("--hello=1122334455");
//		assertEquals(parser.getInteger("hello"), "1122334455");
//	}
	
	
	/*
	 * BUG 8 - The shortcut is not overwritten correctly 
	 * */
//	@Test
//	public void testOne() { 
//		parser.addOption(new Option("output", Type.INTEGER), "O");
//		parser.addOption(new Option("output", Type.INTEGER), "K");
//		assertEquals(parser.optionOrShortcutExists("O"), false);
//	}
	
	
	/*
	 * BUG 9 - parsing an empty space should be successful, even if
	 * there is no result
	 * */
//	@Test
//	public void testTwenty() {
//		parser.addOption(new Option("option", Type.STRING));
//		assertEquals(parser.parse(" "), 0);
//	}
	
	
	/*
	 * BUG 10 - Sending a null character to input throws unexpected result
	 * */
//	@Test 
//	public void testThree() {
//		parser.addOption(new Option("output", Type.CHARACTER), "o");
//		parser.parse("--output=\0");
//		
//		assertEquals(parser.getCharacter("output") , "\0");		
//	}
	
	/*
	 * BUG 11 - A % in the option name is considered valid, when it should not be
	 * */
//	@Test
//	public void testTwo() {
//		parser.addOption(new Option("he%llo", Type.BOOLEAN), "k");
//		assertEquals(parser.optionExists("he%llo"), false);
//	}
	
	
	
//	Bug 1
//	
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
//	
	
	
//	[Bug #1 - Easy, 1PT]
//	[Bug #5 - Medium, 2PTS]
//	
	
	
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
//	
//	
	
	//bug-19
	
//	@Test 
//	public void testSixteen() {
//		parser.addOption(new Option("hello", Type.STRING), "o");
//		parser.parse("--hello= \"yo-lo\"");
////		assertEquals(parser.getString("hello"), "\"yolo\"");
//	}
	
	
	//bug 7
//	
	
	//
	
	
	//bug 17
//	@Test
//	public void testEighteen() {
//		parser.addOption(new Option("averyveryveryveryveryveryveryveryveryveryveryverylongoption", Type.STRING));
//		assertEquals(parser.optionExists("averyveryveryveryveryveryveryveryveryveryveryverylongoption"), true);
//	}
	
	
	//bug 1 + 13
//	@Test
//	public void testNineteen() {
//		parser.addOption(new Option("option", Type.STRING));
//		parser.parse("--option='he=llo'");
//		assertEquals(parser.getString("option"), "'he=llo'");
//	}
	
	//bug 14
//	@Test
//	public void testTwenty() {
//		parser.addOption(new Option("option", Type.STRING));
//		parser.parse("--option=\\n");
//		assertEquals(parser.getString("option"), "\\n");
//	}
	
	
	//bug 3
//	@Test
//	public void testTwenty() {
//		parser.addOption(new Option("option", Type.BOOLEAN));
//		parser.parse("--option=avaluehere");
//		assertEquals(parser.getInteger("option"), 1);
//	}
	
	//bug 9
//		
	
	//bug 1 + 7 +  15
//	@Test
//	public void testTwenty() {
//		parser.addOption(new Option("option", Type.STRING));
//		parser.parse("--option=12345632455");
//		assertEquals(parser.getInteger("option"), "12345632455");
//	}
	
	//[Bug #16 - Medium, 2PTS]
//	@Test(expected = NullPointerException.class)
//	public void testTwenty() {
//		parser.addOption(new Option("option", Type.STRING));
//		parser.getString(null);
////		assertEquals(parser.getInteger("option"), "12345632455");
//	}
	
	//bug6
//	
	
	//BUG 18? 
//	@Test
//	public void testTwentyTwo() {
//		parser.addOption(new Option("hello", Type.STRING));
//		parser.parse("--hello=world");
//		parser.replace("        hello", "world", "ST");
//	}
}
