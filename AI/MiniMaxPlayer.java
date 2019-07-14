/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AI;

import GameEngine.Controller;
import GameEngine.Player;
import GameEngine.Territory;
import RiskGame.Board;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar
 */
public class MiniMaxPlayer extends Player
{

    public MiniMaxPlayer(String name) throws CloneNotSupportedException
    {
        super(name);
    }

    @Override
    public boolean attack(Territory attackingTerritory, Territory defendingTerritory, int numberOfAttackers)
    {
        try
        {
            Search.getInstance().MiniMax(Controller.getInstance(null).getBoard(), 0, 50, 0, -999, 9999);
            return true;
        } catch (CloneNotSupportedException ex)
        {
            Logger.getLogger(MiniMaxPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //will choose territory to deploy new troops to randomly 
    @Override
    public void getDeploymentTerritory()
    {
       int numberOfTerritories = this.listOfTerritories.size();
       while(this.getAvailabelTroops()!=0)
       {
         int randomIndex =(int) (Math.random() * numberOfTerritories);
         listOfTerritories.get(randomIndex).setTroops( listOfTerritories.get(randomIndex).getTroops()+1);
         this.setAvailabelTroops(this.getAvailabelTroops()-1);
       }
      
       
    }

    @Override
    public void deployTroops(String territory)
    {
        
     }

    @Override
    public void movement(int troopsToMove)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
