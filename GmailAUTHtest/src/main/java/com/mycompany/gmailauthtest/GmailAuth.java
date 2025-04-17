/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmailauthtest;

import java.util.Collections;
import java.util.List;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import java.security.GeneralSecurityException;

/**
 *
 * @author motaz
 */
public class GmailAuth {
    private static final String CLIENT_SECRET_FILE = "C:/Users/motaz/OneDrive/Bureau/JAVAPROJECT/test gmail auth/client_secret.json";
    private static final String APPLICATION_NAME = "Your App Name";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_READONLY);
    private static GoogleClientSecrets getClientSecrets() throws IOException {
        File clientSecretFile = new File(CLIENT_SECRET_FILE);
        if (!clientSecretFile.exists()) {
            throw new FileNotFoundException("File not found: " + CLIENT_SECRET_FILE);
        }
        try (InputStream in = new FileInputStream(clientSecretFile)) {
            return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        }
    }
    public static void main(String[] args) throws Exception {
        // Build the authorization code flow
        System.out.println("in1");
        

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getClientSecrets(),
                SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .build();System.out.println("in2");

        // Authorize the request
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
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
    }
}
