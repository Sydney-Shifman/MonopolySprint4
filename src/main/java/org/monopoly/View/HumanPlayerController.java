package org.monopoly.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.monopoly.Model.Banker;
import org.monopoly.Model.Dice;
import org.monopoly.Model.GameBoard;
import org.monopoly.Model.GameTiles.GameTile;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.Model.Players.Player;
import org.monopoly.Model.TurnManager;
import org.monopoly.View.GameScene.Board.TradePaneController;
import org.monopoly.View.GameScene.GameScene;

import java.io.ByteArrayInputStream;
import java.io.IOException;


/**
 * Controller for the human player interface.
 * This class handles the player's actions and updates the UI accordingly.
 * @author walshj05
 */
public class HumanPlayerController {
    @FXML
    private Button mortProp;
    @FXML
    private Button getOutOfJail;
    @FXML
    private Button trade;
    @FXML
    private Button auction;
    @FXML
    private Button quit;
    @FXML
    private AnchorPane playerPane;
    @FXML
    private Button rollDice;
    @FXML
    private Button sell;
    @FXML
    private Button buyProp;
    @FXML
    private Button buyHouse;
    @FXML
    private Button buyHotel;
    @FXML
    private Button endTurn;
    @FXML
    private Label name;
    @FXML
    private ImageView token;
    @FXML
    private Label money;
    @FXML
    private VBox properties;
    private HumanPlayer player;


    /**
     * Initializes the player interface with the given player.
     * @param player The player to initialize the interface for.
     * @author walshj05
     */
    public void setPlayer(Player player) {
        this.player = (HumanPlayer)player;
        name.setText(player.getName());
        token.setImage(new Image(GameScene.addFilePath(player.getToken().getIcon())));
        name.setText(player.getName());
        money.setText("Balance: $" + player.getBalance());
        rollDice.setDisable(true);
        disableTurnButtons();
        this.player.setController(this);
    }

    /**
     * Sets the token image for the player.
     * @author walshj05
     */
    public void updateProperties(){
        properties.getChildren().clear();
        for (String property : player.getPropertiesOwned()){
            properties.getChildren().add(new Label(property));
        }
    }

    /**
     * Updates the players information
     * @author walshj05
     */
    public void updatePlayerInfo(){
        money.setText("Balance: $" + player.getBalance());
        updateProperties();
    }

