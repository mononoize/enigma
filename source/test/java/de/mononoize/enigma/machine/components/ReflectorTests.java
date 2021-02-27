package de.mononoize.enigma.machine.components;

import static de.mononoize.enigma.tools.CharTools.ALPHABET;
import static de.mononoize.enigma.tools.CharTools.isInRange;
import static de.mononoize.enigma.tools.CharTools.toChar;
import static de.mononoize.enigma.tools.CharTools.toIndex;
import static de.mononoize.enigma.tools.MathTools.mod;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests the {@code Reflector} component.
 * 
 * @author mononoize
 */
@TestMethodOrder(OrderAnnotation.class)
public class ReflectorTests {

	@Test
	@Order(101)
	public void testGetAll() {
		final List<Reflector> expected = Arrays.asList(
				Reflector.getReflectorA(),
				Reflector.getReflectorB(),
				Reflector.getReflectorC(),
				Reflector.getReflectorBruno(),
				Reflector.getReflectorCaesar());
	
		assertIterableEquals(expected, Reflector.getReflectors());
	}
	
	@Test
	@Order(102)
	public void testSetPosition() {
		final Reflector reflector = Reflector.getReflectorA();	
		
		for(char i = 0; i < Character.MAX_VALUE; i++) {
			final char inputCharacter = i;
		
			if (isInRange(inputCharacter)) {
				assertEquals(inputCharacter, reflector.setPosition(inputCharacter).getPosition());
			} else {
				assertThrows(IllegalArgumentException.class, () -> reflector.setPosition(inputCharacter));
			}
		}
	}
	
	@Test
	@Order(103)
	public void testIncPosition() {
		final Reflector reflector = Reflector.getReflectorA();
		
		for(char c = 1; c < Character.MAX_VALUE; c++) {
			final char character = c;
			
			assertEquals(toChar(mod(character, 26)), reflector.incPosition().getPosition());
		}
	}
	
	@Test
	@Order(104)
	public void testGetForward() {
		final Reflector reflector = Reflector.getReflectorA();
		
		for(char i = 0; i < Character.MAX_VALUE; i++) {
			final char inputCharacter = i;
			
			if (isInRange(inputCharacter)) {
				assertEquals(inputCharacter, reflector.getForward(reflector.getReverse(inputCharacter)));
			} else {
				assertThrows(IllegalArgumentException.class, () -> reflector.getForward(inputCharacter));
			}
		}
	}
	
	@Test
	@Order(105)
	public void testGetReverse() {
		final Reflector reflector = Reflector.getReflectorA();
		
		for(char i = 0; i < Character.MAX_VALUE; i++) {
			final char inputCharacter = i;
			
			if (isInRange(inputCharacter)) {
				assertEquals(inputCharacter, reflector.getReverse(reflector.getForward(inputCharacter)));
			} else {
				assertThrows(IllegalArgumentException.class, () -> reflector.getReverse(inputCharacter));
			}
		}
	}
	
	@Test
	@Order(201)
	public void testReflectorA() {
		final char[] wiring = "EJMZALYXVBWFCRQUONTSPIKHGD".toCharArray();
		
		final Reflector reflector = Reflector.getReflectorA();
		
		testWiringForward(reflector, wiring);
		testWiringReverse(reflector, wiring);
		testWiring(reflector, wiring);
	}

	@Test
	@Order(202)
	public void testReflectorB() {
		final char[] wiring = "YRUHQSLDPXNGOKMIEBFZCWVJAT".toCharArray();	
		
		final Reflector reflector = Reflector.getReflectorB();
		
		testWiringForward(reflector, wiring);
		testWiringReverse(reflector, wiring);
		testWiring(reflector, wiring);
	}

	@Test
	@Order(203)
	public void testReflectorC() {
		final char[] wiring = "FVPJIAOYEDRZXWGCTKUQSBNMHL".toCharArray();
		
		final Reflector reflector = Reflector.getReflectorC();
		
		testWiringForward(reflector, wiring);
		testWiringReverse(reflector, wiring);
		testWiring(reflector, wiring);
	}
	
	@Test
	@Order(204)
	public void testReflectorBruno() {
		final char[] wiring = "ENKQAUYWJICOPBLMDXZVFTHRGS".toCharArray();
		
		final Reflector reflector = Reflector.getReflectorBruno();
		
		testWiringForward(reflector, wiring);
		testWiringReverse(reflector, wiring);
		testWiring(reflector, wiring);
	}
	
	@Test
	@Order(205)
	public void testReflectorCaesar() {
		final char[] wiring = "RDOBJNTKVEHMLFCWZAXGYIPSUQ".toCharArray();
		
		final Reflector reflector = Reflector.getReflectorCaesar();
		
		testWiringForward(reflector, wiring);
		testWiringReverse(reflector, wiring);
		testWiring(reflector, wiring);
	}
	
	private static void testWiringForward(final Reflector reflector, final char[] wiring) {
		assertArrayEquals(wiring, reflector.getForwardMapping());
	}
	
	private static void testWiringReverse(final Reflector reflector, final char[] wiring) {
		assertArrayEquals(wiring, reflector.getReverseMapping());
	}
	
	private static void testWiring(final Reflector reflector, final char[] wiring) {
		for (final char p : ALPHABET) {
			reflector.setPosition(p);			
			for (final char c : ALPHABET) {
				assertEquals(getOutputCharacter(wiring, p, c), reflector.getForward(c));
				assertEquals(getOutputCharacter(wiring, p, c), reflector.getReverse(c));
				assertEquals(c, reflector.getForward(reflector.getReverse(c)));
				assertEquals(c, reflector.getReverse(reflector.getForward(c)));		
			}			
		}
	}

	private static char getOutputCharacter(final char[] wiring, final char position, final char inputCharcter) {
		final int pIndex = toIndex(position);
		final int cIndex = toIndex(inputCharcter);
		
		final char wiringCharacter = wiring[mod((cIndex + pIndex), 26)];
		final int wIndex = toIndex(wiringCharacter);
		
		final char outputCharacter = toChar(mod((wIndex - pIndex), 26));
		
		return outputCharacter;
	}
	
}
