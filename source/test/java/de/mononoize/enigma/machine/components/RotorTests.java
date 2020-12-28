package de.mononoize.enigma.machine.components;

import static de.mononoize.enigma.tools.CharTools.ALPHABET;
import static de.mononoize.enigma.tools.CharTools.isInRange;
import static de.mononoize.enigma.tools.CharTools.toChar;
import static de.mononoize.enigma.tools.CharTools.toIndex;
import static de.mononoize.enigma.tools.MathTools.mod;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests the {@code Rotor} component.
 * 
 * @author mononoize
 */
@TestMethodOrder(OrderAnnotation.class)
public class RotorTests {
	
	@Test
	@Order(101)
	public void testSetPosition() {
		final Rotor rotor = Rotor.getRotorI();	
		
		for(char i = 0; i < Character.MAX_VALUE; i++) {
			final char inputCharacter = i;
		
			if (isInRange(inputCharacter)) {
				assertEquals(inputCharacter, rotor.setPosition(inputCharacter).getPosition());
			} else {
				assertThrows(IllegalArgumentException.class, () -> rotor.setPosition(inputCharacter));
			}
		}
	}
	
	@Test
	@Order(102)
	public void testIncPosition() {
		final Rotor rotor = Rotor.getRotorI();

		for(char c = 1; c < Character.MAX_VALUE; c++) {
			final char character = c;
			
			assertEquals(toChar(mod(character, 26)), rotor.incPosition().getPosition());
		}
	}
	
	@Test
	@Order(103)
	public void testSetPositionRingChar() {
		final Rotor rotor = Rotor.getRotorI();

		for(char i = 0; i < Character.MAX_VALUE; i++) {
			final char inputCharacter = i;
			
			if (isInRange(inputCharacter)) {
				assertEquals(inputCharacter, rotor.setPositionRing(inputCharacter).getPostionRing());
			} else {
				assertThrows(IllegalArgumentException.class, () -> rotor.setPositionRing(inputCharacter));
			}
		}
	}
	
	@Test
	@Order(104)
	public void testSetPositionRingInt() {
		final Rotor rotor = Rotor.getRotorI();

		for(int i = -Character.MAX_VALUE; i <= Character.MAX_VALUE; i++) {
			final int inputNumber = i;
			
			if ((1 <= i) && (i <= 26)) {
				assertEquals(toChar(i - 1), rotor.setPositionRing(i).getPostionRing());
			} else {
				assertThrows(IllegalArgumentException.class, () -> rotor.setPositionRing(inputNumber));
			}
		}
	}
	
	@Test
	@Order(105)
	public void testGetForward() {
		final Rotor rotor = Rotor.getRotorI();

		for(char i = 0; i < Character.MAX_VALUE; i++) {
			final char inputCharacter = i;
			
			if (isInRange(inputCharacter)) {
				assertEquals(inputCharacter, rotor.getForward(rotor.getReverse(inputCharacter)));
			} else {
				assertThrows(IllegalArgumentException.class, () -> rotor.getForward(inputCharacter));
			}
		}
	}
	
	@Test
	@Order(106)
	public void testGetReverse() {
		final Rotor rotor = Rotor.getRotorI();

		for(char i = 0; i < Character.MAX_VALUE; i++) {
			final char inputCharacter = i;
			
			if (isInRange(inputCharacter)) {
				assertEquals(inputCharacter, rotor.getReverse(rotor.getForward(inputCharacter)));
			} else {
				assertThrows(IllegalArgumentException.class, () -> rotor.getReverse(inputCharacter));
			}
		}
	}
	
	@Test
	@Order(201)
	public void testRotorI() {
		final char[] wiringForward = "EKMFLGDQVZNTOWYHXUSPAIBRCJ".toCharArray();
		final char[] wiringReverse = "UWYGADFPVZBECKMTHXSLRINQOJ".toCharArray();
		final char[] notches = "Q".toCharArray();
		
		final Rotor rotor = Rotor.getRotorI();
		
		testWiringForward(rotor, wiringForward);
		testWiringReverse(rotor, wiringReverse);
		testWiring(rotor, wiringForward, wiringReverse);
		testNotches(rotor, notches);
	}
	
