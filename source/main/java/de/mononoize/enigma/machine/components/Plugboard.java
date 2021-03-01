package de.mononoize.enigma.machine.components;

import static de.mononoize.enigma.tools.CharTools.ALPHABET;
import static de.mononoize.enigma.tools.CharTools.toChar;
import static de.mononoize.enigma.tools.CharTools.toIndex;
import static de.mononoize.enigma.tools.MathTools.mod;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.m_name)
				.append(this.m_description)
				.append(this.m_forwardMapping)
				.append(this.m_reverseMapping)
				.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		
		if (!(object instanceof Plugboard)) {
			return false;
		}
		
		final Plugboard that = (Plugboard) object;
		
		return new EqualsBuilder()
				.append(this.m_name, that.m_name)
				.append(this.m_description, that.m_description)
				.append(this.m_forwardMapping, that.m_forwardMapping)
				.append(this.m_reverseMapping, that.m_reverseMapping)
				.isEquals();
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
	 * @param inputCharacter The input character that shall be swapped.
	 * @param outputCharacter The output character that shall be swapped.
	 * @return A reference to this {@code Plugboard}.
	 */
	public Plugboard addCable(final char inputCharacter, final char outputCharacter) {
		Validate.inclusiveBetween('A', 'Z', inputCharacter);
		Validate.inclusiveBetween('A', 'Z', outputCharacter);
		
		final int iIndex = toIndex(inputCharacter);
		final int oIndex = toIndex(outputCharacter);
		
		if (this.m_forwardMapping[iIndex] != inputCharacter) {
			throw new IllegalArgumentException(String.format("Character %s already in use.", inputCharacter));
		}
		
		if (this.m_forwardMapping[oIndex] != outputCharacter) {
			throw new IllegalArgumentException(String.format("Character %s already in use.", outputCharacter));
		}

		this.m_forwardMapping[iIndex] = outputCharacter;
		this.m_forwardMapping[oIndex] = inputCharacter;
		this.m_reverseMapping[iIndex] = outputCharacter;
		this.m_reverseMapping[oIndex] = inputCharacter;
		
		return this.self();
	}
		
	/**
	 * Adds one or more cable(s) to the {@code Plugboard} to swap characters. 
	 * 
	 * The {@code cables} are a whitespace-separated list of character pairs. For each pair the first character will be
	 * swapped with the second one.
	 * 
	 * @param cables The input and output characters that shall be swapped.
	 * @return A reference to this {@code Plugboard}.
	 */
	public Plugboard addCables(final String cables) {
		Validate.notBlank(cables);
		Validate.isTrue(cables.length() >= 2);
		
		for (final String cable : StringUtils.split(cables)) {
			Validate.isTrue(cable.length() == 2);
			this.addCable(cable.charAt(0), cable.charAt(1));
		}
		
		return this.self();
	}
	
	/**
	 * Removes a cable from the {@code Plugboard} to un-swap the given characters.
	 * 
	 * @param inputCharacter The input character that shall be swapped.
	 * @param outputCharacter The output character that shall be swapped.
	 * @return A reference to this {@code Plugboard}.
	 */
	public Plugboard removeCable(final char inputCharacter, final char outputCharacter) {
		Validate.inclusiveBetween('A', 'Z', inputCharacter);
		Validate.inclusiveBetween('A', 'Z', outputCharacter);
		
		final int iIndex = toIndex(inputCharacter);
		final int oIndex = toIndex(outputCharacter);
		
		if (this.m_forwardMapping[iIndex] != outputCharacter) {
			throw new IllegalArgumentException(String.format("Character %s not in use.", inputCharacter));
		}
		
		if (this.m_forwardMapping[oIndex] != inputCharacter) {
			throw new IllegalArgumentException(String.format("Character %s not in use.", outputCharacter));
		}
		
		this.m_forwardMapping[iIndex] = outputCharacter;
		this.m_forwardMapping[oIndex] = inputCharacter;	
		this.m_reverseMapping[iIndex] = outputCharacter;
		this.m_reverseMapping[oIndex] = inputCharacter;
		
		return this.self();
	}
		
	/**
	 * Removes one or more cable(s) from the {@code Plugboard} to un-swap characters. 
	 * second character.
	 * 
	 * The {@code cables} are a whitespace-separated list of character pairs. For each pair the first character will be
	 * un-swapped with the second one.
	 * 
	 * @param cable The input and output character that shall be swapped.
	 * @return A reference to this {@code Plugboard}.
	 */
	public Plugboard removeCables(final String cables) {
		Validate.notBlank(cables);
		Validate.isTrue(cables.length() >= 2);
		
		for (final String cable : StringUtils.split(cables)) {
			Validate.isTrue(cable.length() == 2);
			this.removeCable(cable.charAt(0), cable.charAt(1));
		}
	
		return this.self();
	}

}