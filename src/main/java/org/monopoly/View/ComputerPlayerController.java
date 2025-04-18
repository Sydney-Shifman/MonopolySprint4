package org.monopoly.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.monopoly.Model.Players.Player;
import org.monopoly.View.GameScene.GameScene;

public class ComputerPlayerController {
    @FXML
    private AnchorPane playerPane;
    @FXML
    private Label name;
    @FXML
    private ImageView token;
    @FXML
    private Label balance;
    @FXML
    private VBox properties;
    private Player player;

    @FXML
    private void initialize() {
        // Initialize the player interface if needed
        // For example, set a default image or style
        playerPane.setVisible(true);
    }


    /**
     * Initializes the player interface with the given player.
     * @param player The player to initialize the interface for.
     * @author walshj05
     */
    public void setPlayer(Player player) {
        this.player = player;
        player.move(0);
        this.token.setImage(new Image(GameScene.addFilePath(player.getToken().getIcon())));
        name.setText(player.getName());
        balance.setText("Balance: $" + player.getBalance());
        // Update properties list if needed
    }
}
