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

        logger.info("Encryption of word finished: \n" + encryptedWord);

        return encryptedWord;
    }

    public static String decrypt(EncryptedContainer<Integer> encryptedWord) {
        logger.info("Decryption of word started");
        logger.debug("Data to decrypt: \n" + encryptedWord);

        encryptedWord.swapAdjacentData();
        logger.debug("Adjacent data swapped: \n" + encryptedWord);

        int i = 0;
        char[] chars = new char[encryptedWord.size()];
        for (Integer encryptedChar : encryptedWord) {
            chars[i] = (char) (encryptedChar - (2 * i + 1));
            i++;
        }

        String decryptedWord = new String(chars);
        logger.info("Decryption of word finished: " + decryptedWord);

        return decryptedWord;
    }
}
