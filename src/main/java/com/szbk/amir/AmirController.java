package com.szbk.amir;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class AmirController {
    @FXML
    private TextArea inputArea;

    @FXML
    private ListView outputList;

    @FXML
    protected void parseBtnClick() {
        outputList.getItems().clear();
        int number = 1;
        String rna = inputArea.getText().toUpperCase().trim().replace("\n", "").replace("\r", "");
        List<String> matches = new ArrayList<>();
        for (int i=0; i<= rna.length()-22; i++) {
            String testString = rna.substring(i, i+22);

            if (startWithGC(testString) && isCorrectTAPosition(testString) && thirdCharNotTA(testString) &&
                    correctAPosition(testString) && gcContectCheck(testString) && atContectCheck(testString) && consecutiveLetter(testString)) {
                matches.add(number + " : " + testString);
                number++;
            }

        }
        if (matches.isEmpty()) {
            outputList.getItems().add("No match found!");
        } else {
            outputList.getItems().addAll(matches);
        }

    }

    private boolean startWithGC(String testString) {
        return testString.startsWith("G") || testString.startsWith("C");
    }

    private boolean isCorrectTAPosition(String testString) {
        return testString.charAt(21)=='T' || testString.charAt(21)=='A';
    }

    private boolean thirdCharNotTA(String testString) {
        return testString.charAt(2)!='T' && testString.charAt(2)!='A';
    }

    private boolean correctAPosition(String testString) {
        return testString.charAt(8)=='A' || testString.charAt(9)=='A' ||
                testString.charAt(8)=='T' || testString.charAt(9)=='T';
    }

    private boolean gcContectCheck(String testString) {
        int gcNumber = (int) testString.chars().filter(ch -> ch == 'G' || ch == 'C').count();
        return gcNumber <= 11;
    }

    private boolean atContectCheck(String testString) {
        int gcNumber = (int) testString.substring(8).chars().filter(ch -> ch == 'A' || ch == 'T').count();
        return gcNumber >= 7;
    }

    private boolean consecutiveLetter(String testString) {
        return !testString.contains("AAAA") && !testString.contains("GGGG") && !testString.contains("TTTT") && !testString.contains("CCCC");
    }
}