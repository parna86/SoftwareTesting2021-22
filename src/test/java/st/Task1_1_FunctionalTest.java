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
	
	/*
	 * BUG 1 - empty shortcuts are considered valid
	 * */
//	@Test(expected = RuntimeException.class)
//	public void bugOne() {
//		parser.addOption(new Option("hello", Type.STRING),"");
// 		parser.parse("- hello");
// 		assertEquals(parser.getString(""), "hello");
//	}
//	
//	
//	/*
//	 * 
//	 * BUG 2 - the parser does not parse false correctly
//	 * */
//	@Test 
//	public void bugTwo() {
//		parser.addOption(new Option("output", Type.BOOLEAN), "o");
//		parser.parse("--output false");
//		assertEquals(parser.getBoolean("output"), false);
//	}
//	
//	/*
//	 * 
//	 * BUG THREE - when passing a random string to an option of type boolean,
//	 * it does not parse it as 1, which is present in the specification
//	 * */
//	@Test
//	public void bugThree() {
//		parser.addOption(new Option("option", Type.BOOLEAN));
//		parser.parse("--option=avaluehere");
//		assertEquals(parser.getInteger("option"), 1);
//	}
//	
//	/*
//	 * BUG 4 - there is no limit to the length of a shortcut, but it throws a bug when a
//	 * very long shortcut name is used
//	 * */
//	@Test
//	public void bugFour() {
//		parser.addOption(new Option("h", Type.STRING), "averyveryveryveryveryveryveryveryverylongshortcut");
//		assertEquals(parser.shortcutExists("averyveryveryveryveryveryveryveryverylongshortcut"), true);
//	}
//	
//	
//	/*
//	 * BUG FIVE - Negative integers are not handled correctly
//	 * */
//	@Test 
//	public void bugFive() {
//		parser.addOption(new Option("hello", Type.INTEGER));
//		parser.parse("--hello=-100");
//		assertEquals(parser.getInteger("--hello"), -100);
//	}
//
//	/*
//	 * BUG 6 - The equals function does not correctly check if two 
//	 * options are equal
//	 * */
//	@Test
//	public void bugSix() {
//		Option world = new Option("world", Type.STRING);
//		Option hello = new Option("hello", Type.STRING);
//		assertEquals(hello.equals(world), false);
//	}
//	
//	/*
//	 * BUG 7 - String type does not correctly store integers
//	 * 
//	 * */
//	@Test
//	public void bugSeven() {
//		parser.addOption(new Option("hello", Type.STRING), "o");
//		parser.parse("--hello=1122334455");
//		assertEquals(parser.getInteger("hello"), "1122334455");
//	}
//	
//	
//	/*
//	 * BUG 8 - The shortcut is not overwritten correctly 
//	 * */
//	@Test
//	public void bugEight() { 
//		parser.addOption(new Option("output", Type.INTEGER), "O");
//		parser.addOption(new Option("output", Type.INTEGER), "K");
//		assertEquals(parser.optionOrShortcutExists("O"), false);
//	}
//	
//	
//	/*
//	 * BUG 9 - parsing an empty space should be successful, even if
//	 * there is no result
//	 * */
//	@Test
//	public void bugNine() {
//		parser.addOption(new Option("option", Type.STRING));
//		assertEquals(parser.parse(" "), 0);
//	}
//	
//	
//	/*
//	 * BUG 10 - Sending a null character to input throws unexpected result
//	 * */
//	@Test 
//	public void bugTen() {
//		parser.addOption(new Option("output", Type.CHARACTER), "o");
//		parser.parse("--output=\0");
//		
//		assertEquals(parser.getCharacter("output") , "\0");		
//	}
//	
//	/*
//	 * BUG 11 - A % in the option name is considered valid, when it should not be
//	 * */
//	@Test
//	public void bugEleven() {
//		parser.addOption(new Option("he%llo", Type.BOOLEAN), "k");
//		assertEquals(parser.optionExists("he%llo"), false);
//	}
//	
//	
//	/*
//	 * BUG 12 - When an option and a shortcut have the same name, the replace 
//	 * function does not correctly replace the right value of the shortcut 
//	 * */
//	@Test 
//	public void bugTwelve() {
//		parser.addOption(new Option("hello", Type.STRING), "world");
//		parser.addOption(new Option("option", Type.STRING), "hello") ;
//		parser.parse("--hello=something -hello=something");
//		parser.replace("-hello", "something", "nothing");
//		assertEquals(parser.getString("-hello"), "nothing");
//	}
	
	
	/*
	 * BUG 13 - the equals sign is not handled correctly when used with single quotes
	 * */
	@Test
	public void bugThirteen() {
		parser.addOption(new Option("option", Type.STRING));
		parser.parse("--option '='");
		assertEquals(parser.getString("option"), "'='");
	}
	
	
	/*
	 * BUG 14 - 
	 * */
//	@Test
//	public void bugFourteen() {
//		parser.addOption(new Option("option", Type.STRING));
//		parser.parse("--option=\\n");
//		assertEquals(parser.getString("option"), "\\n");
//	}
//	
//	/*
//	 * BUG 15 - When an int passed to a string is out of range, 
//	 * parser.getInteger does not handle it correctly
//	 * */
//	@Test
//	public void bugFifteen() {
//		parser.addOption(new Option("option", Type.STRING));
//		parser.parse("--option=12345632455");
//		assertEquals(parser.getInteger("option"), "12345632455");
//	}
//	
//	/*
//	 * BUG 16 - trying to access null in getString does not throw a null pointer
//	 * exception
//	 * */
//	@Test(expected = NullPointerException.class)
//	public void bugSixteen() {
//		parser.addOption(new Option("option", Type.STRING));
//		parser.getString(null);
//	}
//	
//	
//	/*
//	 * BUG 17 - a long option name is considered invalid, but there is no
//	 * limit specified in the specs
//	 * */
//	@Test
//	public void bugSeventeen() {
//		parser.addOption(new Option("averyveryveryveryveryveryveryveryveryveryveryverylongoption", Type.STRING));
//		assertEquals(parser.optionExists("averyveryveryveryveryveryveryveryveryveryveryverylongoption"), true);
//	}
//	
//	
//	/*
//	 * BUG 18 - extra space padded after the variable list stops  
//	 * performing the replace operation 
//	 * */
//	@Test
//	public void bugEighteen() {
//		parser.addOption(new Option("hello", Type.STRING));
//		parser.parse("--hello=world");
//		parser.replace("hello      ", "world", "ST");
//		assertEquals(parser.getString("hello"), "ST");
//	}
//	
//	/**
//	 * BUG 19 - dashes are not handled correctly with double quotes
//	 * */
//	@Test 
//	public void bugNineteen() {
//		parser.addOption(new Option("hello", Type.STRING), "o");
//		parser.parse("--hello= \"yo-lo\"");
//		assertEquals(parser.getString("hello"), "\"yo-lo\"");
//	}
//	
//	/*
//	 * BUG 20 - large spaces between words as input for one option is invalid
//	 * */
//	@Test
//	public void testEleven() {
//		parser.addOption(new Option("hello", Type.STRING), "o");
//		parser.parse("--hello=there             Aparna");
//		assertEquals(parser.getString("--hello"), "there             Aparna");
//	} 
}