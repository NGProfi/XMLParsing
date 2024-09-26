package org.example;

import org.example.model.Root;

public class MainParsing {

    public static void main(String[] args) {
        Parser parser = new Parser();
        Root root = parser.parse();
    }

}
