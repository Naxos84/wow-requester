package com.example.address.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.example.address.App;
import com.example.address.helper.FullResponseBuilder;
import com.example.address.helper.UrlBuilder;
import com.example.address.model.BossKillFeed;
import com.example.address.model.Feed;
import com.example.address.model.FeedResponse;
import com.example.address.model.LootFeed;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FeedOverviewController {

    @FXML
    private TableView<Feed> feedTable;
    @FXML
    private TableColumn<Feed, String> timestampColumn;
    @FXML
    private TableColumn<Feed, String> typeColumn;

    private final ObservableList<Feed> feedData = FXCollections.observableArrayList();

    @FXML
    private AnchorPane empty;
    @FXML
    private AnchorPane lootFeed;
    @FXML
    private AnchorPane bosskillFeed;

    @FXML
    private Label feedDetails;

    @FXML
    private Button requestButton;

    // Reference to the main application.
    private App mainApp;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public FeedOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        typeColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) {
                return new ReadOnlyStringWrapper("Unknown Feed");
            }
            return cellData.getValue().typeProperty();
        });
        timestampColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) {
                return new ReadOnlyStringWrapper("n/a");
            }
            final Locale selectedLocale = mainApp.getSelectedLocale();
            // TODO improve by deserializing timestamp as Date
            final Date date = new Date(cellData.getValue().timestampProperty().getValue());
            final LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            final String s = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(selectedLocale)
                    .withZone(ZoneId.systemDefault()).format(ldt);
            return new ReadOnlyStringWrapper(s);
        });

        showFeedDetails(null);

        feedTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showFeedDetails(newValue);
        });
    }

    private void showFeedDetails(final Feed feed) {
        if (feed == null) {
            empty.toFront();
        } else {
            if (feed instanceof LootFeed) {
                feedDetails.setText("Loot Feed");
                lootFeed.toFront();
                final LootFeed lf = (LootFeed) feed;
                for (final Node n : lootFeed.getChildren()) {
                    if ("itemId".equals(n.getId())) {
                        ((Label) n).setText(String.valueOf(lf.getItemId()));
                    } else if ("bonusList".equals(n.getId())) {
                        ((Label) n).setText(lf.getBonusList().toString());
                    } else if ("context".equals(n.getId())) {
                        ((Label) n).setText(lf.getContext());
                    }
                }
            } else if (feed instanceof BossKillFeed) {
                bosskillFeed.toFront();
                feedDetails.setText("Bosskill Feed");
                final BossKillFeed af = (BossKillFeed) feed;
                for (final Node n : bosskillFeed.getChildren()) {
                    if ("achievement".equals(n.getId())) {
                        ((Label) n).setText(af.achievement.title);
                    } else if ("points".equals(n.getId())) {
                        ((Label) n).setText(String.valueOf(af.achievement.points));
                    } else if ("description".equals(n.getId())) {
                        ((Label) n).setText(af.achievement.description);
                    }
                }
            } else {
                feedDetails.setText("Unknown Feed");
                empty.toFront();
            }

        }
    }

    @FXML
    public void requestFeed() {
        feedData.clear();
        if (mainApp.getAccessToken() == null) {
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Access Token.");
            final Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:resources/images/Address_Book_Icon.png"));
            alert.setContentText("The Access Token is missing. Please request an Acces Token first.");
            alert.showAndWait();
            return;
        }
        try {
            final Map<String, String> params = new HashMap<>();
            params.put("fields", "feed");
            params.put("locale", "de_DE");
            params.put("access_token", mainApp.getAccessToken());

            final URL url = new URL(
                    UrlBuilder.buildGetUrl("https://eu.api.blizzard.com/wow/character/Thrall/Djummel", params));
            final HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setRequestProperty("Accept", "application/json");

            final FeedResponse token = FullResponseBuilder.getFullResponseAsJson(con, FeedResponse.class);
            feedData.addAll(token.feed);

        } catch (final MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        feedTable.getSelectionModel().select(0);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(final App mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        feedTable.setItems(feedData);
    }
}
