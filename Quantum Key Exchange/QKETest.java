/*
COMPX304_Assignment_3
ID Number:1614276
Name：Jason Yang
*/
import org.junit.Test; // Import the Test annotation from JUnit
import java.util.List; // Import the List class from java.util package
import static org.junit.Assert.*; // Import all static methods from Assert class for assertions

public class QKETest {

    @Test
    public void testQKEWith16Qubits() {
        // Number of qubits to be used for this test
        int numQubits = 16;
        // Initialize the QKE (Quantum Key Exchange) object with the specified number of qubits
        QKE qke = new QKE(numQubits);

        /**
         * The receiver receives the qubits and measures them with random polarizations.
         * This method returns the polarizations used for the measurements.
         */
        List<Integer> polarizations = qke.receiveQubits();

        // Generate the secret key based on the polarizations used by the receiver
        qke.generateSecretKey(polarizations);

        // Print the sender's and receiver's secret keys (for debugging purposes)
//        System.out.println("Sender secret key: " + qke.getSenderSecretKey());
//        System.out.println("Receiver secret key: " + qke.getRecivedSecretKey());

        // Use the sender's secret key to encrypt the message
        XORCipher cipher = new XORCipher(qke.getSenderSecretKey());
        // Use the receiver's secret key to decrypt the message
        XORDecipher decipher = new XORDecipher(qke.getRecivedSecretKey());

        // Assume the original message is a binary string
        String originalMessage = "110011001100110010101001110101001010101010101010001011110101";
        // Encrypt the original message using the cipher
        String encryptedMessage = cipher.encrypt(originalMessage);
        // Decrypt the encrypted message using the decipher
        String decryptedMessage = decipher.decrypt(encryptedMessage);

        // Assert that the decrypted message matches the original message
        assertEquals(originalMessage, decryptedMessage);
    }

    @Test
    public void testQKEWith256Qubits() {
        // Number of qubits to be used for this test
        int numQubits = 256;
        // Initialize the QKE (Quantum Key Exchange) object with the specified number of qubits
        QKE qke = new QKE(numQubits);

        /**
         * The receiver receives the qubits and measures them with random polarizations.
         * This method returns the polarizations used for the measurements.
         */
        List<Integer> polarizations = qke.receiveQubits();

        // Generate the secret key based on the polarizations used by the receiver
        qke.generateSecretKey(polarizations);

        // Use the sender's secret key to encrypt the message
        XORCipher cipher = new XORCipher(qke.getSenderSecretKey());
        // Use the receiver's secret key to decrypt the message
        XORDecipher decipher = new XORDecipher(qke.getRecivedSecretKey());

        // A longer binary string for testing
        String originalMessage = "110011001100110010101001110101001010101010101010001011110101";
        // Encrypt the original message using the cipher
        String encryptedMessage = cipher.encrypt(originalMessage);
        // Decrypt the encrypted message using the decipher
        String decryptedMessage = decipher.decrypt(encryptedMessage);

        // Assert that the decrypted message matches the original message
        assertEquals(originalMessage, decryptedMessage);
    }

    @Test
    public void testQKEWith1024Qubits() {
        // Number of qubits to be used for this test
        int numQubits = 1024;
        // Initialize the QKE (Quantum Key Exchange) object with the specified number of qubits
        QKE qke = new QKE(numQubits);

        /**
         * The receiver receives the qubits and measures them with random polarizations.
         * This method returns the polarizations used for the measurements.
         */
        List<Integer> polarizations = qke.receiveQubits();

        // Generate the secret key based on the polarizations used by the receiver
        qke.generateSecretKey(polarizations);

        // Use the sender's secret key to encrypt the message
        XORCipher cipher = new XORCipher(qke.getSenderSecretKey());
        // Use the receiver's secret key to decrypt the message
        XORDecipher decipher = new XORDecipher(qke.getRecivedSecretKey());

        // A much longer binary string for testing
        String originalMessage = "110011001100110010101001110101001010101010101010001011110101";
        // Encrypt the original message using the cipher
        String encryptedMessage = cipher.encrypt(originalMessage);
        // Decrypt the encrypted message using the decipher
        String decryptedMessage = decipher.decrypt(encryptedMessage);

        // Assert that the decrypted message matches the original message
        assertEquals(originalMessage, decryptedMessage);
    }

}
