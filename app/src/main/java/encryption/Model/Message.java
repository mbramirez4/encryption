package encryption.Model;

import java.util.ArrayDeque;
import java.util.Queue;

import encryption.Interface.EncryptedContainer;

public final class Message {
    private String originalMessage;
    
    private String encryptedMessage;
    private Queue<EncryptedContainer<Integer>> encryptedWords;

    public Message() {
        this("Hello world!");
    }

    public Message(String message) {
        this.originalMessage = message.trim();

        encryptedWords = new ArrayDeque<>();
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public void encryptMessage() {
        EncryptedContainer<Integer> encryptedWord;
        
        encryptedMessage = "";

        String[] words = originalMessage.split(" ");
        for (String word : words) {
            encryptedWord = Encryptor.encrypt(word);
            encryptedWords.offer(encryptedWord);

            encryptedMessage += encryptedWord.dataAsString() + " ";
        }

        encryptedMessage = encryptedMessage.trim();
    }

    @Override
    public String toString() {
        return "Message{ encryptedMessage='" + encryptedMessage + "' }";
    }
}
