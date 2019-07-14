/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import GameEngine.Player;
import GameEngine.Territory;
import java.util.ArrayList;

/**
 *
 * @author Omar
 */


public class Node
{
 //*****************************************************
    //PREDEFINES for the game State
    private final int DEOPLOYMENT = 0;
    private final int CHOOSEATTACKER = 1;
    private final int CHOOSEDEFENDER = 2;
    private final int CHOOSETOMOVEFROM = 3;
    private final int CHOOSETOMOVETO = 4;
    //*****************************************************
    
    
   private ArrayList<Player> players;
   private int pathCost;
    private int gamePhase;
   private float heuristicBSR;
   private Territory targetTerritory;
   private AStarPlayer player;
   private Node Parent;
   
    public void setPlayer(AStarPlayer player)
    {
        this.player = player;
    }
   
    public Node(int gamePhase,Territory territory,int cost, Node parentNode,float heuristic)
    {
       
        this.gamePhase = gamePhase;
        this.pathCost = cost;
        this.Parent = parentNode;
        this.heuristicBSR=heuristic;
        this.targetTerritory = territory;
     
    }

    public int getPathCost()
    {
        return pathCost;
    }

  
    public int getGamePhase()
    {
        return gamePhase;
    }

    public Node getParent()
    {
        return Parent;
    }

  
    public float getHeuristicBSR()
    {
        return heuristicBSR+pathCost;
    }

    public void setHeuristicBSR(float heuristicBSR)
    {
        this.heuristicBSR = heuristicBSR;
    }

    
    public int getBoardState()
    {
        return gamePhase;
    }

    public void setBoardState(int boardState)
    {
        this.gamePhase = gamePhase;
    }

    public Territory getTargetTerritory()
    {
        return targetTerritory;
    }

    public void setTargetTerritory(Territory targetTerritory)
    {
        this.targetTerritory = targetTerritory;
    }
   
     public AStarPlayer getPlayer()
    {
        return player;
    }

     
}
