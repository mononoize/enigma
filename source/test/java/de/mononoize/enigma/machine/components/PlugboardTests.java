package de.mononoize.enigma.machine.components;

import static de.mononoize.enigma.tools.CharTools.isInRange;
import static de.mononoize.enigma.tools.CharTools.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests the {@code Plugboard} component.
 * 
 * @author mononoize
 */
@TestMethodOrder(OrderAnnotation.class)
public class PlugboardTests {

	private static final Random RANDOM = new Random();
	
	@Test
	@Order(101)
	public void testAddRemoveCable() {
		final Plugboard plugboard = new Plugboard(); 
				
		for(char i = 0; i < Character.MAX_VALUE; i++) {	
			final char inputCharacter = (char) RANDOM.nextInt(Character.MAX_VALUE - 1);
			final char outputCharacter = (char) RANDOM.nextInt(Character.MAX_VALUE - 1);
		
			final String cable = valueOf(inputCharacter, outputCharacter);
			
			if (isInRange(inputCharacter) && isInRange(outputCharacter)) {
				assertEquals(plugboard, plugboard.addCable(inputCharacter, outputCharacter));
				assertEquals(plugboard, plugboard.removeCable(inputCharacter, outputCharacter));
				
				assertEquals(plugboard, plugboard.addCables(cable));
				assertEquals(plugboard, plugboard.removeCables(cable));
			} else {
				assertThrows(IllegalArgumentException.class, () -> plugboard.addCable(inputCharacter, outputCharacter));
				assertThrows(IllegalArgumentException.class, () -> plugboard.removeCable(inputCharacter, outputCharacter));
				
				assertThrows(IllegalArgumentException.class, () -> plugboard.addCables(cable));
				assertThrows(IllegalArgumentException.class, () -> plugboard.removeCables(cable));
			}
		}
	}
	
	@Test
	@Order(102)
	public void testAddRemoveCables() {
		final Plugboard plugboard = new Plugboard();
		
		assertThrows(NullPointerException.class, () -> plugboard.addCables(null));
		assertThrows(NullPointerException.class, () -> plugboard.removeCables(null));
	}
	
	@Test
	@Order(103)
	public void testGetForward() {
		final Plugboard plugboard = new Plugboard() //
				.addCables("AE BJ CM DZ FL GY HX IV KW NR OQ PU ST");

		for(char i = 0; i < Character.MAX_VALUE; i++) {
			final char inputCharacter = i;
			
			if (isInRange(inputCharacter)) {
				assertEquals(inputCharacter, plugboard.getForward(plugboard.getReverse(inputCharacter)));
			} else {
				assertThrows(IllegalArgumentException.class, () -> plugboard.getForward(inputCharacter));
			}
		}
	}
	
	@Test
	@Order(104)
	public void testGetReverse() {
		final Plugboard plugboard = new Plugboard() //
				.addCables("AE BJ CM DZ FL GY HX IV KW NR OQ PU ST");
		
		for(char i = 0; i < Character.MAX_VALUE; i++) {
			final char inputCharacter = i;
			
			if (isInRange(inputCharacter)) {
				assertEquals(inputCharacter, plugboard.getReverse(plugboard.getForward(inputCharacter)));
			} else {
				assertThrows(IllegalArgumentException.class, () -> plugboard.getReverse(inputCharacter));
			}
		}
	}

}
