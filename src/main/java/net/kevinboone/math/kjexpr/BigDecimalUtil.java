/*===============================================================================
KJEXPR
BigDecimalUtil.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import ch.obermuhlner.math.big.BigDecimalMath;
import java.util.*;
import java.math.*;

public class BigDecimalUtil
  {
  static public BigDecimal degToRad (BigDecimal deg, MathContext mc)
    {
    BigDecimal pi = BigDecimalMath.pi (mc);
    return deg.divide(new BigDecimal ("360"), mc)
      .multiply(new BigDecimal ("2")).multiply(pi); 
    }
  
  static public BigDecimal radToDeg (BigDecimal rad, MathContext mc)
    {
    BigDecimal twoPi = BigDecimalMath.pi (mc).multiply (new BigDecimal("2"));
    BigDecimal pi = BigDecimalMath.pi (mc);
    BigDecimal deg = rad.divide (twoPi, mc).multiply (new BigDecimal ("360")); 
    return deg;
    }

  static public BigDecimal gcd (BigDecimal u, BigDecimal v)
    {
    BigDecimal t;
    while (v.compareTo (BigDecimal.ZERO) != 0)
        {
        t = u;
        u = v;
        v = t.remainder (v);
        }
    return u.abs();
    }


  }


