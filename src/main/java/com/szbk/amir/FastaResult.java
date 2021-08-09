package com.szbk.amir;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class FastaResult {

    private Integer number;
    private String fasta;
    private Button button;

    public FastaResult(Integer number, String fasta) {
        this.number = number;
        this.fasta = fasta;
        this.button = new Button("Check result");
        button.setVisible(false);
    }

    public FastaResult(Integer number, String fasta, Button button) {
        this.number = number;
        this.fasta = fasta;
        this.button = button;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFasta() {
        return fasta;
    }

    public void setFasta(String fasta) {
        this.fasta = fasta;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
