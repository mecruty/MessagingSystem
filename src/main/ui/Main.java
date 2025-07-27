package ui;

import model.*;

public class Main {
    public static void main(String[] args) {
        new PostOfficeAppUI(true, new PostOffice());
        //new PostOfficeConsoleApp();
    }
}