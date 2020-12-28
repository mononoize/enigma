package de.mononoize.enigma.machine.components;

import static de.mononoize.enigma.tools.CharTools.toChar;
import static de.mononoize.enigma.tools.CharTools.toIndex;
import static de.mononoize.enigma.tools.MathTools.mod;

import org.apache.commons.lang3.Validate;

/**
 * <p>Base class for all dynamic electrical wirings.</p>
 * 
 * <p>A dynamic wiring maps each character of the Latin alphabet to exactly one other character of the Latin alphabet
 * just like normal  wiring. The only difference is that a wheel is able to rotate to modify the mapping.</p>
 * 
 * @see Reflector
 * @see Rotor
 * 
 * @author Alexander Mattes
 *
 * @param <S> The self type to enable a type-safe fluent API.
 */
public abstract class AbstractWheel<S extends AbstractWheel<?>> extends AbstractWiring<S> {
	
	/**
	 * The position.
	 */
	protected char m_position = 'A';
		
	/**
	 * Constructs a new {@code AbstractWheel}.
	 * 
	 * @param name The name to be used.
	 * @param description The description to be used.
	 * @param wiring The wiring to be used.
	 */
	protected AbstractWheel(final String name, final String description, final String wiring) {
		super(name, description, wiring);
	}

	/**
	 * Sets the position.
	 * 
	 * @param position The position to be set.
	 * @return A reference to this {@code AbstractWheel}.
	 */
	public S setPosition(final char position) {
		Validate.inclusiveBetween('A', 'Z', position);
		
		this.m_position = position;
		return this.self();
	}
	
	/**
	 * Increases the position by one. This method wraps around from 26 to 0.
	 * 
	 * @return A reference to this {@code AbstractWheel}.
	 */
	public S incPosition() {
		this.m_position = toChar(mod((toIndex(this.m_position) + 1), 26));
		return this.self();
	}
	
	/**
	 * Returns the position.
	 * 
	 * @return The position.
	 */
	public char getPosition() {
		return this.m_position;
	}
	
}
