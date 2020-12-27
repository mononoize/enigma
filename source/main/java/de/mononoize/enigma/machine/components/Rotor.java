package de.mononoize.enigma.machine.components;

import static de.mononoize.enigma.tools.CharTools.toChar;
import static de.mononoize.enigma.tools.CharTools.toIndex;
import static de.mononoize.enigma.tools.MathTools.mod;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>A dynamic electrical wiring that maps characters.</p>
 * 
 * @author Alexander Mattes
 */
public class Rotor extends AbstractWheel<Rotor> {

	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger(Rotor.class);
	
	/**
	 * The notches.
	 */
	private final char[] m_notches;
	
	/**
	 * The position of the ring.
	 */
	private char m_positionRing = 'A';
	
	/**
	 * Constructs a new {@code Rotor}.
	 * 
	 * @param name The name to be set.
	 * @param description The description to be set.
	 * @param wiring The (forward) wiring to be set.
	 * @param notches The notches to be set.
	 */
	private Rotor(final String name, final String description, final String wiring, final String notches) {
		super(name, description, wiring);
		this.m_notches = notches.toCharArray();
	}
	
	/**
	 * Returns rotor 'I' used in the Enigma I, Enigma M3, and Enigma M4.
	 *  
	 * @return Rotor 'I' used in the Enigma I, Enigma M3, and Enigma M4.
	 */
	public static final Rotor getRotorI() {
		return new Rotor( //
				"ROT I", //
				"Rotor 'I' used in the Enigma I, Enigma M3, and Enigma M4", //
				"EKMFLGDQVZNTOWYHXUSPAIBRCJ", //
				"Q");
	}
	
	/**
	 * Returns rotor 'II' used in the Enigma I, Enigma M3, and Enigma M4.
	 *  
	 * @return Rotor 'II' used in the Enigma I, Enigma M3, and Enigma M4.
	 */
	public static final Rotor getRotorII() {
		return new Rotor( //
				"ROT II", //
				"Rotor 'II' used in the Enigma I, Enigma M3, and Enigma M4.", //
				"AJDKSIRUXBLHWTMCQGZNPYFVOE", //
				"E");
	}
	
	/**
	 * Returns rotor 'III' used in the Enigma I, Enigma M3, and Enigma M4.
	 *  
	 * @return Rotor 'III' used in the Enigma I, Enigma M3, and Enigma M4.
	 */
	public static final Rotor getRotorIII() {
		return new Rotor( //
				"ROT III",
				"Rotor 'III' used in the Enigma I, Enigma M3, and Enigma M4.",
				"BDFHJLCPRTXVZNYEIWGAKMUSQO",
				"V");
	}
	
	/**
	 * Returns rotor 'IV' used in the Enigma I, Enigma M3, and Enigma M4.
	 *  
	 * @return Rotor 'IV' used in the Enigma I, Enigma M3, and Enigma M4.
	 */
	public static final Rotor getRotorIV() {
		return new Rotor( //
				"ROT IV", //
				"TRotor 'IV' used in the Enigma I, Enigma M3, and Enigma M4.", //
				"ESOVPZJAYQUIRHXLNFTGKDCMWB", //
				"J");
	}
	
	/**
	 * Returns rotor 'V' used in the Enigma I, Enigma M3, and Enigma M4.
	 *  
	 * @return Rotor 'V' used in the Enigma I, Enigma M3, and Enigma M4.
	 */
	public static final Rotor getRotorV() {
		return new Rotor( //
				"ROT V", //
				"Rotor 'V' used in the Enigma I, Enigma M3, and Enigma M4.", //
				"VZBRGITYUPSDNHLXAWMJQOFECK", //
				"Z");
	}
	
	/**
	 * Returns rotor 'VI' used in the Enigma M3 and Enigma M4.
	 *  
	 * @return Rotor 'VI' used in the Enigma M3 and Enigma M4.
	 */
	public static final Rotor getRotorVI() {
		return new Rotor( //
				"ROT VI", //
				"Rotor 'VI' used in the Enigma M3, and Enigma M4.", //
				"JPGVOUMFYQBENHZRDKASXLICTW", // 
				"ZM");
	}
	
