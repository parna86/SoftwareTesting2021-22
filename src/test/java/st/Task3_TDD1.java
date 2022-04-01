package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task3_TDD1 {
	private Task3_Parser parser;
	
	@Before
	public void setup() {
		parser = new Task3_Parser();
	}
	
	
	/*
	 * Standard usage of addAll  
	 */
	@Test 
	public void standardUsageTestOne() throws Exception{
		parser.addAll("option1 option2 option3", "o1 o2 o3", "String Integer Boolean");
		assertEquals(parser.optionExists("option2"), true);
	}
	
	@Test 
	public void standardUsageTestTwo() throws Exception{
		parser.addAll("option1 option2 option3", "o1 o2 o3", "String Integer Boolean");
		assertEquals(parser.getType("option3"), Type.BOOLEAN);
	}
	
	@Test 
	public void standardUsageTestThree() throws Exception{
		parser.addAll("option1 option2 option3", "o1 o2 o3", "String Integer Boolean");
		assertEquals(parser.shortcutExists("o1"), true);
	}
	/*
	 * Extra spaces are omitted
	 */
	@Test 
	public void extraSpacesTestOne() throws Exception{
		parser.addAll("option1   option3", "o1 o2", "String Boolean");
		assertEquals(parser.optionExists("option3"), true);
	}
	
	/*
	 * More shortcuts than options
	 */
	@Test
	public void addAllTestTwo() throws Exception{
		parser.addAll("option1 option2", "o1 o2 o3 o4", "String Integer Boolean");
		assertFalse(parser.shortcutExists("o4"));
	}
	
	/*
	 * Fewer shortcuts than options
	 */
	@Test
	public void addAllTestThree() throws Exception{
		parser.addAll("option1 option2 option3", "o1 o2", "String Integer Boolean");
		assertTrue(parser.optionExists("option3"));
	}
	
	/*
	 * Fewer types than options 
	 */
	@Test
	public void addAllTestFour() throws Exception{
		parser.addAll("option1 option2 option3", "o1 o2 o3", "String Integer");
		assertEquals(parser.getType("option3"), Type.INTEGER);
	}
	
	/*
	 * More types than options
	 */
	@Test
	public void addAllTestFive() throws Exception{
		parser.addAll("option1 option2 option3", "o1 o2 o3", "String Integer Boolean Character");
		assertEquals(parser.getType("option3"), Type.BOOLEAN);
	}
	
	/*
	 * No shortcuts
	 */
	@Test
	public void addAllTestSeven() throws Exception{
		parser.addAll("option1 option2", "String Integer");
		assertTrue(parser.optionExists("option1"));
	}
	
	/*
	 * No shortcuts, fewer types than options
	 */
	@Test
	public void addAllTestEight() throws Exception{
		parser.addAll("option1 option2", "String");
		assertEquals(parser.getType("option2"), Type.STRING);
	}
	
	/*
	 * Grouped options, no shortcuts
	 */
	@Test
	public void groupedTestOne() throws Exception{
		parser.addAll("option1-3", "String");
		assertTrue(parser.optionExists("option2"));
	}
	
	/*
	 * Grouped shortcuts only, no grouped options
	 */
	@Test
	public void groupedTestTwo() throws Exception {
		parser.addAll("option1 option2 option3", "o1-3", "String Integer");
		assertTrue(parser.shortcutExists("o3"));
	}
	
	
	/*
	 * Grouped shortcuts and options
	 */
	@Test
	public void groupedTestThree() throws Exception {
		parser.addAll("option1-3", "o1-3", "String Integer");
		assertTrue(parser.optionExists("option2"));
	}
	
	/*
	 * Illegal type
	 */
	@Test(expected = Exception.class)
	public void groupedTestFour() throws Exception{
		parser.addAll("option1-3", "o1-3", "Blah blah");
	}
	
	/*
	 * Decreasing ranges
	 */
	@Test
	public void groupedTestFive() throws Exception{
		parser.addAll("option3-1", "o3-1", "String");
		parser.parse("-o1 hello");
		assertEquals(parser.getString("option1"), "hello");
	}
	
	/*
	 * Increasing shortcut range, decreasing option range
	 */
	@Test
	public void groupedTestSix() throws Exception{
		parser.addAll("option3-1", "o1-3", "String");
		parser.parse("-o3 hello");
		assertEquals(parser.getString("option1"), "hello");
	}
}
