package GameEngine;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HumanAgent extends Player {

    public HumanAgent(String name) throws CloneNotSupportedException {
        super(name);
    }

    public void deployTroops(String territory) {

        int currentTerritoryTroops = this.getTerritoryByID(territory).getTroops();

        if (this.getAvailabelTroops() > 0) {
            this.getTerritoryByID(territory).setTroops(currentTerritoryTroops + 1);
            this.setAvailabelTroops(this.getAvailabelTroops() - 1);
            System.out.println("New troops are added for " + territory);
            System.out.println("Troops left = " + this.getAvailabelTroops());
        }
        //No more troops to deploy, then attacking phase
        if (this.getAvailabelTroops() == 0) {
            try
            {
                Controller.getInstance(null).chooseAttacker();
            } catch (CloneNotSupportedException ex)
            {
                Logger.getLogger(HumanAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void getDeploymentTerritory() {
        controller.enableTerritories(this.listOfTerritories);
    }

    @Override
    public boolean attack(Territory attackingTerritory, Territory defendingTerritory, int numberOfAttackers) {

        //check that at least one troop will remain in the attacking territory
        if (attackingTerritory.getTroops() - numberOfAttackers >= 1) {
            int[] attackers = new int[numberOfAttackers]; //user enters number of attacking troops
            int numberOfDefenders = defendingTerritory.getTroops();

            //check that defender will defend with max 2 troops only
            if (numberOfDefenders >= 2) {
                numberOfDefenders = 2;
            }

            int[] defenders = new int[numberOfDefenders];

            for (int i = 0; i < numberOfAttackers; i++) {
                attackers[i] = (int) (Math.random() * 6) + 1;
            }
            for (int i = 0; i < numberOfDefenders; i++) {
                defenders[i] = (int) (Math.random() * 6) + 1;
            }

            Arrays.sort(attackers);
            Arrays.sort(defenders);

            if (defenders[0] < attackers[0]) {

                //remove lost troops from defending territory
                defendingTerritory.setTroops(defendingTerritory.getTroops() - numberOfDefenders);
                if (defendingTerritory.getTroops() == 0) {
                    //if there are no troops left, add attacking troops and 
                    //update the owner and the list of territories for both attacker and defender
                    defendingTerritory.setTroops(numberOfAttackers);
                    defendingTerritory.getOwner().removeTerritory(defendingTerritory);
                    attackingTerritory.getOwner().addTerritory(defendingTerritory);
                    defendingTerritory.setOwner(attackingTerritory.getOwner());
                    attackingTerritory.setTroops(attackingTerritory.getTroops() - numberOfAttackers);
                }
            } else {
                //remove lost troops from attacking territory
                attackingTerritory.setTroops(attackingTerritory.getTroops() - numberOfAttackers);
            }
            return true;
        }
        return false;
    }

    @Override
    public void movement(int troopsToMove) {
        Territory fromTerritory = null;
        Territory toTerritory = null;

        for (Territory t : listOfTerritories) {
            if (t.getTerritoryID().equalsIgnoreCase(fromId)) {
                fromTerritory = t;
            } else if (t.getTerritoryID().equalsIgnoreCase(toId)) {
                toTerritory = t;
            }
        }

        if (fromTerritory == null || toTerritory == null) //invalid move    
        {
        } else {
            fromTerritory.setTroops(fromTerritory.getTroops() - troopsToMove);
            toTerritory.setTroops(toTerritory.getTroops() + troopsToMove);
        }
    }

}
