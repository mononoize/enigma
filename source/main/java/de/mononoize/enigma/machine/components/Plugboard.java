package de.mononoize.enigma.machine.components;

import static de.mononoize.enigma.tools.CharTools.ALPHABET;
import static de.mononoize.enigma.tools.CharTools.toChar;
import static de.mononoize.enigma.tools.CharTools.toIndex;
import static de.mononoize.enigma.tools.MathTools.mod;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>A static electrical wiring that swaps characters pairwise.<p>
 * 
 * @author Alexander Mattes
 */
public class Plugboard extends AbstractWiring<Plugboard> {

	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger(Plugboard.class);
	
	/**
	 * Constructs a new {@code Plugboard}.
	 */
	public Plugboard() {
		super("PLB", "Plugboard", ALPHABET);
	}
	
	@Override
	public char getForward(final char inputCharacter) {
		Validate.inclusiveBetween('A', 'Z', inputCharacter);
		
		final char wiringCharacter = this.getWiringCharacter(this.m_forwardMapping, inputCharacter);
		final char outputCharacter = this.getOutputCharacter(this.m_forwardMapping, wiringCharacter);

		final String name = String.format("%-12s", this.m_name);
		LOGGER.debug("{}: {} > {} > {} (-/-)", name, inputCharacter, wiringCharacter, outputCharacter);

		return outputCharacter;
	}

	@Override
	public char getReverse(final char inputCharacter) {
		Validate.inclusiveBetween('A', 'Z', inputCharacter);
		
		final char wiringCharacter = this.getWiringCharacter(this.m_reverseMapping, inputCharacter);
		final char outputCharacter = this.getOutputCharacter(this.m_reverseMapping, wiringCharacter);
		
		final String name = String.format("%-12s", this.m_name);
		LOGGER.debug("{}: {} > {} > {} (-/-)", name, inputCharacter, wiringCharacter, outputCharacter);
		
		return outputCharacter;
	}
	
	/**
	 * Returns the wiring character for the given input character.
	 * 
	 * @param wiring The (forward/reverse) wiring to be used.
	 * @param inputCharcter The input character to be used.
	 * @return The wiring character for the given input character.
	 */
	private char getWiringCharacter(final char[] wiring, final char inputCharcter) {
		final int cIndex = toIndex(inputCharcter);
		
		return wiring[mod((cIndex), 26)];
	}
	
	/**
	 * Returns the output character for the given wiring character.
	 * 
	 * @param wiring The (forward/reverse) wiring to be used.
	 * @param wiringCharacter The wiring character to be used.
	 * @return The output character for the given wiring character.
	 */
	private char getOutputCharacter(final char[] wiring, final char wiringCharacter) {
		final int wIndex = toIndex(wiringCharacter);
		
		return toChar(mod((wIndex), 26));
	}
	
	/**
	 * Adds a cable to the {@code Plugboard} to swap the given characters.
	 * 
	 * @param inputCharacter The input character that shall be switched.
	 * @param outputCharacter The output character that shall be switched.
	 * @return A reference to this {@code Plugboard}.
	 */
	public Plugboard addCable(final char inputCharacter, final char outputCharacter) {
		Validate.inclusiveBetween('A', 'Z', inputCharacter);
		Validate.inclusiveBetween('A', 'Z', outputCharacter);
		
		this.m_forwardMapping[toIndex(inputCharacter)] = outputCharacter;
		this.m_forwardMapping[toIndex(outputCharacter)] = inputCharacter;
		this.m_reverseMapping[toIndex(inputCharacter)] = outputCharacter;
		this.m_reverseMapping[toIndex(outputCharacter)] = inputCharacter;
		
		return this.self();
	}
	
	/**
	 * Adds a cable to the {@code Plugboard} to swap the first character of the given {@code String} with the second
	 * character.
	 * 
	 * @param characters The input and output character that shall be switched.
	 * @return A reference to this {@code Plugboard}.
	 */
	public Plugboard addCable(final String characters) {
		Validate.notBlank(characters);
		Validate.isTrue(characters.length() == 2);
		
		return this.addCable(characters.charAt(0), characters.charAt(1));
	}
	
	/**
	 * Removes a cable from the {@code Plugboard} to un-swap the given characters.
	 * 
	 * @param inputCharacter The input character that shall be switched.
	 * @param outputCharacter The output character that shall be switched.
	 * @return A reference to this {@code Plugboard}.
	 */
	public Plugboard removeCable(final char inputCharacter, final char outputCharacter) {
		Validate.inclusiveBetween('A', 'Z', inputCharacter);
		Validate.inclusiveBetween('A', 'Z', outputCharacter);
		
		this.m_forwardMapping[toIndex(outputCharacter)] = inputCharacter;
		this.m_forwardMapping[toIndex(inputCharacter)] = outputCharacter;
		this.m_reverseMapping[toIndex(outputCharacter)] = inputCharacter;
		this.m_reverseMapping[toIndex(inputCharacter)] = outputCharacter;
		
		return this.self();
	}

	/**
	 * Removes a cable to the {@code Plugboard} to un-swap the first character of the given {@code String} with the 
	 * second character.
	 * 
	 * @param characters The input and output character that shall be switched.
	 * @return A reference to this {@code Plugboard}.
	 */
	public Plugboard removeCable(final String characters) {
		Validate.notBlank(characters);
		Validate.isTrue(characters.length() == 2);
		
		return this.removeCable(characters.charAt(0), characters.charAt(1));
	}
	
}