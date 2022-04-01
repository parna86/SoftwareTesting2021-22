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
	public void getIntegerTestSix() {
		parser.parse("--optionCharacter a");
		assertEquals(parser.getInteger("optionCharacter"), 97);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getIntegerTestSeven() {
		parser.addOption(new Option("hello", Type.NOTYPE)); 
		//above line throws IllegalArgumentException, never
		//reaches below line
		assertEquals(parser.getInteger("hello"), 0);
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
		parser.setShortcut(null, "shortcutOne");
		assertEquals(parser.shortcutExists("shortcutOne"), false);
	}
	
	@Test
	public void setShortcutTestThree() {
		parser.setShortcut("optionInteger", null);
		assertEquals(parser.shortcutExists(null), true);
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
		parser.replace("--optionBoolean", "true", "false");
		assertFalse(parser.getBoolean("optionBoolean"));
	}
	
	@Test
	public void replaceTestEight() {
		parser.addOption(new Option("newOption", Type.STRING), "shortcut");
		parser.parse("-shortcut thisisinteresting");
		parser.replace("shortcut", "this", "that");
		assertEquals(parser.getString("shortcut"), "thatisinteresting");
	}
	
	@Test(expected = RuntimeException.class)
	public void replaceTestNine() {
		parser.addOption(new Option("newOption", Type.STRING), "shortcut");
		parser.parse("-shortcut thisisinteresting");
		parser.replace("--short", "this", "that");
		assertEquals(parser.getString("shortcut"), "thisisinteresting");
	}
	
	@Test(expected = RuntimeException.class)
	public void replaceTestTen() {
		parser.addOption(new Option("newOption", Type.STRING), "shortcut");
		parser.parse("-shortcut thisisinteresting");
		parser.replace("-short", "this", "that");
		assertEquals(parser.getString("shortcut"), "thisisinteresting");
	}
	
	
	@Test
	public void optionExistsTestOne() {
		assertEquals(parser.optionExists("optionInteger"), true);
	}
	
	@Test
	public void optionExistsTestTwo() {
		assertEquals(parser.optionExists("optionInt"), false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void optionExistsTestThree() {
		parser.addOption(new Option("", Type.BOOLEAN));
		parser.optionExists("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void optionExistsTestFour() {
		parser.addOption(new Option("hello", Type.STRING), null);
		parser.shortcutExists(null);
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
	public void optionOrShortcutExistsTestThree() {
		parser.addOption(new Option("hello", Type.STRING), "h");
		assertEquals(parser.optionOrShortcutExists("h"), true);
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
	
	@Test
	public void parseThree() {
		assertEquals(parser.parse(""), -2);	
	}
	
	@Test
	public void parseFour() {
		assertEquals(parser.parse(null), -1);
	}
	
	
	
	//this test should pass - not sure why it is not passing
	//potential bug?
	@Test 
	public void createOptionTestOne() {
		Option a = new Option("hello", Type.BOOLEAN);
		a.setName("world");
		assertEquals(parser.optionExists("world"), false);
	}
	
	
	@Test
	public void getOptionTestOne() {
		parser.addOption(new Option("hello", Type.BOOLEAN)); 
		assertTrue(parser.optionExists("hello"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getOptionTestTwo() {
		parser.addOption(new Option(null, Type.BOOLEAN));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getOptionTestThree() {
		parser.addOption(new Option("", Type.BOOLEAN));
	}
	
	@Test
	public void optionEqualsTestOne() {
		Option option1 = new Option("op", Type.BOOLEAN);
		Option option2 = new Option("op", Type.BOOLEAN);
		assertTrue(option1.equals(option2));
	}
	
	@Test
	public void optionEqualsTestTwo() {
		Option option1 = new Option("op", Type.INTEGER);
		Option option2 = new Option("op", Type.BOOLEAN);
		assertFalse(option1.equals(option2));
	}
	
	@Test
	public void optionEqualsTestThree() {
		Option option1 = new Option("op1", Type.BOOLEAN);
		Option option2 = new Option("op", Type.BOOLEAN);
		assertFalse(option1.equals(option2));
	}
	
	@Test
	public void optionEqualsTestFour() {
		Option option1 = null;
		Option option2 = new Option("op", Type.BOOLEAN);
		assertFalse(option2.equals(option1));
	}
	
	@Test
	public void optionEqualsTestFive() {
		Option option1 = new Option("op1", Type.BOOLEAN);
		Option option2 = new Option(null, Type.BOOLEAN);
		assertFalse(option2.equals(option1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addOptionTestOne() {
		parser.addOption(new Option("op", Type.BOOLEAN), "%");
	}
	
	@Test
	public void toStringTestOne() {
		parser.toString();
	}
	
	@Test
	public void weirdQuotesTestOne() {
		parser.parse("--optionString \"hello\"");
		assertEquals(parser.getString("optionString"), "hello");
	}
	
	@Test
	public void weirdQuotesTestTwo() {
		parser.parse("--optionString \'hello\'");
		assertEquals(parser.getString("optionString"), "hello");
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * TESTS FROM TASK 1
	 * 
	 * 
	 * 
	 * 
	 * 
	 * BUG ONE - empty shortcuts are considered valid when they should not be
	 */
	@Test(expected = RuntimeException.class)
	public void bugOne() {
		parser.addOption(new Option("hello", Type.STRING), "");
 		parser.parse("- hello");
 		assertEquals(parser.getString(""), "hello");
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
	@Test(expected = RuntimeException.class)
	public void bugThirteen() {
		parser.addOption(new Option("option", Type.STRING), "o");
		parser.parse("--option='='");
		assertEquals(parser.getString("option"), "="); //RIP
	}
	
	
	/*
	 * BUG FOURTEEN - if the input contains a \\n, the input is not handled properly. 
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
	@Test(expected = RuntimeException.class)
	public void bugNineteen() {
		parser.addOption(new Option("option1", Type.STRING), "o");
		parser.parse("--option1=\"-\"");
		assertEquals(parser.getString("option1"), "-");
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
