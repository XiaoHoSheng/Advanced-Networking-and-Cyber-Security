/*
COMPX304_Assignment_3
ID Number:1614276
Name：Jason Yang
*/
public class XORDecipher {
    // The key used for the XOR decipher
    private String key;

    /**
     * Initializes the XORDecipher with the specified key.
     * @param key The key to be used for decryption.
     */
    public XORDecipher(String key) {
        // Set the key for the XOR decipher
        this.key = key;
    }

    /**
     * Decrypts the input string using the XOR cipher.
     * Each character in the input string is XORed with the corresponding character in the key.
     * If the key is shorter than the input, it wraps around and repeats.
     * @param input The input string to be decrypted.
     * @return The decrypted string.
     */
    public String decrypt(String input) {
        // StringBuilder to construct the output string
        StringBuilder output = new StringBuilder();

        // Loop through each character in the input string
        for (int i = 0; i < input.length(); i++) {
            // Get the current character from the input string and convert it to a binary digit (0 or 1)
            int inputBit = input.charAt(i) - '0';
            // Get the corresponding character from the key (wrapping around if necessary) and convert it to a binary digit (0 or 1)
            int keyBit = key.charAt(i % key.length()) - '0';
            // XOR the input bit with the key bit
            int bit = inputBit ^ keyBit;
            // Append the result to the output string
            output.append(bit);
        }

        // Return the output string
        return output.toString();
    }
}
