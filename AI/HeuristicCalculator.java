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
public class HeuristicCalculator
{

    private static HeuristicCalculator heuristicCalculator;
    
    private HeuristicCalculator()
    {
    }
    
    public static HeuristicCalculator getInstance()
    {
        if(heuristicCalculator == null)
            return new HeuristicCalculator();
        else
            return heuristicCalculator;
    }
    
    
    
    
    
    /*
    Function to calculate Score for each player 
    It takes into ocnsideration 
    1-Number of owned territories
    2-Number of adjacent owned territories
    3-The average number of troope in each territory
    */
    public int getPlayerScore(Player player)
    {
        int playerScore =0;
        int totalTroops=0;
        ArrayList<Territory> territoriesList = player.getListOfTerritories();
        ArrayList<Territory> adjacentTerritories;
        playerScore+=territoriesList.size()*3;
        
        for(int i=0;i<territoriesList.size();i++)
        {
            totalTroops += territoriesList.get(i).getTroops();
            adjacentTerritories = territoriesList.get(i).getAdjacentTerritoris();
            for(int j=0;j<adjacentTerritories.size();j++)
            {
                if(adjacentTerritories.get(j).getOwner().equals(player))
                    playerScore+=1;
            }
        }
        
        playerScore += totalTroops / territoriesList.size();
        
        return playerScore;
                
        
    }
    
    
    public int getdeploymentHeuristic(Player player,Territory territory)
    {
        int score =0;
        score += player.getTotalTroops()/territory.getTroops();
        score +=territory.getTroops() *0.5;
        ArrayList<Territory> adjacentTerritories = territory.getAdjacentTerritoris();
        for(int i=0;i<adjacentTerritories.size();i++)
        {
            if(adjacentTerritories.get(i).getOwner()==player)
            {
                score +=5;
            } 
            else
                if(adjacentTerritories.get(i).getTroops()<3)
                    score +=7;
            else
                score += getPlayerScore(adjacentTerritories.get(i).getOwner())*0.5;
        }
        
               
        return score;
        
        
    }
    
    public int getAttackingHeuristic(Player player,Territory territory)
    {
        int score =0;
        score += territory.getTroops();
         ArrayList<Territory> adjacentTerritories = territory.getAdjacentTerritoris();
        for(int i=0;i<adjacentTerritories.size();i++)
        {
            if(adjacentTerritories.get(i).getOwner()==player)
            {
                score +=5;
            } 
            else
                if(adjacentTerritories.get(i).getTroops()<3)
                    score +=8;
            else
                score -= getPlayerScore(adjacentTerritories.get(i).getOwner())*0.5;
        }
        return score;
    }
    
    
    public float getNBSRHeuristic(Player player, Territory territory)
    {
        float score;
        int BST =0;
        ArrayList<Territory> adjacentTerritories = territory.getAdjacentTerritoris();
        ArrayList<Territory> ownedTerritories = player.getListOfTerritories();
        for(int i=0;i<adjacentTerritories.size();i++)
        {   if(adjacentTerritories.get(i).getOwner()!=territory.getOwner())
            BST+=adjacentTerritories.get(i).getTroops();
        }
         float BSR;
       
         BSR = BST / territory.getTroops();
        int adjBST=0,sum=0;
        if(BSR > 3)
            for(Territory territoryy: ownedTerritories)
            {
                adjacentTerritories = territory.getAdjacentTerritoris();
                for(Territory adjacentTerritory: adjacentTerritories)
                    if(adjacentTerritory.getOwner()!=territoryy.getOwner())
                        adjBST+=adjacentTerritory.getTroops();
                sum+=adjBST/territoryy.getTroops();
                    
                    
       
            }
        else
            BSR=0;
        
        float NBSR = BSR / sum;
        
        return NBSR;
        
    }
}