	@Test
	@Order(202)
	public void testRotorII() {
		final char[] wiringForward = "AJDKSIRUXBLHWTMCQGZNPYFVOE".toCharArray();	
		final char[] wiringReverse = "AJPCZWRLFBDKOTYUQGENHXMIVS".toCharArray();
		final char[] notches = "E".toCharArray();

		final Rotor rotor = Rotor.getRotorII();
		
		testWiringForward(rotor, wiringForward);
		testWiringReverse(rotor, wiringReverse);
		testWiring(rotor, wiringForward, wiringReverse);
		testNotches(rotor, notches);
	}
	
	@Test
	@Order(203)
	public void testRotorIII() {
		final char[] wiringForward = "BDFHJLCPRTXVZNYEIWGAKMUSQO".toCharArray();
		final char[] wiringReverse = "TAGBPCSDQEUFVNZHYIXJWLRKOM".toCharArray();
		final char[] notches = "V".toCharArray();
		
		final Rotor rotor = Rotor.getRotorIII();
		
		testWiringForward(rotor, wiringForward);
		testWiringReverse(rotor, wiringReverse);
		testWiring(rotor, wiringForward, wiringReverse);
		testNotches(rotor, notches);
	}
	
	@Test
	@Order(204)
	public void testRotorIV() {
		final char[] wiringForward = "ESOVPZJAYQUIRHXLNFTGKDCMWB".toCharArray();
		final char[] wiringReverse = "HZWVARTNLGUPXQCEJMBSKDYOIF".toCharArray();
		final char[] notches = "J".toCharArray();
		
		final Rotor rotor = Rotor.getRotorIV();
		
		testWiringForward(rotor, wiringForward);
		testWiringReverse(rotor, wiringReverse);
		testWiring(rotor, wiringForward, wiringReverse);
		testNotches(rotor, notches);
	}	
	
	@Test
	@Order(205)
	public void testRotorV() {
		final char[] wiringForward = "VZBRGITYUPSDNHLXAWMJQOFECK".toCharArray();
		final char[] wiringReverse = "QCYLXWENFTZOSMVJUDKGIARPHB".toCharArray();
		final char[] notches = "Z".toCharArray();
		
		final Rotor rotor = Rotor.getRotorV();
		
		testWiringForward(rotor, wiringForward);
		testWiringReverse(rotor, wiringReverse);
		testWiring(rotor, wiringForward, wiringReverse);
		testNotches(rotor, notches);
	}
	
	@Test
	@Order(206)
	public void testRotorVI() {
		final char[] wiringForward = "JPGVOUMFYQBENHZRDKASXLICTW".toCharArray();
		final char[] wiringReverse = "SKXQLHCNWARVGMEBJPTYFDZUIO".toCharArray();
		final char[] notches = "ZM".toCharArray();
		
		final Rotor rotor = Rotor.getRotorVI();
		
		testWiringForward(rotor, wiringForward);
		testWiringReverse(rotor, wiringReverse);
		testWiring(rotor, wiringForward, wiringReverse);
		testNotches(rotor, notches);
	}	
	
	@Test
	@Order(207)
	public void testRotorVII() {
		final char[] wiringForward = "NZJHGRCXMYSWBOUFAIVLPEKQDT".toCharArray();
		final char[] wiringReverse = "QMGYVPEDRCWTIANUXFKZOSLHJB".toCharArray();
		final char[] notches = "ZM".toCharArray();
		
		final Rotor rotor = Rotor.getRotorVII();
		
		testWiringForward(rotor, wiringForward);
		testWiringReverse(rotor, wiringReverse);
		testWiring(rotor, wiringForward, wiringReverse);
		testNotches(rotor, notches);
	}
	
