package net.kevinboone.math.kjexpr;
import java.util.*;


public class ParseTreeMultiply extends ParseTreeInfix
  {
  public ParseTreeMultiply (ParseTree first, ParseTree second)
    {
    super (first, "-", second);
    }

  public String toString ()
    {
    return "multiply: " + first + " " + "*" + " " + second;
    }
  }




