package always;
import java.awt.Desktop;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class GoogleAuth {
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final Collection<String> SCOPE = Arrays.asList("http://picasaweb.google.com/data/");
    private static final String CALLBACK_URI = GoogleOAuthConstants.OOB_REDIRECT_URI;
    private static final String ENDPOINT = "https://accounts.google.com/o/oauth2/auth";
    private final GoogleAuthorizationCodeFlow flow;
    private static GoogleAuth instance;
    private String accessToken;
    private PicasaService picasaService;
    private Credential credential;

    public static GoogleAuth getInstance() {
        if (instance == null) {
            synchronized (GoogleAuth.class) {
                instance = new GoogleAuth();
            }
        }

        return instance;
    }

    private GoogleAuth() {
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPE).build();
        picasaService = PicasaService.getInstance();
    }

    public boolean isTokenValid(String testToken) {
        if (testToken != null) {
            try {
                Credential testCredential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setFromTokenResponse(new TokenResponse().setAccessToken(testToken));
                if (picasaService.setCredential(testCredential)) {
                    credential = testCredential;
                    accessToken = testToken;
                    return true;
                }
            } catch (Exception e) {}
        }
        return false;
    }

    public String refreshUrl() {
        String url = flow.newAuthorizationUrl().setRedirectUri(CALLBACK_URI).build();

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            // TODO: DO something if browser doesn't open!??
            e.printStackTrace();
        }
        
        return url;
    }

    public void showDialog() {
        String url = refreshUrl();

        new AuthorizationDialog(this, url);
    }

    public boolean getAccessToken(String code) {
        try {
            GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
            return isTokenValid(response.getAccessToken());
        } catch (Exception e) {
        }
        return false;
    }
}
