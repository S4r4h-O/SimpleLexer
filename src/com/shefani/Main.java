package com.shefani;

import com.shefani.lexing.Lexer;
import java.io.FileReader;
import java.io.IOException;

public class Main {

  static void main(String[] args) {

    String source = "";
    try (FileReader fr = new FileReader("test.txt")) {
      source = fr.readAllAsString();
    } catch (IOException e) {
      System.out.println("ERROR: " + e.getMessage());
      System.exit(1);
    }
    System.out.println(source);

    Lexer lexer = new Lexer(source);
    lexer.lexer();
  }
}
