package de.mononoize.enigma.tools;

import org.apache.commons.lang3.Validate;

/**
 * Helper class for {@code char}s.
 * 
 * @author mononoize
 */
public final class CharTools {

	/**
	 * The Latin alphabet.
	 */
	public static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	/**
	 * Enforce non-instantiability. 
	 */
	private CharTools() {
	}
	
	/**
	 * Returns the index of the given {@code character} in the Latin alphabet.
	 * 
	 * @param character The character to be used.
	 * @return The index of the given {@code character} in the Latin alphabet.
	 */
	public static int toIndex(final char character) {
		Validate.inclusiveBetween('A', 'Z', character);
		
		return (character - 'A');
	}
	
	/**
	 * Returns the character from the Latin alphabet at the given {@code index}.
	 * 
	 * @param index The index of the character to be returned.
	 * @return The character from the Latin alphabet at the given {@code index}.
	 */
	public static char toChar(final int index) {
		Validate.inclusiveBetween(0, 25, index);
		
		return (char) (index + 'A');
	}
	
	/**
	 * Checks if the given character is one of the Latin alphabet.
	 * 
	 * @param character The character to be used.
	 * @return {@code True} if the character is one of the Latin alphabet.
	 */
	public static boolean isInRange(final char character) {
		return (('A' <= character) && (character <= 'Z'));
	}

}
