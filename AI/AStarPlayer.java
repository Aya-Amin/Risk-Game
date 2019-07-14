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
public class AStarPlayer extends Player
{
 //PREDEFINES for the game State
    private final int DEOPLOYMENT = 0;
    private final int CHOOSEATTACKER = 1;
    private final int CHOOSEDEFENDER = 2;
    private final int CHOOSETOMOVEFROM = 3;
    private final int CHOOSETOMOVETO = 4;
    //==========================================
    
    
    private int gamePhase=0;
    private Search search;
    

  
    public AStarPlayer(String name)
    {
        super(name);
        search = Search.getInstance();
    }

     public AStarPlayer(AStarPlayer player)
    {
        
        super(player.getName());
        this.setListOfTerritories(new ArrayList<>(player.getListOfTerritories()));
        this.setAvailabelTroops(player.getAvailabelTroops());
//        player.setAttackingTerritory(new String(this.getAttackingTerritory().getTerritoryID()));
  //      player.setDefendingTerritory(new String(this.defendingTerritory.getTerritoryID()));
        
    }

     public int getGameState()
    {
        return gamePhase;
    }

    public void setGameState(int gamePhase)
    {
        this.gamePhase = gamePhase;
    }
    
    public void selectAttackingTerritory()
    {
        
    }
     
    @Override
    public boolean attack(Territory attackingTerritory, Territory defendingTerritory, int numberOfAttackers)
    {
        
    }

    @Override
    public void getDeploymentTerritory()
    {
        Territory deploymentTerritory = search.getBestDeployment(this);
        deployTroops(deploymentTerritory.getTerritoryID());
    }

    @Override
    public void deployTroops(String territory)
    {
        this.getTerritoryByID(territory).setTroops(this.getTerritoryByID(territory).getTroops()+1);
        this.setAvailabelTroops(this.getAvailabelTroops()-1);
        if(this.getAvailabelTroops() ==0)
            this.setGameState(CHOOSEATTACKER);
        
    }

    @Override
    public void movement(int troopsToMove)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void play(Territory territory)
    {
        switch(gamePhase)
        {
            case DEOPLOYMENT:
                this.deployTroops(territory.getTerritoryID());
                break;
            case CHOOSEATTACKER:
                this.setAttackingTerritory(territory.getTerritoryID());
                this.setGameState(CHOOSEDEFENDER);
                break;
            case CHOOSEDEFENDER:
                this.setDefendingTerritory(territory.getTerritoryID());
                this.getListOfTerritories().add(territory);
                territory.setOwner(this);
                this.setGameState(DEOPLOYMENT);
                
                break;
        }
    }
    
    
    
}
