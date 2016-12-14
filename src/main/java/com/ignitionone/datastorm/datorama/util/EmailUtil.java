package com.ignitionone.datastorm.datorama.util;

import javax.mail.*;
import java.util.Date;
import java.util.Properties;
import java.util.function.Function;

public class EmailUtil implements AutoCloseable {

    private final String SessionType = "imap";
    private Store EmailStore;
    private Folder Folder;

    public EmailUtil (String incomingHost, String userName, String password, String folderName){
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");

        try {
            Session emailSession = Session.getInstance(props);
            EmailStore = emailSession.getStore(SessionType);
            EmailStore.connect(incomingHost, userName, password);

            Folder = EmailStore.getFolder(folderName);
            Folder.open(Folder.READ_ONLY);
        } catch (MessagingException ex) {
            close();
            throw new RuntimeException(ex);
        }
    }

    public <T> T PollMessagesForResult(int messageCount, int timeoutSeconds, int pollingInterval, Function<Message[], T> resultFunc) throws InterruptedException {
        long startMilliseconds = new Date().getTime();
        long pollingMilliseconds = 0;
        long timeoutMilliseconds = timeoutSeconds * 1000;
        T result = null;

        while (result == null && pollingMilliseconds <= timeoutMilliseconds) {
            try {
                int totalMessages = Folder.getMessageCount();
                Message[] messages = Folder.getMessages(totalMessages - messageCount, totalMessages);
                result = resultFunc.apply(messages);
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }

            pollingMilliseconds = new Date().getTime() - startMilliseconds;

            if (result == null) {
                Thread.sleep(pollingInterval);
            }
        }

        return result;
    }

    @Override
    public void close() {
        try {
            if (Folder != null) {
                Folder.close(false);
            }
            if (EmailStore != null) {
                EmailStore.close();
            }
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