	@Test
	@Order(208)
	public void testRotorVIII() {
		final char[] wiringForward = "FKQHTLXOCBJSPDZRAMEWNIUYGV".toCharArray();
		final char[] wiringReverse = "QJINSAYDVKBFRUHMCPLEWZTGXO".toCharArray();
		final char[] notches = "ZM".toCharArray();
		
		final Rotor rotor = Rotor.getRotorVIII();
		
		testWiringForward(rotor, wiringForward);
		testWiringReverse(rotor, wiringReverse);
		testWiring(rotor, wiringForward, wiringReverse);
		testNotches(rotor, notches);
	}

	@Test
	@Order(209)
	public void testRotorBeta() {
		final char[] wiringForward = "LEYJVCNIXWPBQMDRTAKZGFUHOS".toCharArray();
		final char[] wiringReverse = "RLFOBVUXHDSANGYKMPZQWEJICT".toCharArray();
		final char[] notches = "".toCharArray();
				
		final Rotor rotor = Rotor.getRotorBeta();
					
		testWiringForward(rotor, wiringForward);
		testWiringReverse(rotor, wiringReverse);
		testWiring(rotor, wiringForward, wiringReverse);
		testNotches(rotor, notches);
	}
	
	@Test
	@Order(210)
	public void testRotorGamma() {
		final char[] wiringForward = "FSOKANUERHMBTIYCWLQPZXVGJD".toCharArray();
		final char[] wiringReverse = "ELPZHAXJNYDRKFCTSIBMGWQVOU".toCharArray();
		final char[] notches = "".toCharArray();
		
		final Rotor rotor = Rotor.getRotorGamma();
		
		testWiringForward(rotor, wiringForward);
		testWiringReverse(rotor, wiringReverse);
		testWiring(rotor, wiringForward, wiringReverse);
		testNotches(rotor, notches);
	}	
	
	private static void testWiringForward(final Rotor rotor, final char[] wiringForward) {
		rotor.setPositionRing('A');
		rotor.setPosition('A');
		
		assertArrayEquals(wiringForward, rotor.getForwardMapping());
	}
	
	private static void testWiringReverse(final Rotor rotor, final char[] wiringReverse) {
		rotor.setPositionRing('A');
		rotor.setPosition('A');
		
		assertArrayEquals(wiringReverse, rotor.getReverseMapping());
	}
	
	private static void testNotches(final Rotor rotor, final char[] notches) {
		rotor.setPositionRing('A');
		rotor.setPosition('A');
		
		assertArrayEquals(notches, rotor.getNotches());
		
		for (final char r : ALPHABET) {
			rotor.setPositionRing(r);
			for (final char p : ALPHABET) {
				assertEquals(ArrayUtils.contains(notches, p), rotor.setPosition(p).isNotchPosition());		
			}
		}
	}
	
	private static void testWiring(final Rotor rotor, final char[] wiringForward, final char[] wiringReverse) {
		rotor.setPositionRing('A');
		rotor.setPosition('A');
		
		for (final char r : ALPHABET) {
			rotor.setPositionRing(r);
			for (final char p : ALPHABET) {
				rotor.setPosition(p);			
				for (final char c : ALPHABET) {
					assertEquals(getOutputCharacter(wiringForward, r, p, c), rotor.getForward(c));
					assertEquals(getOutputCharacter(wiringReverse, r, p, c), rotor.getReverse(c));
					assertEquals(c, rotor.getForward(rotor.getReverse(c)));
					assertEquals(c, rotor.getReverse(rotor.getForward(c)));		
				}			
			}
		}
	}

	private static char getOutputCharacter(final char[] wiring, final char positionRing, final char position, final char inputCharcter) {
		final int rIndex = toIndex(positionRing);
		final int pIndex = toIndex(position);
		final int cIndex = toIndex(inputCharcter);
		
		final char wiringCharacter = wiring[mod((cIndex - rIndex + pIndex), 26)];
		final int wIndex = toIndex(wiringCharacter);
		
		final char outputCharacter = toChar(mod((wIndex + rIndex - pIndex), 26));
		
		return outputCharacter;
	}
	
}
