package de.mononoize.enigma.machine;

import static de.mononoize.enigma.tools.CharTools.isInRange;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mononoize.enigma.machine.components.Plugboard;
import de.mononoize.enigma.machine.components.Reflector;
import de.mononoize.enigma.machine.components.Rotor;

/**
 * <p>An electro-mechanical encryption device.</p>
 * 
 * <p>The encryption mechanism consists of:</p>
 * <ul>
 * <li>1 {@code Plugboard}</li> 
 * <li>3 {@code Rotors}</li>
 * <li>1 {@code Reflector}</li>
 * </ul>
 * 
 * @author mononoize
 */
public class Enigma {
	
	/**
	 * The {@code Enigma} builder.
	 */
	public static class Builder implements org.apache.commons.lang3.builder.Builder<Enigma> {
		
		private Plugboard m_plugboard = new Plugboard();
		
		private Rotor m_rotor1;
		
		private Rotor m_rotor2;
		
		private Rotor m_rotor3;

		private Reflector m_reflector;
	 
		public Builder() {
		}
		
		public Builder setRotor1(final Rotor rotor, final char ringPosition, final char position) {
			this.m_rotor1 = rotor.setPositionRing(ringPosition).setPosition(position);
			return this;
		}

		public Builder setRotor1(final Rotor rotor, final int ringPosition, final char position) {
			this.m_rotor1 = rotor.setPositionRing(ringPosition).setPosition(position);
			return this;
		}
		
		public Builder setRotor2(final Rotor rotor, final char ringPosition, final char position) {
			this.m_rotor2 = rotor.setPositionRing(ringPosition).setPosition(position);
			return this;
		}
		
		public Builder setRotor2(final Rotor rotor, final int ringPosition, final char position) {
			this.m_rotor2 = rotor.setPositionRing(ringPosition).setPosition(position);
			return this;
		}
		
		public Builder setRotor3(final Rotor rotor, final char ringPosition, final char position) {
			this.m_rotor3 = rotor.setPositionRing(ringPosition).setPosition(position);
			return this;
		}
		
		public Builder setRotor3(final Rotor rotor, final int ringPosition, final char position) {
			this.m_rotor3 = rotor.setPositionRing(ringPosition).setPosition(position);
			return this;
		}
			
		public Builder setReflector(final Reflector reflector) {
			this.m_reflector = reflector;
			return this;
		}
		
		public Builder addCable(final char inputCharacter, final char outputCharacter) {
			this.m_plugboard.addCable(inputCharacter, outputCharacter);
			return this;
		}
		
		@Override
		public Enigma build() {
			Validate.notNull(this.m_rotor1);
			Validate.notNull(this.m_rotor2);
			Validate.notNull(this.m_rotor3);
			Validate.notNull(this.m_reflector);
			
			return new Enigma(this);
		}
	}

	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger(Enigma.class);
	
	/**
	 * The plugboard.
	 */
	private Plugboard m_plugboard;
	
	/**
	 * The 1st (right) rotor.
	 */
	private Rotor m_rotor1;
	
	/**
	 * The 2nd (middle) rotor.
	 */
	private Rotor m_rotor2;
	
	/**
	 * The 3rd (left) rotor.
	 */
	private Rotor m_rotor3;
		
	/**
	 * The reflector.
	 */
	private Reflector m_reflector;

	/**
	 * Constructs a new {@code Enigma} using the given builder.
	 * 
	 * @param builder The builder to be used;
	 */
	private Enigma(final Builder builder) {
		this.m_rotor1 = builder.m_rotor1;
		this.m_rotor2 = builder.m_rotor2;
		this.m_rotor3 = builder.m_rotor3;
		this.m_reflector = builder.m_reflector;
		this.m_plugboard = builder.m_plugboard;
	}
	
	/**
	 * Encodes the given text.
	 * 
	 * @param text The text to be encoded.
	 * @return The encoded text.
	 */
	public String encode(final String text) {
		return this.process(text);
	}
		
	/**
	 * Decodes the given text.
	 * 
	 * @param text The text to be decoded.
	 * @return The decoded text.
	 */
	public String decode(final String text) {
		return this.process(text);
	}
	
	/**
	 * Encodes/decodes the given text.
	 * 
	 * @param text The text to be encoded/decoded.
	 * @return The encoded/decoded text.
	 */
	private String process(final String text) {
		final StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < text.length(); i++) {
			final char inputCharacter = text.charAt(i);
			
			// STEP 1: Skip all input characters that are not an element of the Latin alphabet.
			if (!isInRange(inputCharacter)) {
				continue;
			}
			
			LOGGER.debug("INPUT       : {}", inputCharacter);
			
			// STEP 2: Perform the stepping mechanism.
			final boolean rotor1IsNotchPosition = this.m_rotor1.isNotchPosition();
			final boolean rotor2IsNotchPosition = this.m_rotor2.isNotchPosition();
			
			this.m_rotor1.incPosition();
			
			if (rotor1IsNotchPosition) {
				this.m_rotor2.incPosition();
			}
			
			if (rotor2IsNotchPosition) {
				this.m_rotor2.incPosition();
				this.m_rotor3.incPosition();
			}
			
			// STEP 3: Perform the actual encryption/decryption. 
			final char forwardPlugboard = this.m_plugboard.getForward(inputCharacter);
			
			final char forwardRotor1 = this.m_rotor1.getForward(forwardPlugboard);
			final char forwardRotor2 = this.m_rotor2.getForward(forwardRotor1);
			final char forwardRotor3 = this.m_rotor3.getForward(forwardRotor2);
			
			final char reflector = this.m_reflector.getForward(forwardRotor3);
			
			final char reverseRotor3 = this.m_rotor3.getReverse(reflector);
			final char reverseRotor2 = this.m_rotor2.getReverse(reverseRotor3);
			final char reverseRotor1 = this.m_rotor1.getReverse(reverseRotor2);
			
			final char reversePlugboard = this.m_plugboard.getReverse(reverseRotor1);
			
			LOGGER.debug("OUTPUT      : {}", reversePlugboard);
			
			result.append(reversePlugboard);

			// STEP 4: Chop result into groups of 5 characters.
			if (((result.length() + 1) % 6 == 0) && ((i != (text.length() - 1)))) {
				result.append(" ");
			}
		}

		// STEP 5: Pad last group to 5 characters.
		final int count = (5 - (result.length() % 6));
		for (int i = 0; i < count; i++) {
			result.append("-");
		}
	
		return result.toString();
	}
	
}
