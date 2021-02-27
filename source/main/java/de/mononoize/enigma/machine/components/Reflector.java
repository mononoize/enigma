package de.mononoize.enigma.machine.components;

import static de.mononoize.enigma.tools.CharTools.toChar;
import static de.mononoize.enigma.tools.CharTools.toIndex;
import static de.mononoize.enigma.tools.MathTools.mod;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>A static/dynamic electrical wiring that swaps characters pairwise.</p>
 * 
 * @author Alexander Mattes
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
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.m_name)
				.append(this.m_description)
				.append(this.m_forwardMapping)
				.append(this.m_reverseMapping)
				.append(this.m_position)
				.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		
		if (!(object instanceof Reflector)) {
			return false;
		}
		
		final Reflector that = (Reflector) object;
		
		return new EqualsBuilder()
				.append(this.m_name, that.m_name)
				.append(this.m_description, that.m_description)
				.append(this.m_forwardMapping, that.m_forwardMapping)
				.append(this.m_reverseMapping, that.m_reverseMapping)
				.append(this.m_position, that.m_position)
				.isEquals();
	}
		
	/**
	 * Returns a list of all available reflectors.
	 * 
	 * @return A list of all available reflectors.
	 */
	public static List<Reflector> getReflectors() {
		return Arrays.asList(
				getReflectorA(),
				getReflectorB(),
				getReflectorC(),
				getReflectorBruno(),
				getReflectorCaesar());
	}

	/**
	 * Returns reflector 'A' used in the Enimga I.
	 *  
	 * @return Reflector 'A' used in the Enimga I.
	 */
	public static final Reflector getReflectorA() {
		return new Reflector( //
				"UKW A", //
				"Reflector 'A' used in the Enimga I.", //
				"EJMZALYXVBWFCRQUONTSPIKHGD");
	}
	
	/**
	 * Returns reflector 'B' used in the Enimga I, Enigma M3, and Enigma M4.
	 *  
	 * @return Reflector 'B' used in the Enimga I, Enigma M3, and Enigma M4.
	 */
	public static final Reflector getReflectorB() {
		return new Reflector( //
				"UKW B", //
				"Reflector 'B' used in the Enimga I, Enigma M3, and Enigma M4.", //
				"YRUHQSLDPXNGOKMIEBFZCWVJAT");
	}
	
	/**
	 * Returns reflector 'C' used in the Enimga I, Enigma M3, and Enigma M4.
	 *  
	 * @return Reflector 'C' used in the Enimga I, Enigma M3, and Enigma M4.
	 */
	public static final Reflector getReflectorC() {
		return new Reflector( //
				"UKW C", //
				"Reflector 'C' used in the Enimga I, Enigma M3, and Enigma M4.", //
				"FVPJIAOYEDRZXWGCTKUQSBNMHL");
	}
	
	/**
	 * Returns reflector 'Bruno' or 'Thin B' used in the Enigma M4.
	 *  
	 * @return Reflector 'Bruno' or 'Thin B' used in the Enigma M4.
	 */
	public static final Reflector getReflectorBruno() {
		return new Reflector( //
				"UKW Bruno", //
				"Reflector 'Bruno' or 'Thin B' used in the Enigma M4.", //
				"ENKQAUYWJICOPBLMDXZVFTHRGS");
	}
	
	/**
	 * Returns reflector 'Caesar' or 'Thin C' used in the Enigma M4.
	 *  
	 * @return Reflector 'Caesar' or 'Thin C' used in the Enigma M4.
	 */
	public static final Reflector getReflectorCaesar() {
		return new Reflector( //
				"UKW Caesar", //
				"Reflector 'Caesar' or 'Thin C' used in the Enigma M4.", //
				"RDOBJNTKVEHMLFCWZAXGYIPSUQ");
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