    /**
     * Handles the action when the player clicks the "Roll Dice" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onRollDice(ActionEvent actionEvent) {
        player.rollDice(Dice.getInstance());
        rollDice.setDisable(true);
        System.out.println(Dice.getInstance().getNumDoubles());
        checkForDouble();
    }

    /**
     * Handles the action when the player clicks the "Sell" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onSell(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Sell button clicked");
    }

    /**
     * Handles the action when the player clicks the "Buy Property" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onBuyProperty(ActionEvent actionEvent) {
        Banker banker = Banker.getInstance();
        player.resolveDecision();
        GameTile tile = GameBoard.getInstance().getTile(player.getPosition());
        player.purchaseProperty(tile.getName(), tile.getPrice());
        buyProp.setDisable(true);
        checkForDouble();
        System.out.println(banker.getBankNumProperties());
    }

    /**
     * Handles the action when the player clicks the "Buy House" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onBuyHouse(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Buy House button clicked");
    }

    /**
     * Handles the action when the player clicks the "Buy Hotel" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onBuyHotel(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Buy Hotel button clicked");
    }

    /**
     * Handles the action when the player clicks the "End Turn" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onEndTurn(ActionEvent actionEvent) {
        if (player.isInJail()){
            player.incrementJailTurns();
        }
        disableTurnButtons();
        TurnManager.getInstance().nextPlayersTurn();
    }

    /**
     * Handles the action when the player clicks the "Get Out of Jail" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onAuction(ActionEvent actionEvent) {
        player.resolveDecision();
        buyProp.setDisable(true);
        checkForDouble();
        System.out.println(player.getName() + " Auction button clicked");
    }

    /**
     * Handles the action when the player clicks the "Trade" button.
     * @param actionEvent The action event triggered by the button click.
     *
     * Developed by: shifmans
     */
    public void onTrade(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Trade button clicked");

        try {
            String fxmlPath3 = "/org/monopoly/View/GameScene/Trade/Trade.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath3));
            Parent root3 = loader.load();

            TurnManager turnManager = TurnManager.getInstance();
            TradePaneController tradePaneController = loader.getController();
            tradePaneController.initialize(player, turnManager.getPlayers());

            Stage tradeStage = new Stage();
            tradeStage.setTitle("Trade");
            tradeStage.setScene(new Scene(root3, 370, 400));

            tradeStage.initModality(Modality.APPLICATION_MODAL);
            tradeStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the player clicks the "Get Out of Jail" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onGetOutOfJail(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(GameScene.class.getResource("jailInterface.fxml"));
        try {
            Parent pane = fxmlLoader.load();
            JailTurnController controller = fxmlLoader.getController();
            controller.setPlayer(player);
            Stage stage = new Stage();
            stage.setTitle("Get Out of Jail");
            stage.setScene(new javafx.scene.Scene(pane));
            stage.show();
            getOutOfJail.setDisable(true);
        } catch (IOException e) {
            return;
        }
    }

    /**
     * Handles the action when the player clicks the "Mortgage Property" button.
     * @param actionEvent The action event triggered by the button click.
     * @author walshj05
     */
    public void onMortgageProperty(ActionEvent actionEvent) {
        System.out.println(player.getName() + " Mortgage Property button clicked");
    }

    /**
     * Starts the player's turn.
     * @author walshj05
     */
    public void startTurn(){
        if (player.isInJail()){
            GameScene.sendAlert("You are in jail! Attempt to get out of jail, manage your assets, or end your turn.");
            getOutOfJail.setDisable(false);
            endTurn.setDisable(false);
        } else {
            disableTurnButtons();
            rollDice.setDisable(false);
            System.out.println(rollDice.isDisable());
        }
    }

    /**
     * Enables the "Buy Property" and "Auction" buttons for the player.
     * @author walshj05
     */
    public void purchasingProperty(){
        disableTurnButtons();
        buyProp.setDisable(false);
        auction.setDisable(false);
    }

    /**
     * Disables the turn buttons for the player.
     * @author walshj05
     */
    private void disableTurnButtons(){
        getOutOfJail.setDisable(true);
        buyProp.setDisable(true);
        buyHouse.setDisable(true);
        buyHotel.setDisable(true);
        endTurn.setDisable(true);
//        rollDice.setDisable(true);
    }

    /**
     * Checks if the player has rolled doubles and updates the UI accordingly.
     * @author walshj05
     */
    private void checkForDouble(){
        System.out.println("Testing: This is the curr num doubles: " + Dice.getInstance().getNumDoubles());
        if (Dice.getInstance().isDouble() && Dice.getInstance().getNumDoubles() < 3 && buyProp.isDisable()){
            GameScene.sendAlert("You still need to roll again!");
            System.out.println("TEST doubles register");
            startTurn();
        } else if (Dice.getInstance().getNumDoubles() == 3){
            GameScene.sendAlert("You rolled doubles 3 times in a row! Go to jail!");
            player.goToJail();
            endTurn.setDisable(false);
        }
        else if (buyProp.isDisable()){
            endTurn.setDisable(false);
            GameScene.sendAlert("No more rolls! Manage your assets or end your turn!");
        }
    }

    /**
     * Removes the player from the game when they quit.
     *
     * Developed by: shifmans
     */
    public void onQuit(ActionEvent actionEvent) {
        GameScene.sendAlert(player.getName() + " quit the game.");
        TurnManager turnManager = TurnManager.getInstance();
        long humanPlayerCount = turnManager.getPlayers().stream()
                .filter(player -> player instanceof HumanPlayer)
                .count();

        Alert winnerAlert = new Alert(Alert.AlertType.INFORMATION);
        winnerAlert.setTitle("Game Results");
        winnerAlert.setContentText("Game Over! The winner is " + turnManager.getWealthiestPlayer().getName() + " with $" + turnManager.getWealthiestPlayer().getBalance() + "!");


        if (humanPlayerCount == 1) {
            HumanPlayer player = (HumanPlayer) turnManager.getPlayers().get(0);
            String simulatedInput = "Y\n";
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            player.endGame();
            winnerAlert.showAndWait();
            System.exit(0);
        }

        player.quitGame(player);

        Parent root = GameScene.getInstance().getScene().getRoot();
        if (root instanceof AnchorPane rootPane) {
            if (playerPane != null && rootPane.getChildren().contains(playerPane)) {
                double quittingPlayerY = playerPane.getLayoutY();
                rootPane.getChildren().remove(playerPane);
                moveUpPlayerInterfaces(rootPane, quittingPlayerY);
            }
        }
    }

    /**
     * Moves the player interfaces up when a player quits.
     * @param root The root pane containing the player interfaces.
     * @param startingY The Y-coordinate of the quitting player interface.
     *
     * Developed by: shifmans
     */
    private void moveUpPlayerInterfaces(AnchorPane root, double startingY) {
        for (Node node : root.getChildren()) {
            if (node instanceof AnchorPane playerInterface && playerInterface.getLayoutY() > startingY) {
                playerInterface.setLayoutY(playerInterface.getLayoutY() - 185);
            }
        }
    }

}
