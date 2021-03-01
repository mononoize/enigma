package de.mononoize.enigma.tools;

import org.apache.commons.lang3.Validate;

/**
 * Helper class for {@code String}s.
 * 
 * @author mononoize
 */
public final class StringTools {

	/**
	 * Enforce non-instantiability. 
	 */
	private StringTools() {
	}
	
	public static void main(String[] args) {
		final String text = "";
		final int length = 5;
		
		System.out.println(format(text, length));
	}
	
	/**
	 * Formats the given text by arranging the characters into groups.
	 * 
	 * @param text The text to be formatted.
	 * @param length The number of characters per group.
	 * @param separator The separator character.
	 * @param padding The padding character or {@code null} if the final group shall not be padded.
	 * @return The formatted text.
	 */
	public static String format(final String text, final int length, final Character separator, final Character padding) {
		Validate.notEmpty(text);
		Validate.inclusiveBetween(0, (Integer.MAX_VALUE - 1), length);
	
		final StringBuffer result = new StringBuffer();
		final int modulo = length + 1;
		
		// Splitting
		for (final char character : text.trim().toCharArray()) {
			if ((result.length() + 1) % modulo == 0) {
				result.append(separator);
			}
			result.append(character);
		}

		// Padding
		if (padding != null) {			
			int count = (length - (result.length() % modulo));
			for (int i = 0; i < count; i++) {
				result.append(padding);
			}
		}
		
		return result.toString();
	}
	
	/**
	 * Formats the given text by grouping the characters into groups. This method uses the default separator character
	 * {@code ' '} and the default padding character {@code '-'}.
	 * 
	 * @param text The text to be formatted.
	 * @param length The number of characters per group.
	 * @return The formatted text.
	 */
	public static String format(final String text, final int length) {	
		return format(text, length, ' ', '-');
	}
	
}
