package com.szbk.amir;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AmirController {
    @FXML
    private TextArea inputArea;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<FastaResult, Integer> numberColumn;
    @FXML
    private TableColumn<FastaResult, String> fastaColumn;
    @FXML
    private TableColumn<FastaResult, Button> btnColumn;

    private List<String> fastaStrings;

    @FXML
    protected void parseBtnClick() {
        tableView.getItems().clear();

        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        fastaColumn.setCellValueFactory(new PropertyValueFactory<>("fasta"));
        btnColumn.setCellValueFactory(new PropertyValueFactory<>("button"));

        tableView.setPlaceholder(new Label("No rows to display"));

        int number = 1;
        String rna = inputArea.getText().toUpperCase().trim().replace("\n", "").replace("\r", "");
        for (int i=0; i<= rna.length()-22; i++) {
            String testString = rna.substring(i, i+22);

            if (startWithGC(testString) && isCorrectTAPosition(testString) && thirdCharNotTA(testString) &&
                    correctAPosition(testString) && gcContentCheck19(testString) && gcContentCheck1022(testString) &&
                    consecutiveLetter(testString)) {
                if (fastaStrings == null) {
                    fastaStrings = new ArrayList<>();
                }
                fastaStrings.add(testString);
                Button btn = new Button("BLAST this sh*t!");

                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        try {
                            Desktop.getDesktop().browse(new URL("https://blast.ncbi.nlm.nih.gov/Blast.cgi?QUERY="+testString+"&CMD=Put&DATABASE=refseq_select_rna&PROGRAM=blastn").toURI());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                FastaResult fastaResult = new FastaResult(number, testString, btn);
                tableView.getItems().add(fastaResult);
                number++;
            }
        }
    }

    @FXML
    protected void copyClipboard() {
        final ClipboardContent content = new ClipboardContent();
        List<FastaResult> items = tableView.getItems();
        content.putString(items.stream().map(f -> f.getNumber().toString() + ": " + f.getFasta()).collect(Collectors.joining("\n")));
        Clipboard.getSystemClipboard().setContent(content);
    }

    @FXML
    protected void blastAll() throws IOException, URISyntaxException {
        List<FastaResult> items = tableView.getItems();
        String queryString = items.stream().map(a -> "> " + a.getNumber() + " " + a.getFasta() + "\n" + a.getFasta() + "\n").collect(Collectors.joining());
        String encodedQueryString = URLEncoder.encode(queryString, StandardCharsets.UTF_8.toString());
        String encodedSpeciesString = URLEncoder.encode("Mus musculus (taxid:10090)", StandardCharsets.UTF_8.toString());
        Desktop.getDesktop().browse(new URL("https://blast.ncbi.nlm.nih.gov/Blast.cgi?QUERY=" + encodedQueryString + "&CMD=Put&DATABASE=refseq_rna&PROGRAM=blastn&MEGABLAST=on&EQ_MENU="+encodedSpeciesString).toURI());
    }

    private boolean startWithGC(String testString) {
        return testString.startsWith("G") || testString.startsWith("C");
    }

    private boolean thirdCharNotTA(String testString) {
        return testString.charAt(2)!='A';
    }

    private boolean gcContentCheck19(String testString) {
        int gcNumber = (int) testString.substring(0, 9).chars().filter(ch -> ch == 'G' || ch == 'C').count();
        return gcNumber >= 5;
    }

    private boolean correctAPosition(String testString) {
        return testString.charAt(9)=='A' || testString.charAt(9)=='T';
    }

    private boolean gcContentCheck1022(String testString) {
        int gcNumber = (int) testString.substring(9).chars().filter(ch -> ch == 'G' || ch == 'C').count();
        return gcNumber <= 6;
    }

    private boolean consecutiveLetter(String testString) {
        return !testString.contains("AAAAA") && !testString.contains("GGGG") && !testString.contains("TTTTT") && !testString.contains("CCCC");
    }
    private boolean isCorrectTAPosition(String testString) {
        return testString.charAt(21)=='T' || testString.charAt(21)=='A';
    }
/*
    private boolean weightCheck(String testString) {
        int firstGCCount = (int) testString.substring(0, 3).chars().filter(ch -> ch == 'G' || ch == 'C').count();
        int firstATCount = (int) testString.substring(0, 3).chars().filter(ch -> ch == 'A' || ch == 'T').count();

        String lastChars = testString.substring(testString.length() - 4);
        if (firstGCCount < firstATCount) {
            return lastChars.chars().filter(ch -> ch == 'G' || ch == 'C').count() >
                    lastChars.chars().filter(ch -> ch == 'A' || ch == 'T').count();
        } else {
            return lastChars.chars().filter(ch -> ch == 'G' || ch == 'C').count() <
                    lastChars.chars().filter(ch -> ch == 'A' || ch == 'T').count();
        }
    }
*/
}