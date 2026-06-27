package com.shefani.lexing;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Lexer {

  private final String source;
  List<Token> tokens = new LinkedList<>();
  private int rowNo;
  private int rowIdx = 0;
  private int nextIdx = 1;

  public Lexer(String source) {
    this.source = Objects.requireNonNull(source);
  }

  public void lexer() {
    while (rowIdx < source.length()) {
      var token = nextToken();
      if (token != null) {
        tokens.add(token);
      }
    }
    tokens.forEach(System.out::println);
    System.out.println("rowNo: " + rowNo);
  }

  private Token nextToken() {
    skipWhitespace();

    char current = peek();

    switch (current) {
      case '+' -> {
        advance();
        return new Token(TokenType.PLUS, "+");
      }
      case '-' -> {
        advance();
        return new Token(TokenType.MINUS, "-");
      }
      case '(' -> {
        advance();
        return new Token(TokenType.L_PARENS, "(");
      }
      case ')' -> {
        advance();
        return new Token(TokenType.R_PARENS, ")");
      }
      case '=' -> {
        advance();
        return new Token(TokenType.EQUALS, "=");
      }
      case ';' -> {
        advance();
        return new Token(TokenType.SEMICOLON, ";");
      }
      case '{' -> {
        advance();
        return new Token(TokenType.L_CURLY, "{");
      }
      case '}' -> {
        advance();
        return new Token(TokenType.R_CURLY, "}");
      }
    }

    if (Character.isDigit(current)) {
      return new Token(TokenType.NUMBER, readNumber());
    }

    if (Character.isLetter(current)) {
      return new Token(TokenType.MODIFIER, readIdent());
    }

    advance();
    return null;
  }

  private String readNumber() {
    int start = rowIdx;
    while (!isAtEnd() && Character.isDigit(peek())) {
      advance();
    }
    return source.substring(start, rowIdx);
  }

  private String readIdent() {
    int start = rowIdx;
    while (!isAtEnd() && Character.isLetter(peek())) {
      advance();
    }
    return source.substring(start, rowIdx);
  }

  private void skipWhitespace() {
    while (!isAtEnd()) {
      char c = peek();
      if (c == '\n') {
        rowNo++;
        advance();
      } else if (c == ' ' || c == '\t' || c == '\r') {
        advance();
      } else {
        break;
      }
    }
  }

  private char peek() {
    if (isAtEnd()) {
      return '\0';
    }
    return source.charAt(rowIdx);
  }

  private void advance() {
    if (!isAtEnd()) rowIdx++;
  }

  private boolean isAtEnd() {
    return rowIdx >= source.length();
  }

  record Token(TokenType tokenType, String value) {}
}
