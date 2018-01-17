package net.kevinboone.math.kjexpr;
//import net.kevinboone.math.util.*;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;


public class BinaryOperatorPower extends BinaryOperator
  {
  public BinaryOperatorPower()
    {
    super ("^", "power");
    }

  ParseTree apply (ParseTree arg1, ParseTree arg2, EvaluationContext context)
      throws EvaluationException
    {
    if (arg1 instanceof ParseTreeNumber)
      {
      if (arg2 instanceof ParseTreeNumber)
        {
        ParseTreeNumber _base = (ParseTreeNumber)arg1;
        ParseTreeNumber _exponent = (ParseTreeNumber)arg2;

        BigDecimal base = _base.toBigDecimal();
        BigDecimal exponent = _exponent.toBigDecimal();
      
        MathContext mc = context.getMC(); 

        return new ParseTreeNumber 
          (BigDecimalMath.pow (base, exponent, mc));
        }
      else
        throw new EvaluationException 
         ("Can't divide values of class " + arg1.getName() + " and " 
           + arg2.getName());
      }
    else
      throw new EvaluationException 
       ("Can't divide values of class " + arg1.getName() + " and " 
         + arg2.getName());
    }
  }





