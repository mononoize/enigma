package de.mononoize.enigma.machine.components;

import static de.mononoize.enigma.tools.CharTools.toChar;
import static de.mononoize.enigma.tools.CharTools.toIndex;
import static de.mononoize.enigma.tools.MathTools.mod;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Alexander Mattes
 *
 * @param <S>
 */
public class Reflector extends AbstractWheel<Reflector> {
	
	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger(Reflector.class);
	
	/**
	 * Constructs a new {@code Reflector}.
	 * 
	 * @param name The name to be set.
	 * @param description The description to be set.
	 * @param wiring The wiring to be set.
	 */
	private Reflector(final String name, final String description, final String wiring) {
		super(name, description, wiring);
	}

	@Override
	public char getForward(final char inputCharacter) {
		Validate.inclusiveBetween('A', 'Z', inputCharacter);
		
		final char wiringCharacter = this.getWiringCharacter(this.m_forwardMapping, inputCharacter);
		final char outputCharacter = this.getOutputCharacter(this.m_forwardMapping, wiringCharacter);

		final String name = String.format("%-12s", this.m_name);
		LOGGER.debug("{}: {} > {} > {} (-/{})", name, inputCharacter, wiringCharacter, outputCharacter, this.m_position);

		return outputCharacter;
	}

	@Override
	public char getReverse(final char inputCharacter) {
		Validate.inclusiveBetween('A', 'Z', inputCharacter);
		
		final char wiringCharacter = this.getWiringCharacter(this.m_reverseMapping, inputCharacter);
		final char outputCharacter = this.getOutputCharacter(this.m_reverseMapping, wiringCharacter);
		
		final String name = String.format("%-12s", this.m_name);
		LOGGER.debug("{}: {} > {} > {} (-/{})", name, inputCharacter, wiringCharacter, outputCharacter, this.m_position);
		
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
		final int pIndex = toIndex(this.m_position);
		final int cIndex = toIndex(inputCharcter);
		
		return wiring[mod((cIndex + pIndex), 26)];
	}
	
	/**
	 * Returns the output character for the given wiring character.
	 * 
	 * @param wiring The (forward/reverse) wiring to be used.
	 * @param wiringCharacter The wiring character to be used.
	 * @return The output character for the given wiring character.
	 */
	private char getOutputCharacter(final char[] wiring, final char wiringCharacter) {
		final int pIndex = toIndex(this.m_position);
		final int wIndex = toIndex(wiringCharacter);
		
		return toChar(mod((wIndex - pIndex), 26));
	}
	
}