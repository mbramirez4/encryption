package encryption.Model;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import encryption.Interface.EncryptedContainer;
import encryption.Util.SimpleLinkedList;

public final class Encryptor {
    private static final Logger logger = LogManager.getLogger(Encryptor.class.getName());
    private static final Logger timesLogger = LogManager.getLogger("times");
    
    // this class should not be instantiated
    private Encryptor() {}

    public static EncryptedContainer<Integer> encrypt(String word) {
        logger.info("Encryption of word started");
        logger.debug("Word to encrypt: " + word);

        long initialTime = System.nanoTime();

        char[] chars = word.toCharArray();
        
        int encryptedChar;
        EncryptedContainer<Integer> encryptedWord = new SimpleLinkedList<>();
        for (int i = 0; i < chars.length; i++) {
            encryptedChar = (int) chars[i] + (2 * i + 1);
            encryptedWord.addLast(encryptedChar);
        }

        long finalTime = System.nanoTime();
        logger.debug("Chars encryption finished: \n" + encryptedWord);
        timesLogger.info("Encryption of characters in a word with " + chars.length + " letters took " + (finalTime - initialTime) + " ns");        
        
        initialTime = System.nanoTime();
        encryptedWord.swapAdjacentData();
        finalTime = System.nanoTime();
        
        logger.debug("Adjacent data swapped: \n" + encryptedWord);
        timesLogger.info("Swapping adjacent data in a structure with " + chars.length + " letters took " + (finalTime - initialTime) + " ns");

        logger.info("Word encryption finished: \n" + encryptedWord);

        return encryptedWord;
    }

    public static String decrypt(EncryptedContainer<Integer> encryptedWord) {
        logger.info("Decryption of word started");
        logger.debug("Data to decrypt: \n" + encryptedWord);

        long initialTime = System.nanoTime();
        encryptedWord.swapAdjacentData();
        long finalTime = System.nanoTime();

        logger.debug("Adjacent data swapped: \n" + encryptedWord);
        timesLogger.info("Swapping adjacent data in a structure with " + encryptedWord.size() + " letters took " + (finalTime - initialTime) + " ns");

        initialTime = System.nanoTime();

        int i = 0;
        char[] chars = new char[encryptedWord.size()];
        for (Integer encryptedChar : encryptedWord) {
            chars[i] = (char) (encryptedChar - (2 * i + 1));
            i++;
        }
        String decryptedWord = new String(chars);

        finalTime = System.nanoTime();
        
        logger.info("Word decryption finished: " + decryptedWord);
        timesLogger.info("Decryption of characters in a word with " + chars.length + " letters took " + (finalTime - initialTime) + " ns");

        return decryptedWord;
    }
}
