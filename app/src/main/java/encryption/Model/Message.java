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

    public String decryptMessage() {
        String decryptedMessage = "";
        EncryptedContainer<Integer> encryptedWord;

        while (!encryptedWords.isEmpty()) {
            encryptedWord = encryptedWords.poll();
            decryptedMessage += Encryptor.decrypt(encryptedWord) + " ";
        }

        return decryptedMessage.trim();
    }

    public boolean checkDecryptedMessage(String message) {
        return originalMessage.equals(message);
    }

    @Override
    public String toString() {
        return "Message{ encryptedMessage='" + encryptedMessage + "' }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;
        return (
            encryptedMessage.equals(message.encryptedMessage)
            && originalMessage.equals(message.originalMessage)
        );
    }
}
