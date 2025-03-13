/*
COMPX304_Assignment_3
ID Number:1614276
Name：Jason Yang
*/
import java.util.Random; // Import the Random class from the java.util package

public class Qubit {
    // The value of the qubit (0 or 1)
    private int value;
    // The polarization of the qubit (0 or 1)
    private int polarization;

    /**
     * Initializes a new qubit with the specified value and polarization.
     * @param value The initial value of the qubit (0 or 1)
     * @param polarization The initial polarization of the qubit (0 or 1), 0 for circular, 1 for linear
     */
    public Qubit(int value, int polarization) {
        // Set the value of the qubit to the specified value
        this.value = value;
        // Set the polarization of the qubit to the specified polarization
        this.polarization = polarization;
    }

    /**
     * Initializes a new qubit with a random value and polarization.
     */
    public Qubit() {
        Random rand = new Random(); // Create a new Random object
        // Set the value of the qubit to a random value (0 or 1)
        this.value = rand.nextInt(2);
        // Set the polarization of the qubit to a random value (0 or 1)
        this.polarization = rand.nextInt(2);
    }

    /**
     * Sets the value and polarization of the qubit to the specified values.
     * @param value The new value of the qubit (0 or 1)
     * @param polarization The new polarization of the qubit (0 or 1), 0 for circular, 1 for linear
     */
    public void set(int value, int polarization) {
        // Update the value of the qubit
        this.value = value;
        // Update the polarization of the qubit
        this.polarization = polarization;
    }

    /**
     * Measures the qubit based on its current polarization.
     * If the polarization matches the current polarization of the qubit,
     * returns the current value of the qubit. Otherwise, randomly sets
     * a new polarization and value for the qubit and returns the new value.
     * @param polarization The polarization used for measuring the qubit (0 for circular, 1 for linear)
     * @return The measured value of the qubit (0 or 1)
     */
    public int measure(int polarization) {
        // If the provided polarization matches the qubit's polarization
        if (polarization == this.polarization) {
            // Return the current value of the qubit
            return this.value;
        } else {
            // Create a new Random object
            Random random = new Random();
            // Randomly set a new polarization for the qubit (0 or 1)
            this.polarization = random.nextInt(2);
            // Randomly set a new value for the qubit (0 or 1)
            this.value = random.nextInt(2);
            // Return the new value of the qubit
            return this.value;
        }
    }

    /**
     * Returns the current value of the qubit.
     * @return The current value of the qubit (0 or 1)
     */
    public int getValue() {
        return value; // Return the value of the qubit
    }

    /**
     * Returns the current polarization of the qubit.
     * @return The current polarization of the qubit (0 or 1)
     */
    public int getPolarization() {
        return polarization; // Return the polarization of the qubit
    }
}
