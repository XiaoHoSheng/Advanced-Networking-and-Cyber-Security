/*
COMPX304_Assignment_3
ID Number:1614276
Name：Jason Yang
*/
import static org.junit.Assert.assertEquals; // Import the assertEquals method from JUnit
import org.junit.Test; // Import the Test annotation from JUnit

public class QubitTest {
    /**
     * Test the measure method of the Qubit class.
     */
    @Test
    public void testMeasure() {
        // Create a new Qubit object with value 0 and polarization 1
        Qubit q = new Qubit(0, 1);

        // Test the measure method when the polarization matches
        // The qubit should return its value (0)
        assert q.measure(1) == 0 : "Test 1 failed";

        // Test the measure method when the polarization does not match
        // The qubit should return a new random value (0 or 1)
        assert q.measure(0) >= 0 && q.measure(0) <= 1 : "Test 2 failed";

        // Set the qubit's value to 1 and polarization to 0
        q.set(1, 0);

        // Test the measure method when the polarization matches
        // The qubit should return its value (1)
        assert q.measure(0) == 1 : "Test 3 failed";

        // Test the measure method when the polarization does not match
        // The qubit should return a new random value (0 or 1)
        assert q.measure(1) >= 0 && q.measure(1) <= 1 : "Test 4 failed";
    }
}
