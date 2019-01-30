package com.example.address;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.example.address.view.FeedOverviewController;
import com.example.address.view.RootLayoutController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    private static final String BUNDLE_PATH = "lang/messages";

    private Stage primaryStage;
    private BorderPane rootLayout;

    private String accessToken;

    private String currentView;

    private ResourceBundle bundle;

    public App() {
    }

    @Override
    public void init() throws Exception {
        final String country = System.getProperty("user.country");
        final String lang = System.getProperty("user.language");
        bundle = ResourceBundle.getBundle(BUNDLE_PATH, new Locale(lang, country));
        setCurrentView("../../../view/FeedOverview.fxml");
    }

    @Override
    public void start(final Stage primaryStage) {
        final FXMLLoader loader = new FXMLLoader();
        loader.setResources(bundle);
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(bundle.getString("APP_TITLE"));
        this.primaryStage.getIcons().add(new Image("images/wow_logo_240x240.png"));
        initRootLayout();
        showCurrentView();

    }

    public void initRootLayout() {
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setResources(bundle);
            loader.setLocation(App.class.getResource("../../../view/RootLayout.fxml"));
            rootLayout = loader.load();
            final RootLayoutController controller = loader.getController();
            controller.setApplication(this);

            final Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentView(final String view) {
        currentView = view;
    }

    @SuppressWarnings("unchecked")
    public <T> T showCurrentView() {
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setResources(bundle);
            loader.setLocation(App.class.getResource(currentView));
            final AnchorPane personOverview = loader.load();

            rootLayout.setCenter(personOverview);

            final FeedOverviewController controller = loader.getController();
            controller.setMainApp(this);
            return (T) controller;
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showCharacterFeed() {
        setCurrentView("../../../view/FeedOverview.fxml");
        final FeedOverviewController controller = showCurrentView();
        controller.requestFeed();
    }

    public void selectLanguage(final Locale locale) {
        bundle = ResourceBundle.getBundle(BUNDLE_PATH, locale);
        primaryStage.setTitle(bundle.getString("APP_TITLE"));
        initRootLayout();
        showCurrentView();
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getString(final String key) {
        return bundle.getString(key);
    }

    public Locale getSelectedLocale() {
        return bundle.getLocale();
    }
}
