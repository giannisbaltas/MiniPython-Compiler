import java.io.*;
import minipython.lexer.Lexer;
import minipython.parser.Parser;
import minipython.node.*;
import java.util.*;

public class ParserTest
{
  public static void main(String[] args)
  {
    try
    {
      Parser parser =
        new Parser(
        new Lexer(
        new PushbackReader(
        new FileReader(args[0].toString()), 1024)));
      Hashtable symtable =  new Hashtable();
      Start ast = parser.parse();
      ast.apply(new Visitor(symtable));
      ast.apply(new Visitor2(symtable));
      
    }
    catch (Exception e)
    {
      System.err.println(e);
    }
  }
}
