package ca.uwo.eng.se2205b.lab03;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Simple AutoComplete example to utilize a Trie.
 */
public class AutoComplete extends Application {
    
    @FXML
    private ListView<String> options;

    @FXML
    private TextField input;

    @FXML
    private Spinner<Integer> resultCounter;

    @FXML
    private Label countLabel;

    /**
     * Trie of words
     */
    private Trie prefixTrie;

    /**
     * Load the {@link #prefixTrie} field with the values from the provided dictionary.
     * There is a single word per line.
     */
    private void loadTrie() {
        // TODO SE2205B
        prefixTrie = new LinkedTrie();
        InputStream io = AutoComplete.class.getResourceAsStream("/dictionary.txt");
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(io));
            String line;
            while((line = br.readLine()) != null) {
                prefixTrie.put(line);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Read from the input box, and the spinner and calculate the auto complete and update the "countLabel"
     * with the number of results.
     */
    private void loadAutoComplete() {
        // TODO SE2205B
        String temp = input.getText();
        int num = resultCounter.getValue();
        int count = 0;
        ObservableList<String> items = FXCollections.observableArrayList ();
        for(String t: prefixTrie.getNextN(temp, 20000)){
            count++;
            if(items.size()<num){
                items.add(t);
            }
        }
        countLabel.setText(Integer.toString(count));
        options.setItems(items);
    }


    @FXML
    protected void initialize() {

        // TODO SE2205B

        ////////////////////////////////////////
        // DO NOT CHANGE BELOW
        ////////////////////////////////////////

        resultCounter.valueProperty().addListener((obs, oldValue, newValue) -> {
            loadAutoComplete();
        });

        countLabel.setText("0");

        loadTrie();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AutoComplete.fxml"));
    
        Scene scene = new Scene(root);
    
        stage.setTitle("Auto-complete Example");
        stage.setScene(scene);
        stage.show();
    }
    
    
}
