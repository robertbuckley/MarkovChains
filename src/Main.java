import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) throws IOException {
		final String SHAKESPEARE = "shakespeare.txt";
		final String POKEMON = "pokemon.txt";
		final String COUNTRIES = "countries.txt";
		final String TRUMP = "trump.txt";
		final String OLDTESTAMENT = "bible.txt";
		final String CIVIL = "civil.txt";
		final String JANE = "jane.txt";

		final String SEED = "roche";
		Map<String, Map<String, Integer>> map = turnToMap(turnToOnlyLowercaseLetters(JANE), SEED.length(), 1);
		System.out.println("Seeded with: " + SEED);
		System.out.println(generateText(map, SEED, 500));
	}
	
	/**
	 * @param filename name of the file with text
	 * @return the updated string
	 */	
	public static String turnToOnlyLowercaseLetters(String filename) throws IOException{
		String line, s = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		while((line = br.readLine()) != null){
			s += line;
		}
		s = removeExtraCharacters(s.toLowerCase());
		return s;
	}
	
	/**
	 * @param s a string that you want to remove spaces, punctuation, etc. from
	 * @return the updated string
	 * Be careful for weird new types of quotes and things like that
	 */	
	public static String removeExtraCharacters(String s){
		for(int i = 0; i < s.length(); ++i){
			if(    	false
					//|| s.substring(i, i + 1).equals(" ")
					//|| s.substring(i, i + 1).equals("#")
					//|| s.substring(i, i + 1).equals("@")
					//|| s.substring(i, i + 1).equals(".")
					|| s.substring(i, i + 1).equals(",") 
					|| s.substring(i, i + 1).equals(";") 
					|| s.substring(i, i + 1).equals(":") 
					|| s.substring(i, i + 1).equals("!") 
					|| s.substring(i, i + 1).equals("-") 
					|| s.substring(i, i + 1).equals("(")
					|| s.substring(i, i + 1).equals(")")
					|| s.substring(i, i + 1).equals("\"")
					|| s.substring(i, i + 1).equals("—")
					|| s.substring(i, i + 1).equals("*")
					|| s.substring(i, i + 1).equals("'")
					|| s.substring(i, i + 1).equals("1")
					|| s.substring(i, i + 1).equals("2")
					|| s.substring(i, i + 1).equals("3")
					|| s.substring(i, i + 1).equals("4")
					|| s.substring(i, i + 1).equals("5")
					|| s.substring(i, i + 1).equals("6")
					|| s.substring(i, i + 1).equals("7")
					|| s.substring(i, i + 1).equals("8")
					|| s.substring(i, i + 1).equals("9")
					|| s.substring(i, i + 1).equals("0")
					){
				s = s.substring(0, i) + s.substring(i + 1);
				i--;
			}
		}
		return s;
	}
	
	/**
	 * @param s string to turn into a map
	 * @param lengthFirst how long first string is, must be > length second
	 * @param lengthSecond how long second string is
	 * @precondition length first + length second <= s.length()
	 * @return an map of 
	 */
	public static Map<String, Map<String, Integer>> turnToMap(String s, int lengthFirst, int lengthSecond){
		Map<String, Map<String, Integer>> map = new HashMap<String, Map<String, Integer>>();
		for(int i = 0; i <= s.length() - lengthFirst - lengthSecond; ++i){
			if(map.get(s.substring(i,i + lengthFirst)) == null){
				map.put(s.substring(i,i + lengthFirst), new HashMap<String, Integer>());
			}
			if(map.get(s.substring(i,i + lengthFirst)).get(s.substring(i + lengthFirst,i + lengthFirst + lengthSecond)) == null){
				map.get(s.substring(i,i + lengthFirst)).put(s.substring(i + lengthFirst,i + lengthFirst + lengthSecond), 1);
			}
			else{
				map.get(s.substring(i,i + lengthFirst)).put(s.substring(i + lengthFirst,i + lengthFirst + lengthSecond), map.get(s.substring(i,i + lengthFirst)).get(s.substring(i + lengthFirst,i + lengthFirst + lengthSecond)) + 1);
			}
		}
		return map;
	}
	
	/**
	 * @param map the map the training is stored in
	 * @param seed the seed to start generation with that is the length of the strings stored in the first layer of the map
	 * @param lengthOfGeneratedText how long generated text should be
	 * @return A string randomly generated from the map, seeded with seed, witha  length specified in the params
	 */
	public static String generateText(Map<String, Map<String, Integer>> map, String seed, int lengthOfGeneratedText){
		String result = seed;
		while(map.get(result.substring(result.length() - seed.length(), result.length())) != null && result.length() <= lengthOfGeneratedText ){
			String string = "";
			int total = 0;
			for(Entry e:map.get(result.substring(result.length() - seed.length(), result.length())).entrySet()){
				total += Integer.valueOf((int)(e.getValue()));
			}
			int random = (int)(Math.random() * total) + 1;
			for(Entry e:map.get(result.substring(result.length() - seed.length(), result.length())).entrySet()){
				random -= Integer.valueOf((int)(e.getValue()));
				if(random <= 0){
					string = (String)e.getKey();
					break;
				}
			}
			result+=string;
		}
		return result;
	}
}
