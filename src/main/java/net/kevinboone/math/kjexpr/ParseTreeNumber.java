/*===============================================================================
KJEXPR
ParseTreeNumber.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*;


/**
  Models a numeric value. This is a terminal symbol in the parse tree, and
  can be converted to/from a NumberValue object.
*/
public class ParseTreeNumber extends ParseTree
  {
  BigDecimal value;

  public ParseTreeNumber (BigDecimal value)
    {
    this.value = new BigDecimal(0).add (value); 
    }

  /**
    Parses a hex number to a BigDecimal. Note that decimal (or hexadecimal)
    points are not (yet) allowed, nor scientific notation. This
    method can throw the (unchecked) NumberFormatException. 
  */
  static public BigDecimal parseHex (String hex)
    {
    return new BigDecimal (new BigInteger (hex, 16));
    }

  /** 
      Create a ParseTreeNumber from a text string. Note that this
      method can throw the (unchecked) NumberFormatException. 
  */
  public ParseTreeNumber (String text, int defaultBase)
    {
    if (text.length() > 2 && text.startsWith ("0x")) 
      {
      value = parseHex (text.substring (2)); 
      }
    else
      {
      value = new BigDecimal (text); 
      }
    }

  public String getName() { return ("decimal number"); }

  public BigDecimal toBigDecimal()
   {
   return new BigDecimal(0).add (value); 
   }

  public String toString()
    {
    return "" + value;
    }

  public ParseTreeNumber negate()
    {
    return new ParseTreeNumber (new BigDecimal(0).subtract (this.value));
    }

  public ParseTree evaluate (SymbolTable table, 
      EvaluationContext context)
    {
    // Easy -- just return myself
    return this;
    }

  public Value toValue()
    {
    return new NumberValue ("", "", value); 
    }

  }