	/**
	 * Returns rotor 'VII' used in the Enigma M3 and Enigma M4.
	 *  
	 * @return Rotor 'VII' used in the Enigma M3 and Enigma M4.
	 */
	public static final Rotor getRotorVII() {
		return new Rotor( //
				"ROT VII", //
				"Rotor 'VII' used in the Enigma M3, and Enigma M4.", //
				"NZJHGRCXMYSWBOUFAIVLPEKQDT", //
				"ZM");
	}
		
	/**
	 * Returns rotor 'VIII' used in the Enigma M3 and Enigma M4.
	 *  
	 * @return Rotor 'VIII' used in the Enigma M3 and Enigma M4.
	 */
	public static final Rotor getRotorVIII() {
		return new Rotor( //
				"ROT VIII", //
				"Rotor 'VIII' used in the Enigma M3, and Enigma M4.", //
				"FKQHTLXOCBJSPDZRAMEWNIUYGV", //
				"ZM");
	}
	@Override
	public char getForward(final char inputCharacter) {
		Validate.inclusiveBetween('A', 'Z', inputCharacter);
		
		final char wiringCharacter = this.getWiringCharacter(this.m_forwardMapping, inputCharacter);
		final char outputCharacter = this.getOutputCharacter(this.m_forwardMapping, wiringCharacter);

		final String name = String.format("%-12s", this.m_name);
		LOGGER.debug("{}: {} > {} > {} ({}/{})", name, inputCharacter, wiringCharacter, outputCharacter, this.m_positionRing, this.m_position);

		return outputCharacter;
	}

	@Override
	public char getReverse(final char inputCharacter) {
		Validate.inclusiveBetween('A', 'Z', inputCharacter);
		
		final char wiringCharacter = this.getWiringCharacter(this.m_reverseMapping, inputCharacter);
		final char outputCharacter = this.getOutputCharacter(this.m_reverseMapping, wiringCharacter);
		
		final String name = String.format("%-12s", this.m_name);
		LOGGER.debug("{}: {} > {} > {} ({}/{})", name, inputCharacter, wiringCharacter, outputCharacter, this.m_positionRing, this.m_position);
		
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
		final int rIndex = toIndex(this.m_positionRing);
		final int pIndex = toIndex(this.m_position);
		final int cIndex = toIndex(inputCharcter);
		
		return wiring[mod((cIndex - rIndex + pIndex), 26)];
	}
	
	/**
	 * Returns the output character for the given wiring character.
	 * 
	 * @param wiring The (forward/reverse) wiring to be used.
	 * @param wiringCharacter The wiring character to be used.
	 * @return The output character for the given wiring character.
	 */
	private char getOutputCharacter(final char[] wiring, final char wiringCharacter) {
		final int rIndex = toIndex(this.m_positionRing);
		final int pIndex = toIndex(this.m_position);
		final int wIndex = toIndex(wiringCharacter);
		
		return toChar(mod((wIndex + rIndex - pIndex), 26));
	}

	/**
	 * Returns the notches.
	 * 
	 * @return The notches
	 */
	public char[] getNotches() {
		return ArrayUtils.clone(this.m_notches);
	}
	
	/**
	 * Returns {@code True} if the current position is a notch position.
	 * 
	 * @return {@code True} if the current position is a notch position.
	 */
	public boolean isNotchPosition() {
		return ArrayUtils.contains(this.m_notches, this.m_position);
	}
	
	/**
	 * Sets the position of the ring. The given ring position must be a value between 'A' and 'Z'.
	 * 
	 * @return A reference to this {@code Rotor}.
	 */
	public Rotor setPositionRing(final char positionRing) {
		Validate.inclusiveBetween('A', 'Z', positionRing);
		
		this.m_positionRing = positionRing;		
		return this.self();
	}
	
	/**
	 * Sets the position of the ring. The given ring position must be a value between 1 and 26.
	 * 
	 * @return A reference to this {@code Rotor}.
	 */
	public Rotor setPositionRing(final int positionRing) {
		return this.setPositionRing(toChar(positionRing - 1));
	}
	
	/**
	 * Returns the position of the ring.
	 * 
	 * @return The position of the ring.
	 */
	public char getPostionRing() {
		return this.m_positionRing;
	}
	
}