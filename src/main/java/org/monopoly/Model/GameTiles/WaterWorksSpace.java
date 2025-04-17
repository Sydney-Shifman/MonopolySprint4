package org.monopoly.Model.GameTiles;

import org.monopoly.Exceptions.BankruptcyException;
import org.monopoly.Exceptions.InsufficientFundsException;
import org.monopoly.Model.Banker;
import org.monopoly.Model.Cards.ColorGroup;
import org.monopoly.Model.Dice;
import org.monopoly.Model.Players.ComputerPlayer;
import org.monopoly.Model.Players.HumanPlayer;
import org.monopoly.Model.Players.Player;
import org.monopoly.Model.TurnManager;

import java.util.ArrayList;

/**
 * Represents the Water Works Space element on the Game Board's Tiles.
 *
 * @author shifmans
 */
public class WaterWorksSpace extends GameTile {
    private int price;
    private ArrayList<Integer> rentPriceMultiplier;
    private ColorGroup colorGroup;
    private int mortgageValue;
    private int unmortgageValue;
    private boolean isMortgaged;
    private String owner;

    /**
     * Constructor to initialize a WaterWorksSpace with all information.
     * @param actions Actions for a WaterWorksSpace.
     * @param price Price for a WaterWorksSpace.
     * @param rentPriceMultiplier Rent Price Multiplier for a WaterWorksSpace.
     * @param colorGroup Color Group for a WaterWorksSpace.
     * @param mortgageValue Mortgage Value for a WaterWorksSpace.
     *
     * Developed by: shifmans
     */
    public WaterWorksSpace(String actions, int price, ArrayList<Integer> rentPriceMultiplier, ColorGroup colorGroup, int mortgageValue) {
        super("Water Works", actions);
        this.price = price;
        this.rentPriceMultiplier = rentPriceMultiplier;
        this.colorGroup = colorGroup;
        this.mortgageValue = mortgageValue;
        this.unmortgageValue = mortgageValue + (int) (mortgageValue * 0.1); //Mortgage value plus 10% interest
        this.isMortgaged = false;
        this.owner = "";
    }

    /**
     * Shows the actions that occur after a player lands on element space.
     * @return Displays the actions for landing on element space.
     *
     * Developed by: shifmans
     */
    @Override
    public String landOn() {
        actions += displayWaterWorksInfo();
        return actions;
    }

    /**
     * Player is shown information about Water Works after landing on element space.
     * @return Information on Water Works Space.
     *
     * Developed by: shifmans
     */
    private String displayWaterWorksInfo() {
        return "Property Name: " + getName() + "\n" +
                "Color Set: " + getColorGroup() + "\n" +
                "Purchase Price: $" + getPrice() + "\n" +
                "Rent (without houses/hotels): Depends on dice roll\n" +
                "If you own 1 Utility: Rent is 4 times the amount rolled on the dice.\n" +
                "If you own 2 Utilities: Rent is 10 times the amount rolled on the dice.\n" +
                "Mortgage Value: $" + getMortgageValue();
    }

    /**
     * Gets the price of a WaterWorksSpace.
     * @return The price of a WaterWorksSpace.
     *
     * Developed by: shifmans
     */
    public int getPrice() {
        return price;
    }

    /**
     * Calculates the rent price based on the number of utilities owned and the dice roll.
     * @param numUtilitiesOwned The number of utility properties owned by the player.
     * @return The rent price based on the number of utilities owned and the dice roll.
     *
     * Developed by: shifmans
     */
    @Override
    public int getRentPrice(int numUtilitiesOwned) {
        Dice dice = new Dice();
        int[] diceRoll = dice.roll();


        if (numUtilitiesOwned == 1) { // If the player owns 1 utility
            return (diceRoll[0] + diceRoll[1]) * getRentPriceMultiplier().get(0);
        }


        return (diceRoll[0] + diceRoll[1]) * getRentPriceMultiplier().get(1);
    }

    /**
     * Gets the rent prices of a WaterWorksSpace.
     * @return The list of rent prices of a WaterWorksSpace.
     *
     * Developed by: shifmans
     */
    private ArrayList<Integer> getRentPriceMultiplier() {
        return rentPriceMultiplier;
    }

    /**
     * Gets the color group of a WaterWorksSpace.
     * @return The color group of a WaterWorksSpace.
     *
     * Developed by: shifmans
     */
    public ColorGroup getColorGroup() {
        return colorGroup;
    }

    /**
     * Gets the mortgage value of a WaterWorksSpace.
     * @return The mortgage value of a WaterWorksSpace.
     *
     * Developed by: shifmans
     */
    public int getMortgageValue() {
        return mortgageValue;
    }

    /**
     * Gets the unmortgage value of a WaterWorksSpace.
     * @return The unmortgage value of a WaterWorksSpace.
     */
    public int getUnmortgageValue() {
        return unmortgageValue;
    }

    /**
     * Gets the mortgaged status of a WaterWorksSpace.
     * @return The mortgaged status of a WaterWorksSpace.
     *
     * Developed by: shifmans
     */
    public boolean isMortgaged() {
        return isMortgaged;
    }

    /**
     * Sets the mortgaged status of a WaterWorksSpace.
     * @param isMortgaged The mortgaged status of a WaterWorksSpace.
     *
     * Developed by: shifmans
     */
    public void setMortgagedStatus(boolean isMortgaged) {
        this.isMortgaged = isMortgaged;
    }

    /**
     * Gets the owner of a WaterWorksSpace.
     * @return The owner of a WaterWorksSpace.
     *
     * Developed by: shifmans
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of a WaterWorksSpace.
     * @param owner The owner of a WaterWorksSpace.
     *
     * Developed by: shifmans
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Executes the strategy for the WaterWorksSpace.
     * @author crevelings
     * Modified by: crevelings (4/8/25), (4/9/25)
     * Modified by: walshj05 (4/17/25)
     * 4/8/25: Configured for CPU
     * 4/9/25: Added full implementation for strategy
     * 4/17/25: Refactored method for more general usage
     */
    @Override
    public void executeStrategy(Player player) {
        WaterWorksSpace.utilityStrategy(this, player, isMortgaged, rentPriceMultiplier.size());
    }

    public static void utilityStrategy(GameTile utility, Player player, boolean isMortgaged, int rentPriceMultiplier) {
        if (PropertySpace.doNothingCondition(player, isMortgaged, utility.getName())) { // mortgaged or you own it
            return;
        }
        if (utility.getOwner().isEmpty()) { // no one owns it
            try {
                player.purchaseProperty(utility.getName(), utility.getPrice());
            } catch (InsufficientFundsException e) {
                throw new RuntimeException(e);
                //todo go to auction or add try catch to push to auction on insufficient funds
            }
        } else {
            int rent = utility.getRentPrice(rentPriceMultiplier);
            player.subtractFromBalance(rent);
        }
    }
}
