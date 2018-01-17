package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*;

public class NumberValue extends Value
  {
  BigDecimal value;

  public NumberValue (String name, String desc, BigDecimal value)
    {
    super (name, desc);
    this.value = value;
    }

  public NumberValue (String name, String desc, String value)
    {
    super (name, desc);
    this.value = new BigDecimal (value);
    }

  public ParseTree toParseTree (SymbolTable table, 
        EvaluationContext context)
      throws EvaluationException
    {
    return new ParseTreeNumber (value);
    }

  public BigDecimal toBigDecimal ()
    {
    return value;
    }

  public String toString()
    {
    return "" + value;
    }
  }


