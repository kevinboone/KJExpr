/*==========================================================================
KJEXPR
EValue.java
Copyright (c)2017 Kevin Boone, GPL3
==========================================================================*/

package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;

/** A variable to hold the (dynamic) value of e. The value must be
    determined at runtime, according to the current scale settings for
    arbitrary-length numbers
*/
public class EValue extends Value
  {
  public EValue()
    {
    super ("e", "the constant e");
    }

  public ParseTree toParseTree (SymbolTable table, 
        EvaluationContext context)
    throws EvaluationException
      {
      MathContext mc = context.getMC(); 
      return new ParseTreeNumber (BigDecimalMath.e (mc));
      }
  }



