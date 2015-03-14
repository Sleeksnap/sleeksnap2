package org.sleeksnap.util.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Optional;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A basic multi language implementation using JSON files
 * 
 * @author Nikki
 *
 */
public class Language {

	/**
	 * The Gson Parser
	 */
	private static JsonParser parser = new JsonParser();
	
	/**
	 * Pattern to match variables with
	 */
	private static final Pattern VAR_PATTERN = Pattern.compile("\\{(\\d+)\\}");
	
	/**
	 * Tokens in the current loaded language set
	 */
	private static Map<String, String> tokens = new HashMap<>();
	
	/**
	 * Load a language after loading the default 'english' language to prevent missing phrases.
	 * @param lang
	 * 			The language name to load
	 * @throws IOException
	 * 			If an error occurs while loading
	 */
	public static void load(String lang) throws IOException {
		tokens.clear();
		
		loadAndMerge("english");

		if(!lang.equals("english")) {
			loadAndMerge(lang);
		}
	}
	
	/**
	 * Load and merge the tokens from a language
	 * @param lang
	 * 			The language to load
	 * @throws IOException
	 * 			If an error occurs, or if it's an invalid language file
	 */
	@SuppressWarnings("unchecked")
	public static void loadAndMerge(String lang) throws IOException {
		Optional<URL> url = Optional.fromNullable(Language.class.getResource("/languages/" + lang + ".json"));

		if(!url.isPresent()) {
			throw new IOException("Invalid language " + lang);
		}

		try (InputStream input = url.get().openStream()) {
			JsonObject obj = (JsonObject) parser.parse(new InputStreamReader(input));

			JsonObject tokenObj = obj.get("tokens").getAsJsonObject();

			for(Map.Entry<String, JsonElement> e : tokenObj.entrySet()) {
				tokens.put(e.getKey(), e.getValue().getAsString());
			}
		}
	}

	/**
	 * Get a language string
	 * @param token
	 * 			The language token name
	 * @param args
	 * 			The language arguments
	 * @return
	 * 			The parsed language string, or "" if null
	 */
	public static String getToken(String token, Object... args) {
		String str = tokens.get(token);
		
		if(str == null) {
			throw new IllegalArgumentException("No token with the name " + token);
		}
		
		if(str.indexOf('{') != -1) {
			Matcher m = VAR_PATTERN.matcher(str);
			
			StringBuffer out = new StringBuffer();
			while(m.find()) {
				int paramIndex = Integer.parseInt(m.group(1));
				if(args.length > paramIndex - 1) {
					m.appendReplacement(out, args[paramIndex - 1].toString());
				} else {
					throw new IndexOutOfBoundsException("Index " + paramIndex + " is out of bounds.");
				}
			}
			m.appendTail(out);
			
			str = out.toString();
		}

		return str;
	}
}