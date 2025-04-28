/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;


import javax.mail.*;
import javax.mail.internet.*;


import java.util.List;
import java.util.Properties;
import java.util.Base64;


import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.OutputStream;

import java.util.Arrays;

import java.security.GeneralSecurityException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.ByteArrayOutputStream;
/**
 *
 * @author motaz
 */
public class GmailAuth {
    public static final String CLIENT_SECRET_FILE = "C:\\Users\\motaz\\OneDrive\\Bureau\\Locations app\\locationappartement-project\\GmailAUTHtest\\src\\main\\resources\\client_secret.json";
    public static final String APPLICATION_NAME = "Rentry";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/userinfo.email","https://www.googleapis.com/auth/gmail.readonly","https://www.googleapis.com/auth/gmail.modify","https://www.googleapis.com/auth/gmail.compose","https://www.googleapis.com/auth/gmail.send"); // Email scope
    public static GoogleClientSecrets getClientSecrets() throws IOException {
            File client_s = new File(CLIENT_SECRET_FILE);
            /*InputStream in = GmailAuth.class.getResourceAsStream(CLIENT_SECRET_FILE);
            if (in == null) {
                throw new FileNotFoundException("Resource not found: "+CLIENT_SECRET_FILE);
            }*/
            return GoogleClientSecrets.load(JSON_FACTORY, new FileReader(client_s));
           
    }
    public static com.google.api.services.gmail.model.Message createMessage(String toEmail, String subject, String bodyText) throws Exception {
        // Set up mail session properties
        Properties properties = System.getProperties();
        Session session = Session.getDefaultInstance(properties, null);

        // Create the MimeMessage
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress("mekkimotaz@gmail.com"));
        email.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        email.setSubject(subject);
        email.setText(bodyText);

        // Encode the message to Base64 and return it as a raw message
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        email.writeTo(byteArrayOutputStream);
        String rawMessage = Base64.getUrlEncoder().encodeToString(byteArrayOutputStream.toByteArray());

        return new com.google.api.services.gmail.model.Message().setRaw(rawMessage);
    }
    public static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws Exception {
        from = "mekkimotaz@gmail.com";
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        // MimeMessage is a concrete subclass of javax.mail.Message
        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);

        return email;
    }
    public static com.google.api.services.gmail.model.Message createMessageWithEmail(MimeMessage email) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawBytes = buffer.toByteArray();
        String encodedEmail = Base64.getUrlEncoder().encodeToString(rawBytes);

        com.google.api.services.gmail.model.Message message = new com.google.api.services.gmail.model.Message();
        message.setRaw(encodedEmail);
        return message;
    }
    public static Credential authorize(String username) throws IOException, GeneralSecurityException {
        // Load client secrets.
        
        GoogleClientSecrets clientSecrets = getClientSecrets();

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                SCOPES
        )
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("C:\\Users\\motaz\\OneDrive\\Bureau\\Locations app\\locationappartement-project\\Appli\\tokens")))
        .setAccessType("offline")
        .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(username);
    }
    public static void revokeToken(String accessToken) throws Exception {
        String revokeEndpoint = "https://oauth2.googleapis.com/revoke?token=" + accessToken;
        URL url = new URL(revokeEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Send an empty body just to trigger proper headers
        OutputStream os = connection.getOutputStream();
        os.write(0); // write a zero-length payload
        os.flush();
        os.close();
        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            System.out.println("Access token revoked successfully.");
        } else {
            System.out.println("Failed to revoke token. Response code: " + responseCode);
        }
    }
    private static Gmail getGmailService() throws Exception {

        GoogleClientSecrets clientSecrets = getClientSecrets();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("C:\\Users\\motaz\\OneDrive\\Bureau\\Locations app\\locationappartement-project\\Appli\\tokens")))
                .setAccessType("offline")
                .build();

        return new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user"))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    public static void main(String[] args) throws Exception {
        // Build the authorization code flow
        System.out.println("in1");System.out.println("in2");

        // Authorize the request
        Credential credential = authorize("user1");
        Credential credential2 = authorize("user2");
        System.out.println("in3");
        // Build the Gmail service
        Gmail gmailService = new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), 
                JSON_FACTORY, 
                credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Now, you can use the Gmail API to send/receive emails
        ListMessagesResponse messagesResponse = gmailService.users().messages().list("me").execute();
        System.out.println("Messages: " + messagesResponse);
        /*Gmail service = getGmailService();
        String userEmail = service.users().getProfile("me").execute().getEmailAddress();
        System.out.println(userEmail);*/
        Oauth2 oauth2 = new Oauth2.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        JacksonFactory.getDefaultInstance(),
        credential
        ).setApplicationName("Your App Name").build();

        Userinfo userInfo = oauth2.userinfo().get().execute();
        System.out.println("User email: " + userInfo.getEmail());
        
        MimeMessage msg = createEmail(userInfo.getEmail(),"mekkimotaz@gmail.com","Subject","hello");
        com.google.api.services.gmail.model.Message message = createMessageWithEmail(msg);
        com.google.api.services.gmail.model.Message sentMessage = getGmailService().users().messages().send("me", message).execute();
        System.out.println("Message ID: " + sentMessage.getId());
    }
}
