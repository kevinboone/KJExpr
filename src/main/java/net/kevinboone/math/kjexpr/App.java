package net.kevinboone.math.kjexpr;
import net.kevinboone.math.kjexpr.Parser;
import net.kevinboone.math.kjexpr.Evaluator;
import net.kevinboone.math.kjexpr.CommonSymbolTable;
import net.kevinboone.math.kjexpr.Value;

public class App
  {
  public static void main (String[] args)
      throws Exception
    {
    // Instantiate a Parser and an Evaluator. The Evaluator
    //  allows the setting of precision, etc., although it isn't used in
    //  this simple example
    Parser parser = new Parser();
    Evaluator evaluator = new Evaluator();
  
    // Create a symbol table. CommonSymbolTable includes basic
    //  math operations, along with functions like sin() and log(),
    //  and the constants e and pi
    CommonSymbolTable table = new CommonSymbolTable (parser);
    evaluator.setSymbolTable (table);

    // Parse an expression to a ParseTree. This can throw a ParseException
    ParseTree pt = parser.parseString ("tan(360/8)", "stdin");

    // Evaluate the expression. This produces some subclass of Value,
    //  usually a NumberValue
    Value result = evaluator.evaluate (pt);

    // Display the result. Usually we will want to convert it into a
    //   number for further processing
    System.out.println ("result=" + result);
    }
  }



