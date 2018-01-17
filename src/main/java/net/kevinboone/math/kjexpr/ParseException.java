package net.kevinboone.math.kjexpr;

public class ParseException extends Exception
  {
  String filename;
  String token;
  int line;
  int col;
  public ParseException (String message, String token, String filename,
       int line, int col)
    {
    super (message);
    this.filename = filename;
    this.token = token;
    this.line = line;
    this.col = col;
    }


  public void setLine (int line)
    {
    this.line = line;
    }

  public String toString()
    {
    return getMessage() + ", line: " + line + ", col: " + col + ", file: " 
     + filename + (token != null ? ", token: '" + token + "'" : "");
    }
  }
