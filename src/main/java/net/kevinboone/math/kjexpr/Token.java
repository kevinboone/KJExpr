package net.kevinboone.math.kjexpr;
import java.util.*;


public class Token
  {
  public enum Type {NUMBER, OPERATOR, NAME};
  String text;
  Type type;
  int line;
  int col;
  String file;

  public Token (Type type, String text, String file, int line, int col)
    {
    this.type = type;
    this.text = text;
    this.line = line;
    this.col = col;
    this.file = file;
    }

  public String getText() { return text; };
  public Type getType() { return type; };
  public String toString() { return type + " " + text; }
  public String getFile() { return file; }
  public int getLine() { return line; }
  public int getCol() { return col; }
  }




