package encryption.Model;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import encryption.Interface.EncryptedContainer;
import encryption.Util.SimpleLinkedList;

public final class Encryptor {
    private static final Logger logger = LogManager.getLogger(Encryptor.class.getName());
    
    // this class should not be instantiated
    private Encryptor() {}

    public static EncryptedContainer<Integer> encrypt(String word) {
        logger.info("Encryption of word started");
        logger.debug("Word to encrypt: " + word);

        char[] chars = word.toCharArray();
        
        int encryptedChar;
        EncryptedContainer<Integer> encryptedWord = new SimpleLinkedList<>();
        for (int i = 0; i < chars.length; i++) {
            encryptedChar = (int) chars[i] + (2 * i + 1);
            encryptedWord.addLast(encryptedChar);
        }
        
        logger.debug("Chars encryption finished: \n" + encryptedWord);
        
        encryptedWord.swapAdjacentData();
        logger.debug("Adjacent data swapped: \n" + encryptedWord);

        return encryptedWord;
    }
}
