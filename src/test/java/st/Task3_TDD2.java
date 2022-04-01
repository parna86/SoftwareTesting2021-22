package st;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Task3_TDD2 {
	private Parser_Task3 parser;
	
	@Before
	public void setup() {
		parser = new Parser_Task3();
	}
	/*
	 * Standard usage of addAll 
	 */
	@Test 
	public void addAllTestOne() throws Exception{
		parser.addAll("option1 option2 option3", "o1 o2 o3", "String Integer Boolean");
		assertEquals(parser.optionExists("option2"), true);
	}
	
	/*
	 * Too many shortcuts
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
	 * Spaces in the option list 
	 */
	@Test
	public void addAllTestSix() throws Exception{
		parser.addAll("  option1 option2", "o1 o2", "String Integer");
		assertTrue(parser.optionExists("option1"));
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
	 * No shortcuts
	 */
	@Test
	public void addAllTestEight() throws Exception{
		parser.addAll("option1 option2", "String");
		assertEquals(parser.getType("option2"), Type.STRING);
	}
	
	/*
	 * Grouped options only
	 */
	@Test
	public void groupedTestOne() throws Exception{
		parser.addAll("option1-3", "String");
		assertTrue(parser.optionExists("option2"));
	}
	
	/*
	 * Grouped shortcuts only
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
		parser.addAll("option3-1", "o3-o1", "String");
		assertTrue(parser.optionExists("option2"));
	}
	
	/*
	 * ADDITIONAL TESTS TO IMPROVE BRANCH COVERAGE
	 * 
	 * */
	
	@Test
	public void addAllNewTest1() throws Exception{
		parser.addAll("option1-3", "Character");
		assertTrue(parser.optionExists("option2"));
	}
	
	@Test
	public void addAllNewTest2() throws Exception{
		parser.addAll("option1-3", "Boolean");
		assertTrue(parser.optionExists("option2"));
	}
	
	@Test
	public void addAllNewTest3() throws Exception{
		parser.addAll("option1-3", "o3-1", "String");
		assertTrue(parser.optionExists("option3"));
	}
	
	@Test
	public void addAllNewTest4() throws Exception{
		parser.addAll("optionA-c", "o1-3", "String");
		assertFalse(parser.optionExists("optionA"));
	}
	
	@Test
	public void addAllNewTest5() throws Exception{
		parser.addAll("optionA-B", "oa-C", "String");
		assertFalse(parser.shortcutExists("oa"));
	}
	
	@Test
	public void addAllNewTest6() throws Exception{
		parser.addAll("option3-1", "Boolean");
		assertEquals(parser.getType("option2"), Type.BOOLEAN);
	}
	
	@Test
	public void addAllNewTest7() throws Exception{
		parser.addAll("option3-1", "Integer Boolean");
		assertFalse(parser.getType("option1") == Type.BOOLEAN);
	}
}


