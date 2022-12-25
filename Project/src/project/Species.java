/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Moha c
 */
public class Species {
    
    SimpleStringProperty speciesName;
    SimpleStringProperty betaChain;
    
    Species(String speciesName, String betaChain){
        this.speciesName = new SimpleStringProperty(speciesName);
        this.betaChain = new SimpleStringProperty(betaChain);
    }
    
    public void setSpeciesName(String speciesName) {
        this.speciesName = new SimpleStringProperty(speciesName);
    }

    public void setBetaChain(String betaChain) {
        this.betaChain = new SimpleStringProperty(betaChain);
    }

    public String getSpeciesName() {
        return speciesName.get();
    }

    public String getBetaChain() {
        return betaChain.get();
    }
    
}
