package net.kevinboone.math.kjexpr;
import java.util.*;


public abstract class SymbolTable  
  {
  Hashtable<String, UnaryOperator> unaryOperators;
  Hashtable<String, BinaryOperator> binaryOperators;
  Hashtable<String, Function> functions;
  Hashtable<String, Value> variables;

  public SymbolTable()
    {
    unaryOperators = new Hashtable<String, UnaryOperator>();
    binaryOperators = new Hashtable<String, BinaryOperator>();
    functions = new Hashtable<String, Function>();
    variables = new Hashtable<String, Value>();
    }

  public UnaryOperator getUnaryOperator (String name)
    {
    return unaryOperators.get (name);
    }

  public BinaryOperator getBinaryOperator (String name)
    {
    return binaryOperators.get (name);
    }

  public Function getFunction (String name)
    {
    return functions.get (name);
    }

  public Value getVariable (String name)
    {
    return variables.get (name);
    }

  public void addUnaryOperator (String name, UnaryOperator op)
    {
    unaryOperators.put (name, op);
    }

  public void addBinaryOperator (BinaryOperator op)
    {
    binaryOperators.put (op.getSymbol(), op);
    }

  public void addFunction (Function fn)
    {
    functions.put (fn.getName(), fn);
    }

  public void addVariable (Value var)
    {
    variables.put (var.getName(), var);
    }

  public Hashtable<String, Value> getVariableTable() { return variables; }
  public void setVariableTable (Hashtable<String, Value> variables)
    {
    this.variables = variables;
    }

  public Collection<Value> getVariables() { return variables.values(); };
  public Collection<Function> getFunctions() { return functions.values(); };
  public Collection<BinaryOperator> getBinaryOperators() 
    { return binaryOperators.values(); };
  }

