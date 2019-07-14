package GameEngine;

import RiskGame.Board;
import java.util.ArrayList;
import java.util.Set;

public class Controller
{

    //*****************************************************
    //PREDEFINES for the game State
    private final int DEOPLOYMENT = 0;
    private final int CHOOSEATTACKER = 1;
    private final int CHOOSEDEFENDER = 2;
    private final int CHOOSETOMOVEFROM = 3;
    private final int CHOOSETOMOVETO = 4;
    //*****************************************************

    private static Controller controller;
    private int boardState;
    private Player currentPlayer;
    private ArrayList<Player> listOfPlayers;
    private Bridge bridge;
    private Board board;
    private Map map;

    public static Controller getInstance(Map map) throws CloneNotSupportedException
    {
        if (controller == null)
        {
            return new Controller(map);
        } else
        {
            return controller;
        }
    }

    private Controller(Map map) throws CloneNotSupportedException
    {
        listOfPlayers = new ArrayList<Player>();
        controller = this;
        boardState = DEOPLOYMENT;
        bridge = Bridge.getInstance();
        this.map = map;
        board = new Board(listOfPlayers,map.getTerritories(),0);
    }

    //when user adds a new player, call "addPlayer"
    public void addPlayer(Player player)
    {
        listOfPlayers.add(player);
    }

    //call this function at the begining of game
    public void initialDeployment()
    {
        //randomize terrtitory index
        int randomTerritoryNumber;
        int deployedTerritories = 0, playerNum = 0;
        Territory currentTerritory;

        //it passes over the list of players and assigns a random territory to each one
        //from the list of territories in map object
        for (int i = 0; i < map.getTerritories().size(); i++)
        {
            //  randomTerritoryNumber = (int) (Math.random() *  map.getTerritories().size()) + 1;
            currentTerritory = map.getTerritories().get(i);

            //checks if the random index was already assigned to a player
            listOfPlayers.get(playerNum).addTerritory(currentTerritory);
            System.out.println("added A Territory " + currentTerritory.getTerritoryID() + " for " + listOfPlayers.get(playerNum).getName());
            currentTerritory.setOwner(listOfPlayers.get(playerNum));
            playerNum = (playerNum + 1) % (listOfPlayers.size());
            map.getTerritories().remove(currentTerritory);
        }

        /*after distributing all the territories over the players, we distribute the troops 
		  for each player (20/no of territories owned by each player)*/
        for (int i = 0; i < listOfPlayers.size(); i++)
        {

            ArrayList<Territory> currentTerritoryList;
            Player player = listOfPlayers.get(i);
            currentTerritoryList = player.getListOfTerritories();

            int avgTroopNum = (int) Math.floor(20 / currentTerritoryList.size());
            System.out.println("avg: " + avgTroopNum);
            for (int j = 0; j < currentTerritoryList.size(); j++)
            {
                currentTerritoryList.get(j).setTroops(avgTroopNum);
                listOfPlayers.get(i).setAvailabelTroops(listOfPlayers.get(i).getAvailabelTroops() - avgTroopNum);
            }

            //extra troops are assigned to a random territory
            int extra = 20 - avgTroopNum * currentTerritoryList.size();

            if (extra > 0)
            {
                int randomTerritoryNum = (int) (Math.random() * currentTerritoryList.size());
                currentTerritoryList.get(randomTerritoryNum).setTroops(currentTerritoryList.get(randomTerritoryNum).getTroops() + extra);
            }
        }
        currentPlayer = listOfPlayers.get(0);
        addingTroops();
    }

    public void nextPlayer()
    {
        // If the current player conquered 80%, in case of 2 players game, return null to GUI to indicate the winning of the current player.
        //  if ( currentPlayer.getListOfTerritories().size() >= 0.8*map.getTerritories().size() && listOfPlayers.size() == 2) 
        //	  endGame(currentPlayer);

        // if ( currentPlayer.getListOfTerritories().size() >= 0.5*map.getTerritories().size() && listOfPlayers.size() >= 3) 
        //	  endGame(currentPlayer);
        int currentPlayerIndex = listOfPlayers.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % listOfPlayers.size();

        currentPlayer = listOfPlayers.get(nextPlayerIndex);
        System.out.println("Player: " + currentPlayer.getName());
        //After choosing the next player, new troops are added to his list of troops
        addingTroops();

    }

    // After turn has returned, call deployment from GUI passing to it the current player.
    //Bridge function
    public void addingTroops()
    {
        int troops = currentPlayer.getListOfTerritories().size() / 3;
        if (troops < 3)
        {
            troops = 3;
        }
        currentPlayer.setAvailabelTroops(currentPlayer.getAvailabelTroops() + troops);

        System.out.println("Added new " + troops + " Troops for " + currentPlayer.getName() + " \n select where to Deploy >>");
        //Now the player shall select territories from the Map to add the troops
        currentPlayer.getDeploymentTerritory();
        
        boardState = DEOPLOYMENT;
    }

    //the Bridge will recive a territory from the Map and call this function to add a troop to this territory
    //Bridge function
    public void deployNewTroop(String territory)
    {
//        
        currentPlayer.deployTroops(territory);
        
    }
//        int currentTerritoryTroops = currentPlayer.getTerritoryByID(territory).getTroops();
//
//        if (currentPlayer.getAvailabelTroops() > 0)
//        {
//            currentPlayer.getTerritoryByID(territory).setTroops(currentTerritoryTroops + 1);
//            currentPlayer.setAvailabelTroops(currentPlayer.getAvailabelTroops() - 1);
//            System.out.println("New troops are added for " + territory);
//            System.out.println("Troops left = " + currentPlayer.getAvailabelTroops());
//        }
//        
//        //No more troops to deploy, then attacking phase
//        if (currentPlayer.getAvailabelTroops() == 0)
//        {
//            chooseAttacker();
//        }
//
//    }

    //Bridge function
    public void chooseAttacker()
    {

        System.out.println("Attacking Phase, choose a territory to attack with");
        //For the Map to Enable the territories to be clickable
        bridge.enableTerritories(currentPlayer.getListOfTerritories());
        boardState = CHOOSEATTACKER;

    }

    //player Function (Bridge will call this function)
    public void setAttacker(String territory)
    {
        currentPlayer.setAttackingTerritory(territory);
        chooseDefender(territory);

    }

    //Bridge function
    private void chooseDefender(String territory)
    {
        // String[] adjacentIds = currentPlayer.getAdjacentTerritoriesIds(territory);

        System.out.println("Choose a territory to Attack");
        bridge.enableTerritories(currentPlayer.getAdjacentTerritoriesIds(territory));
        boardState = CHOOSEDEFENDER;
    }

    
    public void enableTerritories(ArrayList<Territory> listOfTerritories)
    {
        bridge.enableTerritories(listOfTerritories);
    }
    //player Function (Bridge will call this function)
    public void setDefender(String territory)
    {
        currentPlayer.setDefendingTerritory(territory);

        nextPlayer();

    }

    public int getBoardState()
    {
        return boardState;
    }

    public void setBoardState(int boardState)
    {
        this.boardState = boardState;
    }

    public void setListOfPlayers(ArrayList<Player> listOfPlayers)
    {
        this.listOfPlayers = listOfPlayers;
    }

    public Map getMap()
    {
        return map;
    }

    public Board getBoard()
    {
        return board;
    }

}
