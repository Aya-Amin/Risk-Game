/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RiskGame;

import AI.AStarPlayer;
import GameEngine.Controller;
import GameEngine.Player;
import GameEngine.Territory;
import java.util.ArrayList;

/**
 *
 * @author Omar
 */
public class Board
{
    private final int DEOPLOYMENT = 0;
    private final int CHOOSEATTACKER = 1;
    private final int CHOOSEDEFENDER = 2;
    private final int CHOOSETOMOVEFROM = 3;
    private final int CHOOSETOMOVETO = 4;
    //*****************************************************

    private static Controller controller;
    private int gamePhase;
    private Player currentPlayer;
    private ArrayList<Player> listOfPlayers;
    private ArrayList<Territory> territoriesList;

    public Board(ArrayList<Player> listOfPlayers,ArrayList<Territory> territoriesList, int turn) throws CloneNotSupportedException
    {
       this.territoriesList=new ArrayList<Territory>(territoriesList);
       this.listOfPlayers = new ArrayList<Player>();
       for(int i=0;i<listOfPlayers.size();i++)
            this.listOfPlayers.add((Player)listOfPlayers.get(i).clone());
       for(int i=0;i<listOfPlayers.size();i++)
            this.listOfPlayers.add((Player)listOfPlayers.get(i).clone());
       
       for(int i=0;i<territoriesList.size();i++)
            this.territoriesList.add((Territory)territoriesList.get(i).clone());
       
       this.currentPlayer = this.listOfPlayers.get(turn);
      // this.gamePhase = Integer.valueOf(gamePhase);
      
       
    
       
    }
       
       public ArrayList<Territory> getPossibleMoves()
    {
        
        ArrayList<Territory> possibleMovements = new ArrayList<>();
        switch(gamePhase)
        {  
        case DEOPLOYMENT:
        {
            possibleMovements = currentPlayer.getListOfTerritories();
        }
           
        case CHOOSEATTACKER:
            return currentPlayer.getListOfTerritories();
        case CHOOSEDEFENDER:
        {
            possibleMovements = currentPlayer.getAttackingTerritory().getAdjacentTerritoris();
            int size = currentPlayer.getAttackingTerritory().getAdjacentTerritoris().size();
            for(int i=0;i<size;i++)
                if(!currentPlayer.getAttackingTerritory().getAdjacentTerritoris().get(i).getOwner().getName().equals(currentPlayer.getName()))
                    possibleMovements.add(currentPlayer.getAttackingTerritory().getAdjacentTerritoris().get(i));
                    
        }
            return possibleMovements;
        
                
        }
        return null;
    }
    

    public ArrayList<Player> getListOfPlayers()
    {
        return listOfPlayers;
    }

    public void setListOfPlayers(ArrayList<Player> listOfPlayers)
    {
        this.listOfPlayers = listOfPlayers;
    }

    public ArrayList<Territory> getTerritoriesList()
    {
        return territoriesList;
    }

    public void setTerritoriesList(ArrayList<Territory> territoriesList)
    {
        this.territoriesList = territoriesList;
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    
    
}
