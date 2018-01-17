/*===============================================================================
KJEXPR
EvaluationContext.java
Copyright (c)2017 Kevin Boone, GPL v3.0
===============================================================================*/
package net.kevinboone.math.kjexpr;
import java.util.*;
import java.math.*;


/**
  The class is a carrier for various bits of evaluation-related information,
  that are passed to the Evaluator when recursively evaluating a ParseTree
  into a Value. 
*/
public class EvaluationContext
  {
  MathContext mc; 
  public enum AngleMode {DEG, RAD}
  AngleMode angleMode = AngleMode.DEG;

  public EvaluationContext (int prec)
    {
    mc = new MathContext (prec);
    }

  public void setPrecision (int prec) 
    { 
    mc = new MathContext (prec); 
    }

  public int getPrecision()
    {
    return mc.getPrecision();
    }

  public AngleMode getAngleMode() { return angleMode; }
  public void setAngleMode (AngleMode angleMode) { this.angleMode = angleMode; }

  public MathContext getMC() { return mc; }
  public void setMC (MathContext mc) { this.mc = mc; }
  }


