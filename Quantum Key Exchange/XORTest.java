/*
COMPX304_Assignment_3
ID Number:1614276
Name：Jason Yang
*/
import org.junit.Test; // Import the Test annotation from JUnit
import static org.junit.Assert.*; // Import all static methods from the Assert class for assertions

public class XORTest {

    @Test // Marks this method as a test method
    public void testEncryption() {
        // Define the key for the XOR cipher
        String key = "001";
        // Define the original message to be encrypted
        String originalMessage = "0100110101100101";
        // Create a new XORCipher object with the given key
        XORCipher cipher = new XORCipher(key);
        // Encrypt the original message using the cipher
        String encryptedMessage = cipher.encrypt(originalMessage); // Encrypted message
        // Print the encrypted message (for debugging purposes)
        System.out.println(encryptedMessage);
        // Assert that the encrypted message is not null
        assertNotNull(encryptedMessage);
    }

    @Test // Marks this method as a test method
    public void testDecryption() {
        // Define the key for the XOR cipher
        String key = "001";
        // Define the original message to be encrypted and decrypted
        String originalMessage = "0110100010010011"; // This is a secret message
        // Create a new XORCipher object with the given key
        XORCipher cipher = new XORCipher(key);
        // Encrypt the original message using the cipher
        String encryptedMessage = cipher.encrypt(originalMessage); // Encrypted message
        // Create a new XORDecipher object with the same key
        XORDecipher decipher = new XORDecipher(key);
        // Decrypt the encrypted message using the decipher
        String decryptedMessage = decipher.decrypt(encryptedMessage); // Decrypted message
        // Assert that the decrypted message is not null
        assertNotNull(decryptedMessage);
        // Assert that the decrypted message matches the original message
        assertEquals(originalMessage, decryptedMessage); // Compare the original message with the decrypted message
    }
}
