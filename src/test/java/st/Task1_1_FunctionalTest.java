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
	 * 
	 * BUG ONE - empty shortcuts are considered valid when they should not be
	 */
	@Test(expected = RuntimeException.class)
	public void bugOne() {
		parser.addOption(new Option("hello", Type.STRING), "");
 		parser.parse("- hi");
 		assertEquals(parser.getString(""), "hi");
	}
	
	
	/*
	 * 
	 * BUG TWO - the parser does not parse false correctly for options of type boolean 
	 */
	@Test 
	public void bugTwo() {
		parser.addOption(new Option("output", Type.BOOLEAN), "o");
		parser.parse("--output false");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	/*
	 * 
	 * BUG THREE - when passing a random string to an option of type boolean,
	 * it does not parse it as 1, which is present in the specification
	 */
	@Test
	public void bugThree() {
		parser.addOption(new Option("option", Type.BOOLEAN), "o");
		parser.parse("--option=avaluehere");
		assertEquals(parser.getInteger("option"), 1);
	}
	
	/*
	 * BUG FOUR - there is no limit to the length of a shortcut, but it throws a bug when a
	 * very long shortcut name is used
	 */
	@Test
	public void bugFour() {
		parser.addOption(new Option("h", Type.BOOLEAN), "averyveryveryveryveryveryveryveryveryveryveryveryverylongshortcut");
		assertEquals(parser.shortcutExists("averyveryveryveryveryveryveryveryveryveryveryveryverylongshortcut"), true);
	}
	
	
	/*
	 * BUG FIVE - Negative integers are not handled correctly - 
	 * throws ArithmeticException
	 */
	@Test
	public void bugFive() {
		parser.addOption(new Option("hello", Type.STRING), "o");
		parser.parse("--hello=-1");
		assertEquals(parser.getInteger("--hello"), -1);
	}

	/*
	 * BUG SIX - The equals function does not correctly check if two 
	 * options are equal 
	 */
	@Test
	public void bugSix() {
		Option world = new Option("world", Type.STRING);
		Option hello = new Option("hello", Type.STRING);
		assertEquals(hello.equals(world), false);
	}
	
	/*
	 * BUG SEVEN - String type does not correctly store large integers
	 * 
	 */
	@Test
	public void bugSeven() {
		parser.addOption(new Option("hello", Type.STRING), "o");
		parser.parse("--hello=1122334455");
		assertEquals(parser.getInteger("hello"), 1122334455);
	}
	
	
	/*
	 * BUG EIGHT - When two shortcuts are passed with the same option,
	 * a shortcut is overwritten. However, according to the specification,
	 * multiple shortcuts for one option can exist. 
	 */
	@Test
	public void bugEight() { 
		parser.addOption(new Option("output", Type.INTEGER), "O");
		parser.addOption(new Option("output", Type.INTEGER), "K");
		assertEquals(parser.shortcutExists("O"), parser.shortcutExists("K"));
	}
	
	
	/*
	 * BUG NINE - parsing an empty space should be successful (returns 0), even if
	 * there is no result
	 */
	@Test
	public void bugNine() {
		parser.addOption(new Option("option", Type.STRING), "o");
		assertEquals(parser.parse(" "), 0);
	}
	
	
	/*
	 * BUG TEN - Sending a null character to input throws unexpected result
	 */
	@Test 
	public void bugTen() {
		parser.addOption(new Option("output", Type.CHARACTER), "o");
		parser.parse("--output=\0");
		assertEquals(parser.getCharacter("output") , '\0');		
	}
	
	/*
	 * BUG ELEVEN - A % in the option name is considered valid, when it should not be.
	 * Throws AssertionError instead of IllegalArgumentException.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void bugEleven() {
		parser.addOption(new Option("hel%lo", Type.BOOLEAN), "k");
		assertEquals(parser.optionExists("hel%lo"), false);
	}
	
	
	/*
	 * BUG TWELVE - When an option and a shortcut have the same name, the replace 
	 * function does not correctly replace the right value of the shortcut 
	 * */
	@Test 
	public void bugTwelve() {
		parser.addOption(new Option("hello", Type.STRING), "world");
		parser.addOption(new Option("option", Type.STRING), "hello") ;
		parser.parse("--hello=something -hello=something");
		parser.replace("-hello", "something", "nothing");
		assertEquals(parser.getString("-hello"), "nothing");
	}
	
	
	/*
	 * BUG THIRTEEN - the equals sign is not handled correctly when used with single quotes
	 * */
	@Test
	public void bugThirteen() {
		parser.addOption(new Option("option", Type.STRING), "o");
		parser.parse("--option='='");
		assertEquals(parser.getString("option"), "'='"); //RIP
	}
	
	
	/*
	 * BUG FOURTEEN - if the input contains a \\n, the input is not handled properly.
	 * 
	 */
	@Test
	public void bugFourteen() {
		parser.addOption(new Option("option", Type.STRING), "o");
		parser.parse("--option=\\n");
		assertEquals(parser.getString("option"), "\\n");
	}
	
	/*
	 * BUG FIFTEEN - When an int passed to a string is out of range, a bug is thrown. 
	 * parser.getInteger does not handle it correctly and returns 0.
	 */
	@Test
	public void bugFifteen() {
		parser.addOption(new Option("option", Type.STRING), "o");
		parser.parse("--option=12345632455");
		assertEquals(parser.getInteger("option"), 0);
	}
	
	/*
	 * BUG SIXTEEN - trying to access null in getString does not throw a null pointer
	 * exception
	 */
	@Test(expected = NullPointerException.class)
	public void bugSixteen() {
		parser.addOption(new Option("option", Type.STRING), "o");
		parser.getString(null);
	}
	
	
	/*
	 * BUG SEVENTEEN - a long option name is considered invalid, but there is no
	 * limit specified in the specs
	 */
	@Test
	public void bugSeventeen() {
		parser.addOption(new Option("averyveryveryveryveryveryveryveryveryveryveryverylongoption", Type.STRING));
		assertEquals(parser.optionExists("averyveryveryveryveryveryveryveryveryveryveryverylongoption"), true);
	}
	
	
	/*
	 * BUG EIGHTEEN - extra space padded after the variable list stops  
	 * performing the replace operation 
	 */
	@Test
	public void bugEighteen() {
		parser.addOption(new Option("hello", Type.STRING), "o");
		parser.parse("--hello=world");
		parser.replace("hello      ", "world", "ST");
		assertEquals(parser.getString("hello"), "ST");
	}
	
	/* 
	 * BUG NINETEEN - dashes are not handled correctly with double quotes
	 */
	@Test 
	public void bugNineteen() {
		parser.addOption(new Option("option1", Type.STRING), "o");
		parser.parse("--option1 \"jiogirg-foghor\"");
		assertEquals(parser.getString("option1"), "jiogirg-foghor");
	}
	
	/*
	 * BUG 20 - spaces between words as input for one option is invalid
	 * though the specs say that is valid
	 */
	@Test
	public void bugTwenty() {
		parser.addOption(new Option("hello", Type.STRING), "o");
		parser.parse("--hello=there Aparna");
		assertEquals(parser.getString("--hello"), "there");
	}
}