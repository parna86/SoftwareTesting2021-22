package st;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private OptionMap optionMap;
	
	public Parser() {
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
	
	public void addAll(String options, String shortcuts, String types) {
		options = options.trim();
		shortcuts = shortcuts.trim();
		types = types.trim();
		String[] optionParse = options.split(" ");
		String[] shortcutParse = shortcuts.split(" ");
		String[] typesParse = types.split(" ");
		List<String> newShortcuts = new ArrayList<String>();
		int lenOption = optionParse.length; 
		int lenShortcut = shortcutParse.length;
		int lenTypes = typesParse.length;
		int j = 0;
		while(j < lenShortcut) {
			if(shortcutParse[j].contains("-")) {
				String[] multipleOptions = shortcutParse[j].split("-");
//				System.out.println("MultipleOptions: " + multipleOptions[0] + " ? " + multipleOptions[1]);
				String groupName = multipleOptions[0].substring(0,multipleOptions[0].length()-1);
//				System.out.println("groupname: " + groupName);
				String startRange = Character.toString(multipleOptions[0].charAt(multipleOptions[0].length()-1));
				String endRange = multipleOptions[1];
				if(!(isNumeric(endRange) || isAlpha(endRange))
					|| (isNumeric(startRange) && isAlpha(endRange))
					|| (isNumeric(endRange) && isAlpha(endRange))){
					break; //add something here
				}
				else if(isNumeric(startRange)) {
					int start = Integer.parseInt(startRange);
					int end = Integer.parseInt(endRange);
					while(start <= end) {
						newShortcuts.add(groupName + start);
						start++;
					}
				}
				
				else { 
					while(startRange.compareTo(endRange) <= 0) {
						newShortcuts.add(groupName + startRange);
						startRange = String.valueOf( (char) (startRange.charAt(0) + 1));;
					}
				}
			}//if
			else {
				newShortcuts.add(shortcutParse[j]);
			}
			j++;
		}
		System.out.println(newShortcuts);
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
				String groupName = multipleOptions[0].substring(0,multipleOptions[0].length()-1);
				String startRange = Character.toString(multipleOptions[0].charAt(multipleOptions[0].length()-1));
				String endRange = multipleOptions[1];
				if(!(isNumeric(endRange) || isAlpha(endRange))
					|| (isNumeric(startRange) && isAlpha(endRange))
					|| (isNumeric(endRange) && isAlpha(endRange))){
					i++;
					continue;
				}
				else if(isNumeric(startRange)) {
					int start = Integer.parseInt(startRange);
					int end = Integer.parseInt(endRange);
					while(start <= end) {
//						System.out.println(groupName + start);
//						System.out.println("Here");
						Option currOption = new Option(groupName + start, Type.BOOLEAN);
						if(currType.equals("String")) {
							currOption.setType(Type.STRING);
						}
						else if(currType.equals("Integer")) {
							currOption.setType(Type.INTEGER);
						}
						else if(currType.equals("Character")) {
							currOption.setType(Type.CHARACTER);
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
				
				else { 
					while(startRange.compareTo(endRange) <= 0) {
						Option currOption = new Option(groupName + startRange, Type.BOOLEAN);
						if(currType.equals("String")) {
							currOption.setType(Type.STRING);
						}
						else if(currType.equals("Integer")) {
							currOption.setType(Type.INTEGER);
						}
						else if(currType.equals("Character")) {
							currOption.setType(Type.CHARACTER);
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
			}
			else {
				Option currOption = new Option(optionParse[i].trim(), Type.BOOLEAN);
				if(currType.equals("String")) {
					currOption.setType(Type.STRING);
				}
				else if(currType.equals("Integer")) {
					currOption.setType(Type.INTEGER);
				}
				else if(currType.equals("Character")) {
					currOption.setType(Type.CHARACTER);
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
		}//for
		
	}
	
	public void addAll(String options, String types) {
		options = options.trim();
		types = types.trim();
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
				String groupName = multipleOptions[0].substring(0,multipleOptions[0].length()-1);
				String startRange = Character.toString(multipleOptions[0].charAt(multipleOptions[0].length()-1));
				String endRange = multipleOptions[1];
				if(!(isNumeric(endRange) || isAlpha(endRange))
					|| (isNumeric(startRange) && isAlpha(endRange))
					|| (isNumeric(endRange) && isAlpha(endRange))){
					i++;
					continue;
				}
				else if(isNumeric(startRange)) {
					int start = Integer.parseInt(startRange);
					int end = Integer.parseInt(endRange);
					while(start <= end) {
						Option currOption = new Option(groupName + start, Type.BOOLEAN);
						if(currType.equals("String")) {
							currOption.setType(Type.STRING);
						}
						else if(currType.equals("Integer")) {
							currOption.setType(Type.INTEGER);
						}
						else if(currType.equals("Character")) {
							currOption.setType(Type.CHARACTER);
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
				
				else { 
					while(startRange.compareTo(endRange) <= 0) {
						Option currOption = new Option(groupName + startRange, Type.BOOLEAN);
						if(currType.equals("String")) {
							currOption.setType(Type.STRING);
						}
						else if(currType.equals("Integer")) {
							currOption.setType(Type.INTEGER);
						}
						else if(currType.equals("Character")) {
							currOption.setType(Type.CHARACTER);
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
			}
			else {
				Option currOption = new Option(optionParse[i].trim(), Type.BOOLEAN);
				if(currType.equals("String")) {
					currOption.setType(Type.STRING);
				}
				else if(currType.equals("Integer")) {
					currOption.setType(Type.INTEGER);
				}
				else if(currType.equals("Character")) {
					currOption.setType(Type.CHARACTER);
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
		}//for
	}

	
	//copied from somewhere please change up 
	public static boolean isAlpha(String s)
    {
        if (s == null) {
            return false;
        }
 
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
                return false;
            }
        }
        return true;
    }
	
	public static boolean isNumeric(String s)
    {
        if (s == null) {
            return false;
        }
 
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (!(c >= '0' && c <= '9')) {
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
