package org.monopoly.View.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import org.monopoly.Model.Players.Token;
import org.monopoly.monopolygameproject.HelloApplication;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for the board
 * @author walshj05
 */
public class BoardController {
    @FXML
    private GridPane board;
    private TileController[] tileControllers;

    /**
     * Initializes the board with tiles
     * @throws IOException if an error occurs while loading the FXML files
     * @author walshj05
     */
    @FXML
    public void initialize() throws IOException {
        tileControllers = new TileController[40];
        initializeCorners();
        initializeLeftEdge();
        initializeTopEdge();
        initializeBottomEdge();
        initializeRightEdge();
    }

    /**
     * Initializes the corners of the board
     * @throws IOException if an error occurs while loading the FXML files
     * @author walshj05
     */
    private void initializeCorners() throws IOException {
        // initialize corners of the board
        tileControllers[0] = initializeCornerTile( 10, 10);
        tileControllers[10] = initializeCornerTile(10, 0);
        tileControllers[20] = initializeCornerTile(0, 0);
        tileControllers[30] = initializeCornerTile(0, 10);
        // initialize the rest of the tiles
    }

    /**
     * Initializes the left edge of the board
     * @throws IOException if an error occurs while loading the FXML files
     * @author walshj05
     */
    private void initializeLeftEdge() throws IOException {
        tileControllers[19] = initializePropertyTile(1, 0, 90);
        tileControllers[18] = initializePropertyTile(2, 0, 90);
        tileControllers[17] = initializeEdgeTile(3, 0, 90);
        tileControllers[16] = initializePropertyTile(4, 0, 90);
        tileControllers[15] = initializeEdgeTile(5, 0, 90);
        tileControllers[14] = initializePropertyTile(6, 0, 90);
        tileControllers[13] = initializePropertyTile(7, 0, 90);
        tileControllers[12] = initializeEdgeTile(8, 0, 90);
        tileControllers[11] = initializePropertyTile(9, 0, 90);
    }
    /**
     * Initializes the top edge of the board
     * @throws IOException if an error occurs while loading the FXML files
     * @author walshj05
     */
    private void initializeTopEdge() throws IOException {
        tileControllers[21] = initializePropertyTile(0, 1, 180);
        tileControllers[22] = initializeEdgeTile(0, 2, 180);
        tileControllers[23] = initializePropertyTile(0, 3, 180);
        tileControllers[24] = initializePropertyTile(0, 4, 180);
        tileControllers[25] = initializeEdgeTile(0, 5, 180);
        tileControllers[26] = initializePropertyTile(0, 6, 180);
        tileControllers[27] = initializePropertyTile(0, 7, 180);
        tileControllers[28] = initializeEdgeTile(0, 8, 180);
        tileControllers[29] = initializePropertyTile(0, 9, 180);
    }
    /**
     * Initializes the bottom edge of the board
     * @throws IOException if an error occurs while loading the FXML files
     * @author walshj05
     */
    private void initializeBottomEdge() throws IOException {
        tileControllers[1] = initializePropertyTile(10, 9, 0);
        tileControllers[2] = initializeEdgeTile(10, 8, 0);
        tileControllers[3] = initializePropertyTile(10, 7, 0);
        tileControllers[4] = initializeEdgeTile(10, 6, 0);
        tileControllers[5] = initializeEdgeTile(10, 5, 0);
        tileControllers[6] = initializePropertyTile(10, 4, 0);
        tileControllers[7] = initializeEdgeTile(10, 3, 0);
        tileControllers[8] = initializePropertyTile(10, 2, 0);
        tileControllers[9] = initializePropertyTile(10, 1, 0);
    }

    /**
     * Initializes the right edge of the board
     * @throws IOException if an error occurs while loading the FXML files
     * @author walshj05
     */
    private void initializeRightEdge() throws IOException {
        tileControllers[31] = initializePropertyTile(1, 10, 270);
        tileControllers[32] = initializePropertyTile(2, 10, 270);
        tileControllers[33] = initializeEdgeTile(3, 10, 270);
        tileControllers[34] = initializePropertyTile(4, 10, 270);
        tileControllers[35] = initializeEdgeTile(5, 10, 270);
        tileControllers[36] = initializeEdgeTile(6, 10, 270);
        tileControllers[37] = initializePropertyTile(7, 10, 270);
        tileControllers[38] = initializeEdgeTile(8, 10, 270);
        tileControllers[39] = initializePropertyTile(9, 10, 270);
    }

    /**
     * Initializes a corner tile
     * @param row the row of the tile
     * @param column the column of the tile
     * @return the controller for the tile
     * @throws IOException if an error occurs while loading the FXML files
     * @author walshj05
     */
    private TileController initializeCornerTile(int row, int column) throws IOException {
        FXMLLoader tileLoader = new FXMLLoader(HelloApplication.class.getResource("CornerTile.fxml"));
        Parent tileRoot = tileLoader.load();
        RegularGameTileController controller = tileLoader.getController();
        GridPane.setColumnIndex(tileRoot, column);
        GridPane.setRowIndex(tileRoot, row);
        GridPane.setColumnSpan(tileRoot, 1);
        GridPane.setRowSpan(tileRoot, 1);
        board.getChildren().add(tileRoot);
        return controller;
    }

    /**
     * Initializes an edge tile
     * @param row the row of the tile
     * @param column the column of the tile
     * @param rotation the rotation of the tile
     * @return the controller for the tile
     * @throws IOException if an error occurs while loading the FXML files
     * @author walshj05
     */
    private TileController initializeEdgeTile(int row, int column, int rotation) throws IOException {
        FXMLLoader tileLoader = new FXMLLoader(HelloApplication.class.getResource("EdgeTile.fxml"));
        Parent tileRoot = tileLoader.load();
        RegularGameTileController controller = tileLoader.getController();
        GridPane.setColumnIndex(tileRoot, column);
        GridPane.setRowIndex(tileRoot, row);
        GridPane.setColumnSpan(tileRoot, 1);
        GridPane.setRowSpan(tileRoot, 1);
        controller.rotatePane(rotation);
        board.getChildren().add(tileRoot);
        return controller;
    }

    /**
     * Initializes a property tile
     * @param row the row of the tile
     * @param column the column of the tile
     * @param rotation the rotation of the tile
     * @return the controller for the tile
     * @throws IOException if an error occurs while loading the FXML files
     * @author walshj05
     */
    private TileController initializePropertyTile(int row, int column, int rotation) throws IOException {
        FXMLLoader tileLoader = new FXMLLoader(HelloApplication.class.getResource("PropertyTile.fxml"));
        Parent tileRoot = tileLoader.load();
        PropertyTileController controller = tileLoader.getController();
        GridPane.setColumnIndex(tileRoot, column);
        GridPane.setRowIndex(tileRoot, row);
        GridPane.setColumnSpan(tileRoot, 1);
        GridPane.setRowSpan(tileRoot, 1);
        controller.rotatePane(rotation);
        board.getChildren().add(tileRoot);
        return tileLoader.getController();
    }

    /**
     * Updates the tokens on a tile
     * @param tokens the list of tokens to update
     * @param tileIndex the index of the tile to update
     * @author walshj05
     */
    public void updateTokens(ArrayList<Token> tokens, int tileIndex) {
        tileControllers[tileIndex].updateTokens(tokens);
    }
}
