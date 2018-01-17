package net.kevinboone.math.kjexpr;
import java.util.*;

public abstract class Tokenizer 
  {
  private static Tokenizer defaultTokenizer;

  /** 
    Tokenize a string. The filename argument is just stored in the
    generated tokens, in case it is needed for logging purposes.
  */
      
  public abstract Vector<Token> run (String line, String filename)
    throws ParseException;

  public static Tokenizer getDefault()
    {
    if (defaultTokenizer == null)
      defaultTokenizer = new DefaultTokenizer();
    return defaultTokenizer;
    }
  }


