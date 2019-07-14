package GameEngine;

import java.util.ArrayList;
import java.util.Arrays;

public class PacifistHumanAgent extends Player {

    ArrayList<Territory> allNeighbours;

    public PacifistHumanAgent(String name) {
        super(name);
    }

    @Override
    public boolean attack(Territory attackingTerritory, Territory defendingTerritory, int numberOfAttackers) {
        // TODO Auto-generated method stub
        return false;
    }
    
    private Territory[] toArray(ArrayList<Territory> list)
    {
        Territory[] territoy = new Territory[list.size()];
        
        for(int i=0;i<list.size();i++)
            territoy[i] = list.get(i);
        
        return territoy;
    }

    /*sort list of territories owned by player and place the available troops(3 or more)
	 in the territory with least no of troops*/
    @Override
    public void getDeploymentTerritory() {
        ArrayList<Territory> sortedOwnedTerritories = this.getListOfTerritories();
        Territory[] territoriesArray = toArray(sortedOwnedTerritories);
        Arrays.sort(territoriesArray, new SortByTroops());
        Territory deployTo = territoriesArray[0];
        System.out.println("no-AI Agend Deployed a troop to "+territoriesArray[0].getTerritoryID() );

        if (this.getAvailabelTroops() > 0) {
            deployTo.setTroops(deployTo.getTroops() + this.getAvailabelTroops());
            this.setAvailabelTroops(0);
            System.out.println("New troops are added for " + deployTo.getTerritoryID());
            System.out.println("Troops left = " + this.getAvailabelTroops());
        }

        //No more troops to deploy, then attacking phase
        if (this.getAvailabelTroops() == 0) {
            this.chooseDefender();
        }
    }

    public void chooseDefender() {
        
        allNeighbours = new ArrayList<Territory>();
          //list of all the adjacent allNeighbours to all of the territories owned by current player
          int count = this.getListOfTerritories().size();
        //create list of owned territories
        for (int i = 0; i < count; i++) {
            Territory ownedTerritory;
            ownedTerritory = this.getListOfTerritories().get(i);
            int size = ownedTerritory.getAdjacentTerritoris().size();
            //create list of all adjacent territories
            for (int j = 0; j < size; j++) {
                allNeighbours.add(ownedTerritory.getAdjacentTerritoris().get(j));
            }
        }

        //sort allNeighbours according to no of troops
        ArrayList<Territory> sortedNeighbours = this.allNeighbours;
        Territory[] territoryArray = toArray(sortedNeighbours);
        Arrays.sort( territoryArray, new SortByTroops());

        System.out.println("Pacifist Agent choosed to attack "+ territoryArray[0].getTerritoryID());
        int d = 0; //index of the sorted neighbour array
        do {
            defendingTerritory = territoryArray[d];
            d++;
            
            //choose the attacking territory with max troops from the adjacent owned territories to the chosen defending territory
            this.chooseAttacker();
            
        } while (attackingTerritory.getTroops() <= 1 || defendingTerritory.getTroops() > 0); //go to the next least defending territory if the attacking territory 
        //has one troop only
        this.attack();
    }

    public void chooseAttacker() {
        
        //choose the attacking territory with max troops from the adjacent owned territories to the chosen defending territory
        //get the first attacking territory
        //if more than one attacking territory were found choose the one with max troops

        ArrayList<Territory> defenderNeighbours = new ArrayList<>();
        int size = defendingTerritory.getAdjacentTerritoris().size();
        System.out.println("size: "+size);
        for (int i = 0; i < size; i++) {
            if (defendingTerritory.getAdjacentTerritoris().get(i).getOwner() == this) {
                defenderNeighbours.add(defendingTerritory.getAdjacentTerritoris().get(i));
            }
        }
        
        Territory[] territoryArray = toArray(defenderNeighbours);
        Arrays.sort(territoryArray, new SortByTroops());
        System.out.println("Array of length "+ territoryArray.length+" AyyarList of length "+ defenderNeighbours.size());
      
        attackingTerritory = territoryArray[territoryArray.length -1];    
    }

// public void sortAdjacentList
public void attack() {
    
    int numberOfAttackers = 0;

        while (true) {
            // check that at least one troop will remain in the attacking territory
            if (attackingTerritory.getTroops() >= 4) {
                numberOfAttackers = 3;
            } else if (attackingTerritory.getTroops() > 1) {
                numberOfAttackers = attackingTerritory.getTroops() - 1;
            }

            int[] attackersDice = new int[numberOfAttackers]; // user enters number of attacking troops

            int numberOfDefenders = defendingTerritory.getTroops();
            // check that defender will defend with max 2 troops only
            if (numberOfDefenders >= 2) {
                numberOfDefenders = 2;
            }
            int[] defendersDice = new int[numberOfDefenders];

            for (int i = 0; i < numberOfAttackers; i++) {
                attackersDice[i] = (int) (Math.random() * 6) + 1;
            }
            for (int i = 0; i < numberOfDefenders; i++) {
                defendersDice[i] = (int) (Math.random() * 6) + 1;
            }

            Arrays.sort(attackersDice);
            Arrays.sort(defendersDice);

            if (defendersDice[0] < attackersDice[0]) {
                //remove lost troops from defending territory
                defendingTerritory.setTroops(defendingTerritory.getTroops() - numberOfDefenders);
                if (defendingTerritory.getTroops() == 0) {
                    this.movement(numberOfAttackers);
                }
            } else {
                //remove lost troops from attacking territory
                attackingTerritory.setTroops(attackingTerritory.getTroops() - numberOfAttackers);
            }
}
        }
  
    @Override
    public void movement(int numOfAttackers) {

        //if there are no troops left, add attacking troops and 
        //update the owner and the list of territories for both attacker and defender
        defendingTerritory.setTroops(numOfAttackers);
        defendingTerritory.getOwner().removeTerritory(defendingTerritory);
        attackingTerritory.getOwner().addTerritory(defendingTerritory);
        defendingTerritory.setOwner(attackingTerritory.getOwner());
        attackingTerritory.setTroops(attackingTerritory.getTroops() - numOfAttackers);
        
        System.out.println("Movement done");
    }

    @Override
    public void deployTroops(String territory)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
