package GameEngine;

import java.util.ArrayList;
import java.util.Arrays;

public class PassiveHumanAgent extends Player {

    public PassiveHumanAgent(String name) {
        super(name);
    }

    @Override
    public boolean attack(Territory attackingTerritory, Territory defendingTerritory, int numberOfAttackers) {
        // TODO Auto-generated method stub
        return false;
    }

    //add the available troops(3 or more) to the territory with least number of troops, and no attack
    public void getDeploymentTerritory() {
        
        ArrayList<Territory> sortedOwnedTerritories = this.getListOfTerritories();
        Arrays.sort((Territory[]) (sortedOwnedTerritories.toArray()), new SortByTroops());
        sortedOwnedTerritories.get(0).setTroops(sortedOwnedTerritories.get(0).getTroops() + this.getAvailabelTroops());
        // call bridge?
    }
    
}
