package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task2_3_FunctionalTest {
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
	public void optionOrShortcutExistsTestTwo() {
		assertEquals(parser.optionOrShortcutExists("optionInt"), false);
	}
	
	@Test
	public void optionOrShortcutExistsTestThree() {
		parser.addOption(new Option("hello", Type.STRING), "h");
		assertEquals(parser.optionOrShortcutExists("h"), true);
	}
	
	@Test
	public void parseThree() {
		assertEquals(parser.parse(""), -2);	
	}
	
	@Test
	public void parseFour() {
		assertEquals(parser.parse(null), -1);
	}
	
	@Test 
	public void createOptionTestOne() {
		Option a = new Option("hello", Type.BOOLEAN);
		a.setName("world");
		assertEquals(parser.optionExists("world"), false);
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
	 *
	 */
	
	
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
		parser.parse("--hello='there Aparna'");
		assertEquals(parser.getString("--hello"), "there Aparna");
	}
}
