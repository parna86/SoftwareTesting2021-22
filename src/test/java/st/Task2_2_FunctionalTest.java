package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task2_2_FunctionalTest {
private Parser parser;

	@Before
	public void setUp() {
		parser = new Parser();
		Option opString = new Option("optionString", Type.STRING);
		Option opInteger = new Option("optionInteger", Type.INTEGER);
		Option opBoolean = new Option("optionBoolean", Type.BOOLEAN);
		Option opCharacter = new Option("optionCharacter", Type.CHARACTER);
		parser.addOption(opInteger);
		parser.addOption(opString);
		parser.addOption(opBoolean);
		parser.addOption(opCharacter);
	}
	
	
	/*
	 *  testing get functions 
	 * */
	@Test
	public void getIntegerTestOne() {
		parser.parse("--optionInteger 23");
		assertEquals(parser.getInteger("optionInteger"), 23);
	}
	
	
	@Test
	public void getIntegerTestTwo() {
		parser.parse("--optionBoolean abcd");
		assertEquals(parser.getInteger("optionBoolean"), 1);
	}
	
	@Test
	public void getIntegerTestThree() {
		parser.parse("--optionInteger \0");
		assertEquals(parser.getInteger("optionInteger"), 0);
	}
	
	@Test
	public void getIntegerTestFour() {
		parser.parse("--optionInteger");
		assertEquals(parser.getInteger("optionInteger"), 0);
	}
	
	@Test
	public void getIntegerTestFive() {
		parser.parse("--optionString abcd");
		assertEquals(parser.getInteger("optionString"), 0);
	}
	
	@Test
	public void getBooleanTestOne() {
		parser.parse("--optionBoolean true");
		assertEquals(parser.getBoolean("optionBoolean"), true);
	}
	
	
	@Test
	public void getStringTestOne() {
		parser.parse("--optionString helloWorld");
		assertEquals(parser.getString("optionString"), "helloWorld");
	}
	
	
	@Test
	public void getCharacterTestOne() {
		parser.parse("--optionCharacter a");
		assertEquals(parser.getCharacter("optionCharacter"), 'a');
	}
	
	@Test 
	public void setShortcutTestOne() {
		parser.setShortcut("optionInteger", "shortcut");
		assertEquals(parser.shortcutExists("shortcut"), true);
	}
	
	@Test 
	public void setShortcutTestTwo() {
		parser.setShortcut("optionInteger", "shortcut");
		parser.setShortcut("optionInteger", "shortcutOne");
		assertEquals(parser.shortcutExists("shortcutOne"), true);
	}
	
	@Test
	public void replaceTestOne() {
		parser.parse("--optionString helloWorld");
		parser.replace("optionString", "helloWorld", "hello");
		assertEquals(parser.getString("optionString"), "hello");
	}
	
	@Test
	public void replaceTestTwo() {
		parser.parse("--optionCharacter h");
		parser.replace("optionCharacter", "h", "x");
		assertEquals(parser.getCharacter("optionCharacter"), 'x');
	}
	
	@Test
	public void replaceTestThree() {
		parser.parse("--optionInteger 12");
		parser.replace("optionInteger", "12", "21");
		assertEquals(parser.getInteger("optionInteger"), 21);
	}

	
	@Test
	public void replaceTestFour() {
		parser.parse("--optionInteger 12");
		parser.replace("optionInteger", "12", "21");
		assertFalse(parser.getInteger("optionInteger") == 18);
	}
		
	@Test
	public void replaceTestFive() {
		parser.parse("--optionBoolean true");
		parser.replace("optionBoolean", "true", "false");
		assertEquals(parser.getBoolean("optionBoolean"), false);
	}
	
	@Test
	public void replaceTestSix() {
		parser.parse("--optionBoolean true");
		parser.replace("optionBoolean", "true", "false");
		assertTrue(parser.getBoolean("optionBoolean") == false);
	}
	
	@Test
	public void replaceTestSeven() {
		parser.parse("--optionBoolean true");
		parser.replace("optionBoolean", "true", "false");
		assertFalse(parser.getBoolean("optionBoolean"));
	}
	
	
	@Test
	public void optionExistsTestOne() {
		assertEquals(parser.optionExists("optionInteger"), true);
	}
	
	@Test
	public void optionExistsTestTwo() {
		assertEquals(parser.optionExists("optionInt"), false);
	}
	
	@Test
	public void optionOrShortcutExistsTestOne() {
		assertEquals(parser.optionOrShortcutExists("optionInteger"), true);
	}
	
	@Test
	public void optionOrShortcutExistsTestTwo() {
		assertEquals(parser.optionOrShortcutExists("optionInt"), false);
	}
	
	@Test
	public void shortcutExistsTestOne() {
		assertEquals(parser.shortcutExists("optionInteger"), false);
	}
	
	@Test
	public void shortcutExistsTestTwo() {
		parser.setShortcut("optionInteger", "shortcut");
		assertEquals(parser.shortcutExists("shortcut"), true);
	}
	
	@Test
	public void shortcutExistsTestThree() {
		parser.setShortcut("optionInteger", "shortcut");
		assertEquals(parser.shortcutExists("short"), false);
	}
	
	
	
	@Test
	public void addOptionWithShortcutTestOne() {
		parser.addOption(new Option("option1", Type.STRING), "shortcut");
		assertEquals(parser.shortcutExists("shortcut"), true);
	}
	//getInteger - null, character, integer
	//getBoolean - send a true value
	//getCharacter - send a character
	// 
	
	
	@Test
	public void parseOne() {
		parser.parse("--optionInteger 1");
		assertTrue(parser.getInteger("optionInteger") == 1);
	}
	
	@Test
	public void parseTwo() {
		parser.parse("--optionString hello --optionString world");
		assertTrue(parser.getString("optionString").equals("world"));
	}
	
//	@Test 
//	public void parse
//	
	
	/*
	 * BUG 1 - empty shortcuts are considered valid 
	 * */
	@Test(expected = RuntimeException.class)
	public void bugOne() {
		parser.addOption(new Option("hello", Type.STRING), "");
 		parser.parse("- hello");
 		assertEquals(parser.getString(""), "hello");
	}
//	
//	
//	/*
//	 * 
//	 * BUG 2 - the parser does not parse false correctly
//	 * */
	@Test 
	public void bugTwo() {
		parser.addOption(new Option("output", Type.BOOLEAN), "o");
		parser.parse("--output false");
		assertEquals(parser.getBoolean("output"), false);
	}
//	
//	/*
//	 * 
//	 * BUG THREE - when passing a random string to an option of type boolean,
//	 * it does not parse it as 1, which is present in the specification
//	 * */
	@Test
	public void bugThree() {
		parser.addOption(new Option("option", Type.BOOLEAN), "o");
		parser.parse("--option=avaluehere");
		assertEquals(parser.getInteger("option"), 1);
	}
	
//	/*
//	 * BUG 4 - there is no limit to the length of a shortcut, but it throws a bug when a
//	 * very long shortcut name is used
//	 * */
	@Test
	public void bugFour() {
		parser.addOption(new Option("h", Type.BOOLEAN), "averyveryveryveryveryveryveryveryveryveryveryveryverylongshortcut");
		assertEquals(parser.shortcutExists("averyveryveryveryveryveryveryveryveryveryveryveryverylongshortcut"), true);
	}
//	
//	
//	/*
//	 * BUG FIVE - Negative integers are not handled correctly
//	 * */
	@Test
	public void bugFive() {
		parser.addOption(new Option("hello", Type.STRING), "o");
		parser.parse("--hello=-1");
		assertEquals(parser.getInteger("--hello"), -1);
	}
//
//	/*
//	 * BUG 6 - The equals function does not correctly check if two 
//	 * options are equal
//	 * */
	@Test
	public void bugSix() {
		Option world = new Option("world", Type.STRING);
		Option hello = new Option("hello", Type.STRING);
		assertEquals(hello.equals(world), false);
	}
//	
//	/*
//	 * BUG 7 - String type does not correctly store integers
//	 * 
//	 * */
	@Test
	public void bugSeven() {
		parser.addOption(new Option("hello", Type.STRING), "o");
		parser.parse("--hello=1122334455");
		assertEquals(parser.getInteger("hello"), 1122334455);
	}
//	
//	
//	/*
//	 * BUG 8 - The shortcut is not overwritten correctly 
//	 * */
	@Test
	public void bugEight() { 
		parser.addOption(new Option("output", Type.INTEGER), "O");
		parser.addOption(new Option("output", Type.INTEGER), "K");
		assertEquals(parser.shortcutExists("O"), parser.shortcutExists("K"));
	}
//	
//	
//	/*
//	 * BUG 9 - parsing an empty space should be successful, even if
//	 * there is no result
//	 * */
	@Test
	public void bugNine() {
		parser.addOption(new Option("option", Type.STRING), "o");
		assertEquals(parser.parse(" "), 0);
	}
//	
//	
//	/*
//	 * BUG 10 - Sending a null character to input throws unexpected result
//	 * */
	@Test 
	public void bugTen() {
		parser.addOption(new Option("output", Type.CHARACTER), "o");
		parser.parse("--output=\0");
		
		assertEquals(parser.getCharacter("output") , '\0');		
	}
//	
//	/*
//	 * BUG 11 - A % in the option name is considered valid, when it should not be
//	 * */
	@Test(expected = IllegalArgumentException.class)
	public void bugEleven() {
		parser.addOption(new Option("hel%lo", Type.BOOLEAN), "k");
		assertEquals(parser.optionExists("hel%lo"), false);
	}
//	
//	
//	/*
//	 * BUG 12 - When an option and a shortcut have the same name, the replace 
//	 * function does not correctly replace the right value of the shortcut 
//	 * */
	@Test 
	public void bugTwelve() {
		parser.addOption(new Option("hello", Type.STRING), "world");
		parser.addOption(new Option("option", Type.STRING), "hello") ;
		parser.parse("--hello=something -hello=something");
		parser.replace("-hello", "something", "nothing");
		assertEquals(parser.getString("-hello"), "nothing");
	}
	
	
	/*
	 * BUG 13 - the equals sign is not handled correctly when used with single quotes
	 * */
	@Test
	public void bugThirteen() {
		parser.addOption(new Option("option", Type.STRING), "o");
		parser.parse("--option='jiogirg{DASH}={DASH}foghor'");
		assertEquals(parser.getString("option"), "jiogirg-=-foghor"); //RIP
	}
	
	
	/*
	 * BUG 14 - if the input contains a \\n, the input is not handled properly
	 * */
	@Test
	public void bugFourteen() {
		parser.addOption(new Option("option", Type.STRING), "o");
		parser.parse("--option=\\n");
		assertEquals(parser.getString("option"), "\\n");
	}
	
	/*
	 * BUG 15 - When an int passed to a string is out of range, 
	 * parser.getInteger does not handle it correctly
	 * */
	@Test
	public void bugFifteen() {
		parser.addOption(new Option("option", Type.STRING), "o");
		parser.parse("--option=12345632455");
		assertEquals(parser.getInteger("option"), 0);
	}
	
	/*
	 * BUG 16 - trying to access null in getString does not throw a null pointer
	 * exception
	 * */
	@Test(expected = NullPointerException.class)
	public void bugSixteen() {
		parser.addOption(new Option("option", Type.STRING), "o");
		parser.getString(null);
	}
	
	
	/*
	 * BUG 17 - a long option name is considered invalid, but there is no
	 * limit specified in the specs
	 * */
	@Test
	public void bugSeventeen() {
		parser.addOption(new Option("averyveryveryveryveryveryveryveryveryveryveryverylongoption", Type.STRING));
		assertEquals(parser.optionExists("averyveryveryveryveryveryveryveryveryveryveryverylongoption"), true);
	}
	
	
	/*
	 * BUG 18 - extra space padded after the variable list stops  
	 * performing the replace operation 
	 * */
	@Test
	public void bugEighteen() {
		parser.addOption(new Option("hello", Type.STRING), "o");
		parser.parse("--hello=world");
		parser.replace("hello      ", "world", "ST");
		assertEquals(parser.getString("hello"), "ST");
	}
	
	/**
	 * BUG 19 - dashes are not handled correctly with double quotes
	 * */
	@Test 
	public void bugNineteen() {
		parser.addOption(new Option("option1", Type.STRING), "o");
		parser.parse("--option1 \"jiogirg-foghor\"");
		assertEquals(parser.getString("option1"), "jiogirg-foghor");
	}
	
	/*
	 * BUG 20 - large spaces between words as input for one option is invalid
	 * */
	@Test
	public void bugTwenty() {
		parser.addOption(new Option("hello", Type.STRING), "o");
		parser.parse("--hello=there Aparna");
		assertEquals(parser.getString("--hello"), "there");
	}
}
