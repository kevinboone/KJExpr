# KJExpr

Version 0.0.1

A math expression parser and evaluator for Java.

## What is this?

KJExpr is a math expression parser and evaluator library for Java, that uses
arbitrary-precision decimal arithmetic. It should work on any platform that
provides a Java runtime compatible with JDK 1.7 or later.  The KJCalc project
provides a useable command-line front end to the KJExpr library, and
demonstrates most of its functionality. Potential users of KJExpr should look
at the code for KJCalc because, frankly, there isn't much documentation for the
library itself.

KJExpr provides its own recursive-descent parser, and has few external
dependencies.  


## Using KJExpr

You can download a binary JAR from the author's website, and just add it to the
classpath of your JVM or build tools, or build from source. KJExpr is designed
to be built using Maven:

```
$ mvn clean install
```
 
Then Maven-based projects that use the library can just add 
the following dependency to pom.xml:

```xml
<pre class="codeblock" lang="xml">
    <dependency>
      <groupId>net.kevinboone.math</groupId>
      <artifactId>kjexpr</artifactId>
      <version>0.0.1</version>
    </dependency>
</pre>
```

The following example illustrates the most basic use of the KJExpr
library, to evaluate a math expression specified as a String.

```java
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
    //  usually a NumberValue. An error here results in an EvaluationException
    Value result = evaluator.evaluate (pt);

    // Display the result. Usually we will want to convert it into a
    //   number for further processing
    System.out.println ("result=" + result);
    }
  }
```

 
## Author

KJExpr is mainted by Kevin Boone (kevin at railwayterrace dot com). 
It is distributed under the terms of the GNU Public License, version 3.0,
in the hope that it will be useful. There is no warranty of any kind.


