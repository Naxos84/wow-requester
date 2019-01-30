package com.example.address.view;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.example.address.App;
import com.example.address.helper.FullResponseBuilder;
import com.example.address.helper.ParameterStringBuilder;
import com.example.address.helper.UrlBuilder;
import com.example.address.model.Token;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RootLayoutController {

    private App app;

    @FXML
    private Menu requests;

    @FXML
    private Label accessTokenLabel;

    public void setApplication(final App app) {
        this.app = app;
        accessTokenLabel
                .setText(MessageFormat.format(app.getString("LOG.ACCESSTOKEN"), app.getString("ACCESSTOKEN.UNSET")));

    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void about() {
        final Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(app.getString("APP_TITLE"));
        alert.setHeaderText(app.getString("MENU.HELP.ABOUT"));
        final Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:resources/images/Address_Book_Icon.png"));
        alert.setContentText("Author: Matthias Kolley.\nFrom: Tutorial on code.makery.ch/library/javafx-tutorial");
        alert.showAndWait();
    }

    @FXML
    private void showBossApi() {

    }

    @FXML
    private void showCharacterFeed() {
        app.showCharacterFeed();
    }

    @FXML
    private void showGuildApi() {

    }

    @FXML
    private void showDataApi() {

    }

    @FXML
    private void selectGermanLanguage() {
        app.selectLanguage(Locale.GERMANY);
    }

    @FXML
    private void selectEnglishLanguage() {
        app.selectLanguage(Locale.ENGLISH);
    }

    @FXML
    private void requestUserAccessToken() {
        try {
            final URL url = new URL("https://eu.battle.net/oauth/token");
            final HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            final Map<String, String> params = new HashMap<>();
            params.put("client_id", "276b568afa4d41979e88410e6bcaaf94");
            params.put("client_secret", "9GD1RtUgz86Ua7HAQmWL8EuR62IgRkyr");
            params.put("grant_type", "client_credentials");

            final DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(params));
            out.flush();
            out.close();

            con.connect();
            final Token token = FullResponseBuilder.getFullResponseAsJson(con, Token.class);
            app.setAccessToken(token.access_token);
            for (final MenuItem mi : requests.getItems()) {
                mi.setDisable(false);
            }

            accessTokenLabel
                    .setText(MessageFormat.format(app.getString("LOG.ACCESSTOKEN"), app.getString("ACCESSTOKEN.SET")));
            System.out.println("Token: " + token.access_token);

        } catch (final MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    private void requestCharacterFeed() {
        System.out.println("Not implemented");
    }

    @FXML
    private void requestBosses() {
        try {
            final Map<String, String> params = new HashMap<>();
            params.put("locale", "de_DE");
            params.put("access_token", app.getAccessToken());

            final URL url = new URL(UrlBuilder.buildGetUrl("https://eu.api.blizzard.com/wow/boss/", params));
            final HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setRequestProperty("Accept", "application/json");

            con.connect();
            final Object token = FullResponseBuilder.getFullResponseAsJson(con, Object.class);
            System.out.println(token);

        } catch (final MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
