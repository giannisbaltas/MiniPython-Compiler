package visitors;

import minipython.lexer.Lexer;
import minipython.node.Start;
import minipython.parser.Parser;

import java.io.FileReader;
import java.io.PushbackReader;

public class Compiler
{
    public static void main(String[] args)
    {
        try {
            Parser parser = new Parser(new Lexer(new PushbackReader(new FileReader(args[0]), 1024)));

            Start ast = parser.parse();

            ast.apply(new UndeclaredIdentifiersVisitor());
            ast.apply(new UndeclaredFunctionsVisitor());
            ast.apply(new FunctionArgumentValidatorVisitor());
            ast.apply(new IllegalOperatorsVisitor());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

