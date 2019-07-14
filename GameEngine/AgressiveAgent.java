/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEngine;

import java.util.Arrays;

/**
 *
 * @author Omar
 */
public class AgressiveAgent extends Player
{

    public AgressiveAgent(String name)
    {
        super(name);
    }

    @Override
    public boolean attack(Territory attackingTerritory, Territory defendingTerritory, int numberOfAttackers)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getDeploymentTerritory()
    {
        
      Arrays.sort((Territory[])(this.listOfTerritories.toArray()));  
      
        
    }

    @Override
    public void deployTroops(String territory)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void movement(int troopsToMove)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
