package mariocraft.test;

import mariocraft.geom.Vector2D;
import junit.framework.TestCase;

/**
 * A class made for testing purposes of the Vector2D class.
 * @author Erik
 *
 */
public class VectorTest extends TestCase {

	private static final float MAX_ERROR = 0.001f;
	
	Vector2D v1 = new Vector2D(1, 2);
	Vector2D v2 = new Vector2D(-2, 1);
	Vector2D v3 = new Vector2D(0, 1);
	Vector2D v4 = new Vector2D(-1, 0);
	
	/**
	 * Tests if the norm() method works correctly.
	 */
	public void testNorm() {
		assertEquals(v1.norm(), v2.norm());
		assertTrue(v1.norm() != v3.norm());
		assertTrue(v2.norm() != v3.norm());
		assertTrue(v3.norm() == 1);
		assertTrue(Vector2D.ZERO.norm() == 0);
	}
	
	/**
	 * Tests if the normalize() method works correctly, i.e.
	 * returns a vector with the same direction but norm 1. Floating
	 * point numbers aren't exact so a failure of up to 10^-3 is accepted.
	 * Normalizing the zero vector isn't allowed and should raise an
	 * exception.
	 */
	public void testNormalize() {
		assertTrue(almostEqual(v1.normalize().norm(), 1));
		assertTrue(almostEqual(v2.normalize().norm(), 1));
		assertTrue(almostEqual(v3.normalize().norm(), 1));
		assertTrue(almostEqual(v4.normalize().norm(), 1));
		assertEquals(v1.normalize().arg(), v1.arg());
		assertEquals(v2.normalize().arg(), v2.arg());
		assertEquals(v3.normalize().arg(), v3.arg());
		assertEquals(v4.normalize().arg(), v4.arg());
		try {
			Vector2D.ZERO.normalize();
			fail();
		} catch (UnsupportedOperationException e) {}
	}
	
	/**
	 * Tests if the dotProduct() method works correctly.
	 */
	public void testDotProduct() {
		assertEquals(Vector2D.dotProduct(v1, v2), 0f);
		assertEquals(Vector2D.dotProduct(v1, v3), 2f);
		assertEquals(Vector2D.dotProduct(v2, v3), 1f);
		assertEquals(Vector2D.dotProduct(v1, v4), -1f);
		assertEquals(Vector2D.dotProduct(v2, v4), 2f);
		assertEquals(Vector2D.dotProduct(v3, v4), 0f);
		assertEquals(Vector2D.dotProduct(v1, v1), v1.norm()*v1.norm());
		assertEquals(Vector2D.dotProduct(v2, v2), v2.norm()*v2.norm());
		assertEquals(Vector2D.dotProduct(v3, v3), v3.norm()*v3.norm());
		assertEquals(Vector2D.dotProduct(v4, v4), v4.norm()*v4.norm());
	}
	
	/**
	 * Tests if the arg() method works correctly, i.e. returns the
	 * angle between the two vectors in radians. The angle of the zero
	 * vector isn't defined and should raise an exception.
	 */
	public void testArg() {
		assertEquals(v3.arg(), (float) Math.PI / 2);
		try {
			Vector2D.ZERO.arg();
			fail();
		} catch (IllegalArgumentException e) {}
	}
	
	/**
	 * Tests if the reverse() method works correctly.
	 */
	public void testReverse() {
		final float PI = (float) Math.PI;
		Vector2D rev1 = v1.reverse();
		Vector2D rev2 = v2.reverse();
		Vector2D rev3 = v3.reverse();
		Vector2D rev4 = v4.reverse();
		assertEquals(rev1, v1.scalarMultiply(-1));
		assertEquals(rev2, v2.scalarMultiply(-1));
		assertEquals(rev3, v3.scalarMultiply(-1));
		assertEquals(rev4, v4.scalarMultiply(-1));
		System.out.println(rev4.arg() + " " + (v4.arg() + PI));
		assertEquals(rev1.arg(), v1.arg() - PI);
		assertEquals(rev2.arg(), v2.arg() - PI);
		assertEquals(rev3.arg(), v3.arg() - PI);
		assertTrue(almostEqual(rev4.arg(), v4.arg() + PI));
	}
	
	/**
	 * Tests if the add() method work correctly
	 */
	public void testSumAndAdd() {
		assertEquals(Vector2D.add(v1, v2), new Vector2D(-1, 3));
		assertEquals(Vector2D.add(v1, v3), new Vector2D(1, 3));
		assertEquals(Vector2D.add(v1, v4), new Vector2D(0, 2));
		assertEquals(Vector2D.add(v2, v3), new Vector2D(-2, 2));
		assertEquals(Vector2D.add(v2, v4), new Vector2D(-3, 1));
		assertEquals(Vector2D.add(v3, v4), new Vector2D(-1, 1));
		assertEquals(Vector2D.add(v1, v1), v1.scalarMultiply(2));
		assertEquals(Vector2D.add(v2, v2), v2.scalarMultiply(2));
		assertEquals(Vector2D.add(v3, v3), v3.scalarMultiply(2));
		assertEquals(Vector2D.add(v4, v4), v4.scalarMultiply(2));
	}
	
	/**
	 * Tests if the aim() method works correctly.
	 */
	public void testAim() {
		assertEquals(v1.aim(v2).arg(), v2.arg());
		assertEquals(v1.aim(v3).arg(), v3.arg());
		assertEquals(v1.aim(v4).arg(), v4.arg());
		assertEquals(v2.aim(v3).arg(), v3.arg());
		assertEquals(v2.aim(v4).arg(), v4.arg());
		assertEquals(v3.aim(v4).arg(), v4.arg());
		try {
			v1.aim(Vector2D.ZERO);
			fail();
		} catch (IllegalArgumentException e){}
	}
	
	// Returns true if the difference between the numbers is within
	// the valid range.
	private boolean almostEqual(float f1, float f2) {
		return Math.abs(f1 - f2) < MAX_ERROR;
	}
}
