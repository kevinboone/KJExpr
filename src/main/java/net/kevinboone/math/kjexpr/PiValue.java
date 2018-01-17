/*==========================================================================
KJEXPR
PiValue.java
Copyright (c)2017 Kevin Boone, GPL3
==========================================================================*/

package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;

/** A variable to hold the (dynamic) value of pi. The value must be
    determined at runtime, according to the current scale settings for
    arbitrary-length numbers
*/
public class PiValue extends Value
  {
  public PiValue()
    {
    super ("pi", "the constant pi");
    }

  public ParseTree toParseTree (SymbolTable table, 
        EvaluationContext context)
    throws EvaluationException
      {
      MathContext mc = context.getMC(); 
      return new ParseTreeNumber (BigDecimalMath.pi (mc));
      }
  }



