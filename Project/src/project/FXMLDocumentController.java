/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 *
 * @author Moha c
 */
public class FXMLDocumentController implements Initializable {

    //===========================================================================
    @FXML
    private Label Close;
    @FXML
    private Label Minimize;
    //===========================================================================
    @FXML
    private AnchorPane Headder1;
    @FXML
    private TextField speciesName;
    @FXML
    private TextField betaChain;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button calculateResults;
    @FXML
    private TableView<Species> speciesTable;
    @FXML
    private Label errors;
    @FXML
    private TableColumn<Species, String> speciesNameColumn;
    @FXML
    private TableColumn<Species, String> betaChainColumn;
    Stack<Species> species = new Stack<>();
    ObservableList<Species> speciesRecords = FXCollections.observableArrayList();
    int size;
    //===========================================================================
    //===========================================================================
    //Header Page
    @FXML
    private void close() {
        Stage stage = (Stage) Close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimize() {
        Stage stage = (Stage) Minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void showInput() {
        if (landPage.isVisible()) {
            landPage.setVisible(false);
        }
        if (!inputPage.isVisible()) {
            inputPage.setVisible(true);
        }
        if (!Headder1.isVisible()) {
            Headder1.setVisible(true);
        }
    }
    @FXML
    private void showBackToCalculationPage() {
        if (treePage.isVisible()) {
            treePage.setVisible(false);
        }
        if (!resultsPage.isVisible()) {
            resultsPage.setVisible(true);
        }
    }
    @FXML
    private void reset() {
        if (!landPage.isVisible()) {
            landPage.setVisible(true);
        }
        if (inputPage.isVisible()) {
            inputPage.setVisible(false);
        }
        if (Headder1.isVisible()) {
            Headder1.setVisible(false);
        }
        if (resultsPage.isVisible()) {
            resultsPage.setVisible(false);
        }
        if (treePage.isVisible()) {
            treePage.setVisible(false);
        }
        
        species = new Stack<>();
        speciesRecords = FXCollections.observableArrayList();
        speciesTable.setItems(speciesRecords);
    }
    //===========================================================================
    //First Page
    @FXML
    private void addToStack() {
        if (!checkForAdd()) {
            return;
        }
        species.add(new Species(speciesName.getText(), betaChain.getText()));
        if (species.size() > 3) {
            errors.setText("Error: size must be 3");
            species.pop();
            return;
        }
        speciesRecords.add(species.peek());
        speciesTable.setItems(speciesRecords);
        speciesName.setText("");
        betaChain.setText("");
    }

    @FXML
    private void deleteFromStack() {
        if (!checkForDelete()) {
            return;
        }
        speciesRecords.remove(species.peek());
        speciesTable.setItems(speciesRecords);
        species.pop();
        speciesName.setText("");
        betaChain.setText("");
    }

    private boolean checkForDelete() {
        if (species.empty()) {
            errors.setText("Error: no entries found to delete");
            return false;
        }
        errors.setText("");
        return true;
    }

    private boolean checkForAdd() {
        if (speciesName.getText().isEmpty() && betaChain.getText().isEmpty()) {
            errors.setText("Error: species name and beta chain must be entered");
            return false;
        } else if (speciesName.getText().isEmpty()) {
            errors.setText("Error: species name must be entered");
            return false;
        } else if (betaChain.getText().isEmpty()) {
            errors.setText("Error: beta chain must be entered");
            return false;
        }
        
        if(species.size() > 0){
            if(betaChain.getText().length() != size){
                errors.setText("Error: error size must be as first entry ("+ size +")");
                return false;
            }
        }else{
            size = betaChain.getText().length();
        }
        
        errors.setText("");
        return true;
    }

    //===========================================================================
    //Second Page
    @FXML
    private Label betaChain1;
    @FXML
    private Label betaChain2;
    @FXML
    private Label betaChain3;
    @FXML
    private Label betaChain11;
    @FXML
    private Label betaChain22;
    @FXML
    private Label betaChain33;
    @FXML
    private Label BetaChain12;
    @FXML
    private Label BetaChain21;
    @FXML
    private Label BetaChain23;
    @FXML
    private Label BetaChain32;
    @FXML
    private Label BetaChain31;
    @FXML
    private Label BetaChain13;
    @FXML
    private Label percent;
    @FXML
    private Label percent1;
    @FXML
    private Label percent2;
    @FXML
    private AnchorPane inputPage;
    @FXML
    private AnchorPane resultsPage;
    @FXML
    private AnchorPane landPage;
    
    @FXML
    private void calculateResultsMethod() {
        if (inputPage.isVisible()) {
            inputPage.setVisible(false);
        }
        if (!resultsPage.isVisible()) {
            resultsPage.setVisible(true);
        }

        String[] names = new String[3];
        String[] betaChains = new String[3];

        int index = 0;
        for (Species entitiy : species) {
            names[index] = entitiy.getSpeciesName();
            betaChains[index] = entitiy.getBetaChain();
            index++;
        }
        
        String[] betaChainsResults12 = calculateDef(betaChains[0], betaChains[1]);
        String[] betaChainsResults31 = calculateDef(betaChains[0], betaChains[2]);
        String[] betaChainsResults23 = calculateDef(betaChains[1], betaChains[2]);

        String[] betaChainsdisplay = new String[]{
            betaChainsResults12[0],
            betaChainsResults12[1],
            betaChainsResults31[0],
            betaChainsResults31[1],
            betaChainsResults23[0],
            betaChainsResults23[1],};

        String[] def = new String[]{
            betaChainsResults12[3] + "% • " + betaChainsResults12[2],
            betaChainsResults31[3] + "% • " + betaChainsResults31[2],
            betaChainsResults23[3] + "% • " + betaChainsResults23[2],};

        double[] percent = new double[]{Double.parseDouble(betaChainsResults12[3]), Double.parseDouble(betaChainsResults31[3]), Double.parseDouble(betaChainsResults23[3])};
        double defPercent1 = 0;
        double defPercent2 = 0;
        if ((percent[0] > percent[1]) && (percent[0] > percent[2])) {
            //betaChainsResults12;
            firstSpecies.setText(names[0]);
            secondSpecies.setText(names[1]);
            thirdSpecies.setText(names[2]);
            defPercent1 = 100 - percent[0];
            percent = new double[]{Double.parseDouble(betaChainsResults31[3]), Double.parseDouble(betaChainsResults23[3])};
        } else if ((percent[1] > percent[0]) && (percent[1] > percent[2])) {
            //betaChainsResults31;
            firstSpecies.setText(names[0]);
            secondSpecies.setText(names[2]);
            thirdSpecies.setText(names[1]);
            defPercent1 = 100 - percent[1];
            percent = new double[]{Double.parseDouble(betaChainsResults12[3]), Double.parseDouble(betaChainsResults23[3])};
        } else if ((percent[2] > percent[0]) && (percent[2] > percent[1])) {
            //betaChainsResults23;
            firstSpecies.setText(names[1]);
            secondSpecies.setText(names[2]);
            thirdSpecies.setText(names[0]);
            defPercent1 = 100 - percent[2];
            percent = new double[]{Double.parseDouble(betaChainsResults12[3]), Double.parseDouble(betaChainsResults31[3])};
        }
        
        if(percent[0] > percent[1]){
            defPercent2 = 100- percent[0];
        }else{
            defPercent2 = 100-percent[1];
        }
        setPoints(defPercent1,defPercent2);
        dispayResult(names, betaChainsdisplay, def);
    }
    private void setPoints(double defPercent1,double defPercent2){
        first.setEndX(defPercent1*5);
        second.setEndX(defPercent1*5);
        firstVSSecond.setStartX(500-(defPercent1*5));
        firstVSSecond.setEndX(500-(defPercent1*5));
        FSLine.setEndX(500-(defPercent1*5));
        
        FSLine.setStartX(500-(defPercent2*5));
        third.setEndX(defPercent2*5);
        thirdVSFirstVSSecond.setStartX(500-(defPercent2*5));
        thirdVSFirstVSSecond.setEndX(500-(defPercent2*5));
        finishLine.setEndX(500-defPercent2*5);
    }

    private String[] calculateDef(String species1, String species2) {
        char[] speciesOne = species1.toLowerCase().toCharArray();
        char[] speciesTwo = species2.toLowerCase().toCharArray();

        int result = 0;
        for (int i = 0; i < speciesOne.length; i++) {
            if (speciesOne[i] != speciesTwo[i]) {
                result++;
                speciesOne[i] = Character.toUpperCase(speciesOne[i]);
                speciesTwo[i] = Character.toUpperCase(speciesTwo[i]);
            }
        }
        double percent = ((speciesOne.length - result) / (double) speciesOne.length) * 100;

        return new String[]{new String(speciesOne), new String(speciesTwo), result + "", String.format("%.2f", percent)};
    }

    private void dispayResult(String[] specisName, String[] betaChain, String[] def) {
        //species names
        betaChain1.setText(specisName[0]);
        betaChain11.setText(specisName[0]);

        betaChain2.setText(specisName[1]);
        betaChain22.setText(specisName[1]);

        betaChain3.setText(specisName[2]);
        betaChain33.setText(specisName[2]);

        //beta chain
        BetaChain12.setText(betaChain[0]);
        BetaChain21.setText(betaChain[1]);

        BetaChain13.setText(betaChain[2]);
        BetaChain31.setText(betaChain[3]);

        BetaChain23.setText(betaChain[4]);
        BetaChain32.setText(betaChain[5]);

        //def
        percent.setText(def[0]);
        percent1.setText(def[1]);
        percent2.setText(def[2]);
    }

    //===========================================================================
    //page 3
    @FXML
    private Line first;
    @FXML
    private Line second;
    @FXML
    private Line firstVSSecond;
    @FXML
    private Line FSLine;
    @FXML
    private Line third;
    @FXML
    private Line thirdVSFirstVSSecond;
    @FXML
    private Line finishLine;
    @FXML
    private Label firstSpecies;
    @FXML
    private Label secondSpecies;
    @FXML
    private Label thirdSpecies;
    @FXML
    private AnchorPane treePage;

    @FXML
    private void showTreesMethod() {
        if (resultsPage.isVisible()) {
            resultsPage.setVisible(false);
        }
        if (!treePage.isVisible()) {
            treePage.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        speciesNameColumn.setCellValueFactory(new PropertyValueFactory<>("SpeciesName"));
        betaChainColumn.setCellValueFactory(new PropertyValueFactory<>("BetaChain"));
    }
}
