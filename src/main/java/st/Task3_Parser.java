package st;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task3_Parser {

	private OptionMap optionMap;
	
	public Task3_Parser() {
		optionMap = new OptionMap();
	}
	
	public void addOption(Option option, String shortcut) {
		optionMap.store(option, shortcut);
	}
	
	public void addOption(Option option) {
		optionMap.store(option, "");
	}
	
	public boolean optionExists(String key) {
		return optionMap.optionExists(key);
	}
	
	public boolean shortcutExists(String key) {
		return optionMap.shortcutExists(key);
	}
	
	public boolean optionOrShortcutExists(String key) {
		return optionMap.optionOrShortcutExists(key);
	}
	
	public int getInteger(String optionName) {
		String value = getString(optionName);
		Type type = getType(optionName);
		int result;
		switch (type) {
			case STRING:
			case INTEGER:
				try {
					result = Integer.parseInt(value);
				} catch (Exception e) {
			        try {
			            new BigInteger(value);
			        } catch (Exception e1) {
			        }
			        result = 0;
			    }
				break;
			case BOOLEAN:
				result = getBoolean(optionName) ? 1 : 0;
				break;
			case CHARACTER:
				result = (int) getCharacter(optionName);
				break;
			default:
				result = 0;
		}
		return result;
	}
	
	public boolean getBoolean(String optionName) {
		String value = getString(optionName);
		return !(value.toLowerCase().equals("false") || value.equals("0") || value.equals(""));
	}
	
	public String getString(String optionName) {
		return optionMap.getValue(optionName);
	}
	
	public char getCharacter(String optionName) {
		String value = getString(optionName);
		return value.equals("") ? '\0' :  value.charAt(0);
	}
	
	public void setShortcut(String optionName, String shortcutName) {
		optionMap.setShortcut(optionName, shortcutName);
	}
	
	public void replace(String variables, String pattern, String value) {
			
		variables = variables.replaceAll("\\s+", " ");
		
		String[] varsArray = variables.split(" ");
		
		for (int i = 0; i < varsArray.length; ++i) {
			String varName = varsArray[i];
			String var = (getString(varName));
			var = var.replace(pattern, value);
			if(varName.startsWith("--")) {
				String varNameNoDash = varName.substring(2);
				if (optionMap.optionExists(varNameNoDash)) {
					optionMap.setValueWithOptionName(varNameNoDash, var);
				}
			} else if (varName.startsWith("-")) {
				String varNameNoDash = varName.substring(1);
				if (optionMap.shortcutExists(varNameNoDash)) {
					optionMap.setValueWithOptionShortcut(varNameNoDash, var);
				} 
			} else {
				if (optionMap.optionExists(varName)) {
					optionMap.setValueWithOptionName(varName, var);
				}
				if (optionMap.shortcutExists(varName)) {
					optionMap.setValueWithOptionShortcut(varName, var);
				} 
			}

		}
	}
	
	private List<CustomPair> findMatches(String text, String regex) {
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(text);
	    // Check all occurrences
	    List<CustomPair> pairs = new ArrayList<CustomPair>();
	    while (matcher.find()) {
	    	CustomPair pair = new CustomPair(matcher.start(), matcher.end());
	    	pairs.add(pair);
	    }
	    return pairs;
	}
	
	
	public int parse(String commandLineOptions) {
		if (commandLineOptions == null) {
			return -1;
		}
		int length = commandLineOptions.length();
		if (length == 0) {
			return -2;
		}	
		
		List<CustomPair> singleQuotePairs = findMatches(commandLineOptions, "(?<=\')(.*?)(?=\')");
		List<CustomPair> doubleQuote = findMatches(commandLineOptions, "(?<=\")(.*?)(?=\")");
		List<CustomPair> assignPairs = findMatches(commandLineOptions, "(?<=\\=)(.*?)(?=[\\s]|$)");
		
		
		for (CustomPair pair : singleQuotePairs) {
			String cmd = commandLineOptions.substring(pair.getX(), pair.getY());
			cmd = cmd.replaceAll("\"", "{D_QUOTE}").
					  replaceAll(" ", "{SPACE}").
					  replaceAll("-", "{DASH}").
					  replaceAll("=", "{EQUALS}");
	    	
	    	commandLineOptions = commandLineOptions.replace(commandLineOptions.substring(pair.getX(),pair.getY()), cmd);
		}
		
		for (CustomPair pair : doubleQuote) {
			String cmd = commandLineOptions.substring(pair.getX(), pair.getY());
			cmd = cmd.replaceAll("\'", "{S_QUOTE}").
					  replaceAll(" ", "{SPACE}").
					  replaceAll("-", "{DASH}").
					  replaceAll("=", "{EQUALS}");
			
	    	commandLineOptions = commandLineOptions.replace(commandLineOptions.substring(pair.getX(),pair.getY()), cmd);	
		}
		
		for (CustomPair pair : assignPairs) {
			String cmd = commandLineOptions.substring(pair.getX(), pair.getY());
			cmd = cmd.replaceAll("\"", "{D_QUOTE}").
					  replaceAll("\'", "{S_QUOTE}").
					  replaceAll("-", "{DASH}");
	    	commandLineOptions = commandLineOptions.replace(commandLineOptions.substring(pair.getX(),pair.getY()), cmd);	
		}

		commandLineOptions = commandLineOptions.replaceAll("--", "-+").replaceAll("\\s+", " ");


		String[] elements = commandLineOptions.split("-");
		
		
		for (int i = 0; i < elements.length; ++i) {
			String entry = elements[i];
			
			if(entry.isBlank()) {
				continue;
			}

			String[] entrySplit = entry.split("[\\s=]", 2);
			
			boolean isKeyOption = entry.startsWith("+");
			String key = entrySplit[0];
			key = isKeyOption ? key.substring(1) : key;
			String value = "";
			
			if(entrySplit.length > 1 && !entrySplit[1].isBlank()) {
				String valueWithNoise = entrySplit[1].trim();
				value = valueWithNoise.split(" ")[0];
			}
			
			// Explicitly convert boolean.
			if (getType(key) == Type.BOOLEAN && (value.toLowerCase().equals("false") || value.equals("0"))) {
				value = "";
			}
			
			value = value.replace("{S_QUOTE}", "\'").
						  replace("{D_QUOTE}", "\"").
						  replace("{SPACE}", " ").
						  replace("{DASH}", "-").
						  replace("{EQUALS}", "=");
			 
			
			boolean isUnescapedValueInQuotes = (value.startsWith("\'") && value.endsWith("\'")) ||
					(value.startsWith("\"") && value.endsWith("\""));
			
			value = value.length() > 1 && isUnescapedValueInQuotes ? value.substring(1, value.length() - 1) : value;
			
			if(isKeyOption) {
				optionMap.setValueWithOptionName(key, value);
			} else {
				optionMap.setValueWithOptionShortcut(key, value);
				
			}			
		}

		return 0;
		
	}
	
	public void addAll(String options, String shortcuts, String types) throws Exception {
		//removing extra spaces
		options = options.trim();
		shortcuts = shortcuts.trim();
		types = types.trim();
		
		//split space separated list of options, shortcuts and types
		String[] optionParse = options.split(" ");
		String[] shortcutParse = shortcuts.split(" ");
		String[] typesParse = types.split(" ");
		
		//new list of shortcuts that contain groups of shortcuts after ungrouping
		List<String> newShortcuts = new ArrayList<String>();
		
		int lenOption = optionParse.length; 
		int lenShortcut = shortcutParse.length;
		int lenTypes = typesParse.length;
		int j = 0;
		
		//ungrouping shortcuts if required
		while(j < lenShortcut) {
			if(shortcutParse[j].contains("-")) {
				String[] multipleOptions = shortcutParse[j].split("-");
				String firstOption = multipleOptions[0];
				int firstOptionLength = multipleOptions[0].length();
				String groupName = firstOption.substring(0,firstOptionLength-1);
				String startRange = Character.toString(firstOption.charAt(firstOptionLength-1));
				String endRange = multipleOptions[1];
				//checking edge cases here
				if(!(isNumber(endRange) || isAlphabet(endRange))
						|| (isNumber(startRange) && isAlphabet(endRange))
						|| (isNumber(endRange) && isAlphabet(endRange))
						|| Character.isUpperCase(startRange.charAt(0)) && Character.isLowerCase(endRange.charAt(0))
						|| Character.isUpperCase(endRange.charAt(0)) && Character.isLowerCase(startRange.charAt(0))
						){
						break;
					}
				else if(isNumber(startRange)) {
					int start = Integer.parseInt(startRange);
					int end = Integer.parseInt(endRange);
					if(start <= end) {
						while(start <= end) {
							newShortcuts.add(groupName + start);
							start++;
						}
					}
					else {
						while(start >= end) {
							newShortcuts.add(groupName + start);
							start--;
						}
					}
				}
				
				else { 
					if(startRange.compareTo(endRange) <= 0) {
						while(startRange.compareTo(endRange) <= 0) {
							newShortcuts.add(groupName + startRange);
							startRange = String.valueOf( (char) (startRange.charAt(0) + 1));;
						}
					}
					else {
						while(startRange.compareTo(endRange) >= 0) {
							newShortcuts.add(groupName + startRange);
							startRange = String.valueOf( (char) (startRange.charAt(0) - 1));;
						}
					}
				}
			}
			else {
				newShortcuts.add(shortcutParse[j]);
			}
			j++;
		}
		
		
		int i = 0;
		int shortcutCounter = 0;
		while(i < lenOption) {
			String currType;
			String currentOption = optionParse[i];
			if(i >= lenTypes) {
				currType = typesParse[lenTypes - 1].trim();
			}
			else {
				currType = typesParse[i].trim();
			}
			if(currentOption.contains("-")) {
				String[] multipleOptions = currentOption.split("-");
				String firstOption = multipleOptions[0];
				int firstOptionLength = multipleOptions[0].length();
				String groupName = firstOption.substring(0,firstOptionLength-1);
				
				String startRange = Character.toString(firstOption.charAt(firstOptionLength-1));
				String endRange = multipleOptions[1];
				
				//checking edge cases here
				if(!(isNumber(endRange) || isAlphabet(endRange))
					|| (isNumber(startRange) && isAlphabet(endRange))
					|| (isNumber(endRange) && isAlphabet(endRange))
					|| Character.isUpperCase(startRange.charAt(0)) && Character.isLowerCase(endRange.charAt(0))
					|| Character.isUpperCase(endRange.charAt(0)) && Character.isLowerCase(startRange.charAt(0))
					){
					i++;
					continue;
				}
				
				//if both ranges are numbers
				else if(isNumber(startRange)) {
					int start = Integer.parseInt(startRange);
					int end = Integer.parseInt(endRange);
					//increasing
					if(start <= end) {
						while(start <= end) {
							Option currOption = new Option(groupName + start, Type.NOTYPE);
							if(currType.equals("String")) {
								currOption.setType(Type.STRING);
							}
							else if(currType.equals("Integer")) {
								currOption.setType(Type.INTEGER);
							}
							else if(currType.equals("Character")) {
								currOption.setType(Type.CHARACTER);
							}
							else if(currType.equals("Boolean")) {
								currOption.setType(Type.BOOLEAN);
							}
							else {
								throw new IllegalArgumentException("Illegal type");
							}
							try {
								if(i >= newShortcuts.size()) {
									addOption(currOption);
								}
								else {
									addOption(currOption, newShortcuts.get(shortcutCounter));
									shortcutCounter++;
								}
							}
							catch(Exception e) {
								i++;
								continue;
							}
							start++; 
						}
					}
					else{
						//decreasing
						while(start >= end) {
							Option currOption = new Option(groupName + start, Type.NOTYPE);
							if(currType.equals("String")) {
								currOption.setType(Type.STRING);
							}
							else if(currType.equals("Boolean")) {
								currOption.setType(Type.BOOLEAN);
							}
							else if(currType.equals("Integer")) {
								currOption.setType(Type.INTEGER);
							}
							else if(currType.equals("Character")) {
								currOption.setType(Type.CHARACTER);
							}
							else {
								throw new IllegalArgumentException("Illegal type");
							}
							try {
								if(i >= newShortcuts.size()) {
									addOption(currOption);
								}
								else {
									addOption(currOption, newShortcuts.get(shortcutCounter));
									shortcutCounter++;
								}
							}
							catch(Exception e) {
								i++;
								continue;
							}
							start--; 
						}
					}
				}
				
				else { 
					//if both are characters
					//increasing
					if(startRange.compareTo(endRange) <= 0) {
						while(startRange.compareTo(endRange) <= 0) {
							Option currOption = new Option(groupName + startRange, Type.NOTYPE);
							if(currType.equals("String")) {
								currOption.setType(Type.STRING);
							}
							else if(currType.equals("Boolean")) {
								currOption.setType(Type.BOOLEAN);
							}

							else if(currType.equals("Integer")) {
								currOption.setType(Type.INTEGER);
							}
							else if(currType.equals("Character")) {
								currOption.setType(Type.CHARACTER);
							}
							else {
								throw new IllegalArgumentException("Illegal type");
							}
							try {
								if(i >= newShortcuts.size()) {
									addOption(currOption);
								}
								else {
									addOption(currOption, newShortcuts.get(shortcutCounter));
									shortcutCounter++;
								}
							}
							catch(Exception e) {
								i++;
								continue;
							}
							startRange = String.valueOf( (char) (startRange.charAt(0) + 1));;
						}
					}
					//decreasing
					else {
						while(startRange.compareTo(endRange) >= 0) {
							Option currOption = new Option(groupName + startRange, Type.NOTYPE);
							if(currType.equals("String")) {
								currOption.setType(Type.STRING);
							}
							else if(currType.equals("Boolean")) {
								currOption.setType(Type.BOOLEAN);
							}

							else if(currType.equals("Integer")) {
								currOption.setType(Type.INTEGER);
							}
							else if(currType.equals("Character")) {
								currOption.setType(Type.CHARACTER);
							}
							else {
								throw new IllegalArgumentException("Illegal type");
							}
							try {
								if(i >= newShortcuts.size()) {
									addOption(currOption);
								}
								else {
									addOption(currOption, newShortcuts.get(shortcutCounter));
									shortcutCounter++;
								}
							}
							catch(Exception e) {
								i++;
								continue;
							}
							startRange = String.valueOf( (char) (startRange.charAt(0) - 1));;
						}
					}
				}
			}
			else {
				//no groups of options, singletons
				Option currOption = new Option(optionParse[i].trim(), Type.NOTYPE);
				if(currType.equals("String")) {
					currOption.setType(Type.STRING);
				}
				else if(currType.equals("Boolean")) {
					currOption.setType(Type.BOOLEAN);
				}
				else if(currType.equals("Integer")) {
					currOption.setType(Type.INTEGER);
				}
				else if(currType.equals("Character")) {
					currOption.setType(Type.CHARACTER);
				}
				else {
					throw new IllegalArgumentException("Illegal type");
				}
				try {
					if(i >= newShortcuts.size()) {
						addOption(currOption);
					}
					else {
						addOption(currOption, newShortcuts.get(shortcutCounter));
						shortcutCounter++;
					}
				}
				catch(Exception e) {
					i++;
					continue;
				}
			}
			i++;
		}
		
	}
	
	public void addAll(String options, String types) throws Exception {
		//remove extra spaces
		options = options.trim();
		types = types.trim();
		
		//split space separated list of options and types
		String[] optionParse = options.split(" ");
		String[] typesParse = types.split(" ");
		
		int lenOption = optionParse.length; 
		int lenTypes = typesParse.length;
		
		int i = 0;
		while(i < lenOption) {
			String currType;
			String currentOption = optionParse[i];

			if(i >= lenTypes) {
				currType = typesParse[lenTypes - 1].trim();
			}
			else {
				currType = typesParse[i].trim();
			}
			
			if(currentOption.contains("-")) {
				String[] multipleOptions = currentOption.split("-");
				String firstOption = multipleOptions[0];
				int firstOptionLength = multipleOptions[0].length();
				String groupName = firstOption.substring(0,firstOptionLength-1);
				
				String startRange = Character.toString(firstOption.charAt(firstOptionLength-1));
				String endRange = multipleOptions[1];
				
				//checking edge cases here
				if(!(isNumber(endRange) || isAlphabet(endRange))
					|| (isNumber(startRange) && isAlphabet(endRange))
					|| (isNumber(endRange) && isAlphabet(endRange))
					|| Character.isUpperCase(startRange.charAt(0)) && Character.isLowerCase(endRange.charAt(0))
					|| Character.isUpperCase(endRange.charAt(0)) && Character.isLowerCase(startRange.charAt(0))
					){
						i++;
						continue;
				}
				//if range are numbers
				else if(isNumber(startRange)) {
					int start = Integer.parseInt(startRange);
					int end = Integer.parseInt(endRange);
					//increasing
					if(start < end) {
						while(start <= end) {
							Option currOption = new Option(groupName + start, Type.NOTYPE);
							if(currType.equals("String")) {
								currOption.setType(Type.STRING);
							}
							else if(currType.equals("Boolean")) {
								currOption.setType(Type.BOOLEAN);
							}
							else if(currType.equals("Integer")) {
								currOption.setType(Type.INTEGER);
							}
							else if(currType.equals("Character")) {
								currOption.setType(Type.CHARACTER);
							}
							else {
								throw new Exception("Illegal type");
							}
							try {
								addOption(currOption);
							}
							catch(Exception e) {
								i++;
								continue;
							}
							start++;
						}
					}
					//decreasing
					else {
						while(start >= end) {
							Option currOption = new Option(groupName + start, Type.NOTYPE);
							if(currType.equals("String")) {
								currOption.setType(Type.STRING);
							}
							else if(currType.equals("Boolean")) {
								currOption.setType(Type.BOOLEAN);
							}
							else if(currType.equals("Integer")) {
								currOption.setType(Type.INTEGER);
							}
							else if(currType.equals("Character")) {
								currOption.setType(Type.CHARACTER);
							}
							else {
								throw new Exception("Illegal type");
							}
							try {
								addOption(currOption);
							}
							catch(Exception e) {
								i++;
								continue;
							}
							start--;
						}
					}
				}
				
				//alphabets
				else { 
					if(startRange.compareTo(endRange) <= 0) {
						//increasing
						while(startRange.compareTo(endRange) <= 0) {
							Option currOption = new Option(groupName + startRange, Type.NOTYPE);
							if(currType.equals("String")) {
								currOption.setType(Type.STRING);
							}
							else if(currType.equals("Boolean")) {
								currOption.setType(Type.BOOLEAN);
							}
							else if(currType.equals("Integer")) {
								currOption.setType(Type.INTEGER);
							}
							else if(currType.equals("Character")) {
								currOption.setType(Type.CHARACTER);
							}
							else {
								throw new Exception("Illegal type");
							}
							try {
								addOption(currOption);
							}
							catch(Exception e) {
								i++;
								continue;
							}
							startRange = String.valueOf( (char) (startRange.charAt(0) + 1));;
						}
					}
					//decreasing
					else {
						while(startRange.compareTo(endRange) >= 0) {
							Option currOption = new Option(groupName + startRange, Type.NOTYPE);
							if(currType.equals("String")) {
								currOption.setType(Type.STRING);
							}
							else if(currType.equals("Boolean")) {
								currOption.setType(Type.BOOLEAN);
							}
							else if(currType.equals("Integer")) {
								currOption.setType(Type.INTEGER);
							}
							else if(currType.equals("Character")) {
								currOption.setType(Type.CHARACTER);
							}
							else {
								throw new Exception("Illegal type");
							}
							try {
								addOption(currOption);
							}
							catch(Exception e) {
								i++;
								continue;
							}
							startRange = String.valueOf( (char) (startRange.charAt(0) - 1));;
						}
					}
				}
			}
			//no groups of option, just singletons
			else {
				Option currOption = new Option(optionParse[i].trim(), Type.NOTYPE);
				if(currType.equals("String")) {
					currOption.setType(Type.STRING);
				}
				else if(currType.equals("Boolean")) {
					currOption.setType(Type.BOOLEAN);
				}
				else if(currType.equals("Integer")) {
					currOption.setType(Type.INTEGER);
				}
				else if(currType.equals("Character")) {
					currOption.setType(Type.CHARACTER);
				}
				else {
					throw new Exception("Illegal type");
				}
				try {
					addOption(currOption);
				}
				catch(Exception e) {
					i++;
					continue;
				}
			}
			i++;
		}
	}

	/**
	 * Helper method - checks if string contains only alphabets
	 * @param str
	 * @return true if only alphabets, false otherwise
	 */
	public static boolean isAlphabet(String str)
    {
        if (str == null) return false;
 
        for (int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            if (!(ch >= 'A' && ch <= 'Z') && !(ch >= 'a' && ch <= 'z')) {
                return false;
            }
        }
        return true;
    }
	
	/**
	 * Helper method - checks if string contains only numbers
	 * @param str
	 * @return true if only numbers, false otherwise
	 */
	public static boolean isNumber(String str)
    {
        if (str == null) return false;
 
        for (int i = 0; i < str.length(); i++)
        {
            char ch = str.charAt(i);
            if (!(ch >= '0' && ch <= '9')) {
                return false;
            }
        }
        return true;
    }
	
	public Type getType(String option) {
		Type type = optionMap.getType(option);
		return type;
	}
	
	@Override
	public String toString() {
		return optionMap.toString();
	}

	
	private class CustomPair {
		
		CustomPair(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
	    private int x;
	    private int y;
	    
	    public int getX() {
	    	return this.x;
	    }
	    
	    public int getY() {
	    	return this.y;
	    }
	}
}
