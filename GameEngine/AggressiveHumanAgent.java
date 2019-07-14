package GameEngine;

import java.util.ArrayList;
import java.util.Arrays;

public class AggressiveHumanAgent extends Player {

    ArrayList<Territory> attackingTerritoryNeighbours;

    public AggressiveHumanAgent(String name) {
        super(name);
    }

    @Override
    public boolean attack(Territory attackingTerritory, Territory defendingTerritory, int numberOfAttackers) {
        return false;
    }

    //get the list of owned territories and sort it, select the territory with max troops
    //and add the available troops (3 or more) to it
    public void getDeploymentTerritory() {
//		attackingTerritory = new Territory();
        //set the attacking territory to the territory with max troops
        ArrayList<Territory> sortedOwnedTerritories = this.getListOfTerritories();
        Arrays.sort((Territory[]) (sortedOwnedTerritories.toArray()), new SortByTroops());

        sortedOwnedTerritories.get(sortedOwnedTerritories.size() - 1).setTroops(sortedOwnedTerritories.get(sortedOwnedTerritories.size() - 1).getTroops() + this.getAvailabelTroops());
        this.chooseAttacker(sortedOwnedTerritories.get(sortedOwnedTerritories.size() - 1));
    }

    public void chooseAttacker(Territory attacker) {
        this.attackingTerritory = attacker;
        if(attackingTerritory.getTroops() > 1)
        this.chooseDefender();
    }

    public void chooseDefender() {

        attackingTerritoryNeighbours = new ArrayList<Territory>();
        attackingTerritoryNeighbours = attackingTerritory.getAdjacentTerritoris();
        //choose the adjacent territory with least number of troops and assign it to the defending territory
        //defendingTerritory ;
        Arrays.sort((Territory[]) (attackingTerritoryNeighbours.toArray()), new SortByTroops());
        int d = attackingTerritoryNeighbours.size() - 1;
        do {
            this.defendingTerritory = attackingTerritoryNeighbours.get(d);
            d--;
        } while (attackingTerritory.getTroops() < defendingTerritory.getTroops() && d >= 0);
        this.attack();

    }

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
