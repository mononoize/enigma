package de.mononoize.enigma.machine.components;

import static de.mononoize.enigma.tools.CharTools.ALPHABET;
import static de.mononoize.enigma.tools.CharTools.toChar;
import static de.mononoize.enigma.tools.CharTools.toIndex;

import org.apache.commons.lang3.ArrayUtils;

/**
 * <p>Base class for all electrical wirings.</p>
 * 
 * <p>A wiring maps each character of the Latin alphabet to exactly one other character of the Latin alphabet. Usually,
 * a character is not mapped to itself. The mapping is bi-directional, i.e. the wiring can be passed in forward as well
 * as reverse direction.</p>
 * 
 * <p>For example: If a wiring maps the character {@code 'A'} to the character {@code 'E'} the forward mapping replaces
 * {@code 'A'} with {@code 'E'} and the reverse mapping replaces {@code 'E'} with {@code 'A'}.</p>
 * 
 * @see AbstractWheel
 * @see Reflector
 * @see Rotor
 * @see Plugboard
 * 
 * @author Alexander Mattes
 *
 * @param <S> The self type to enable a type-safe fluent API.
 */
public abstract class AbstractWiring<S extends AbstractWiring<?>> {
	
	/**
	 * The name.
	 */
	protected final String m_name;
	
	/**
	 * The description.
	 */
	protected final String m_description;
	
	/**
	 * The forward mapping.
	 */
	protected final char[] m_forwardMapping = new char[26];
	
	/**
	 * The reverse mapping.
	 */
	protected final char[] m_reverseMapping = new char[26];
	
	/**
	 * Constructs a new {@code AbstractWiring}.
	 * 
	 * @param name The name to be used.
	 * @param description The description to be used.
	 * @param wiring The wiring to be used.
	 */
	protected AbstractWiring(final String name, final String description, final char[] wiring) {		
		this.m_name = name;
		this.m_description = description;
				
		for (int i = 0; i < ALPHABET.length; i++) {
			this.m_forwardMapping[i] = wiring[i];
			this.m_reverseMapping[toIndex(wiring[i])] = toChar(i);			
		}		
	}
	
	/**
	 * Constructs a new {@code AbstractWiring}.
	 * 
	 * @param name The name to be used.
	 * @param description The description to be used.
	 * @param wiring The wiring to be used.
	 */
	protected AbstractWiring(final String name, final String descrition, final String wiring) {
		this(name, descrition, wiring.toCharArray());
	}

	/**
	 * Returns the forward mapping of the given input character.
	 * 
	 * @param inputCharacter The input character to be mapped.
	 * @return The forward mapping of the given input character.
	 */
	public abstract char getForward(final char inputCharacter);

	/**
	 * Returns the reverse mapping of the given input character.
	 * 
	 * @param inputCharacter The input character to be mapped.
	 * @return The reverse mapping of the given input character.
	 */
	public abstract char getReverse(final char inputCharacter);
	
	/**
	 * Returns a reference to this {@code AbstractWiring}.
	 * 
	 * @return A reference to this {@code AbstractWiring}.
	 */
	@SuppressWarnings("unchecked")
	protected final S self() {
		return (S) this;
	}

	/**
	 * Returns the name.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return this.m_name;
	}
	
	/**
	 * Returns the description.
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return this.m_description;
	}
		
	/**
	 * Returns the forward mapping.
	 * 
	 * @return The forward mapping.
	 */
	public char[] getForwardMapping() {
		return ArrayUtils.clone(this.m_forwardMapping);
	}
		
	/**
	 * Returns the reverse mapping.
	 * 
	 * @return The reverse mapping.
	 */
	public char[] getReverseMapping() {
		return ArrayUtils.clone(this.m_reverseMapping);
	}
	
}