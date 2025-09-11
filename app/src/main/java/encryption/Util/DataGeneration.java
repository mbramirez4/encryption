package encryption.Util;

import java.util.Random;
import encryption.Model.Encryptor;
import encryption.Interface.EncryptedContainer;
import encryption.Model.Message;

public class DataGeneration {
    private static Random random = new Random();
    private static char[] generateRandomChars(int size) {
        char[] chars = new char[size];
        for (int i = 0; i < size; i++) {
            chars[i] = (char) (random.nextInt(33, 126));
        }
        return chars;
    }

    private static String generateRandomString(int size) {
        return new String(generateRandomChars(size));
    }

    private static void encryptAndDecrypt(String word) {
        EncryptedContainer<Integer> encryptedWord = Encryptor.encrypt(word);
        String decryptedWord = Encryptor.decrypt(encryptedWord);
        assert word.equals(decryptedWord);
    }

    public static void generateWordsData() {
        String word;
        int[] wordsSizes = {2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000};
        for (int i = 0; i < 10; i++) {
            for (int wordSize : wordsSizes) {
                word = generateRandomString(wordSize);
                encryptAndDecrypt(word);
            }
        }
    }

    public static void generateMessagesData() {
        String word;
        String text;
        String decryptedText;
        Message message = new Message();

        int[] wordsSizes = {2, 5, 10, 25, 50, 75, 100, 125, 150, 200};
        int[] wordsAmounts = {5, 20, 50, 100, 200, 400, 600, 800, 1000, 1200, 1500};
        for (int i = 0; i < 10; i++) {
            for (int numWords : wordsAmounts) {
                for (int wordSize : wordsSizes) {
                    /* Uncomment the following line to get the worst case scenario */
                    // wordSize = numWords;
                    text = "";
                    for (int j = 0; j < numWords; j++) {
                        word = generateRandomString(wordSize);
                        text += word + " ";
                    }
                    
                    message.setMessage(text);
                    message.encryptMessage();
                    decryptedText = message.decryptMessage();
                    assert message.checkDecryptedMessage(decryptedText);
                }
            }
        }
    }

    public static void main(String[] args) {
        generateMessagesData();
    }
}
