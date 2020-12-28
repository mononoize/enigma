package de.mononoize.enigma.tools;

/**
 * Helper class for mathematical functions.  
 * 
 * @author mononoize
 */
public final class MathTools {

	/**
	 * Enforce non-instantiability. 
	 */
	private MathTools() {
	}

	/**
	 * Returns the modulo of the given arguments. It always returns the sign of the {@code divisor}.
	 *  
	 * @param dividend The division dividend. 
	 * @param divisor The division divisor
	 * @return The modulo of the given arguments.
	 */
	public static int mod(final int dividend, final int divisor) {
		return ((dividend % divisor + divisor) % divisor);
	}
	
	/**
	 * Returns the modulo of the given arguments. It always returns the sign of the {@code divisor}.
	 *  
	 * @param dividend The division dividend. 
	 * @param divisor The division divisor
	 * @return The modulo of the given arguments.
	 */
	public static char mod(final char dividend, final int divisor) {
		return (char) mod((int) dividend, divisor);
	}
	
}
