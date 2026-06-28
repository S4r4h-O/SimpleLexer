package com.shefani.lexing;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Lexer {

  private final String source;
  List<Token> tokens = new LinkedList<>();
  private int rowNo = 1;
  private int rowIdx = 0;
  private int cursorIdx = -1;

  public Lexer(String source) {
    this.source = Objects.requireNonNull(source);
  }

  public void lexer() {

    while (rowIdx < source.length()) {
      var token = nextToken();
      if (token.tokenType != TokenType.INVALID) {
        this.tokens.add(token);
      } else {
        System.out.printf("Error in line %d, %d: %s%n", rowNo, (cursorIdx - 1), token);
      }
    }

    tokens.forEach(System.out::println);
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
    return new Token(TokenType.INVALID, String.valueOf(current));
  }

  private String readNumber() {
    int start = this.rowIdx;
    while (!isAtEnd() && Character.isDigit(peek())) {
      advance();
    }
    return source.substring(start, this.rowIdx);
  }

  private String readIdent() {
    int start = this.rowIdx;
    while (!isAtEnd() && Character.isAlphabetic(peek())) {
      advance();
    }
    return source.substring(start, this.rowIdx);
  }

  private void skipWhitespace() {
    while (!isAtEnd()) {
      char c = peek();
      if (c == '\n') {
        this.cursorIdx = 0;
        this.rowNo++;
        advance();
      } else if (Character.isSpaceChar(c)) {
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
    if (!isAtEnd()) {
      this.rowIdx++;
      this.cursorIdx++;
    }
  }

  private boolean isAtEnd() {
    return this.rowIdx >= source.length();
  }

  record Token(TokenType tokenType, String value) {

    @Override
    public String toString() {
      return "Token{" + "tokenType=" + tokenType + ", value='" + value + '\'' + '}';
    }
  }
}
