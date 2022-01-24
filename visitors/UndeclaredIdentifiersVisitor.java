package visitors;

import minipython.analysis.DepthFirstAdapter;
import minipython.node.*;

import java.util.HashSet;
import java.util.Set;

//Visitor 1
public class UndeclaredIdentifiersVisitor extends DepthFirstAdapter
{
    Set<String> globalIdentifiers = new HashSet<>(30);
    Set<String> tempFuncIdentifiers = new HashSet<>(6);

    @Override
    public void caseAIdentifierDefVal(AIdentifierDefVal node)
    {
        //these represent the function arguments, that need to exist until the function ends
        tempFuncIdentifiers.add(node.getIdentifier().toString());
    }

    @Override
    public void outAFunction(AFunction node)
    {
        //clearing the scoped identifiers (the function arguments), when the function ends
        tempFuncIdentifiers.clear();
    }

    @Override
    public void inAAssignStatement(AAssignStatement node)
    {
        globalIdentifiers.add(node.getIdentifier().toString());
    }

    @Override
    public void inAIdentifierExpression(AIdentifierExpression node)
    {
        String identifier = node.getIdentifier().toString();

        if (!globalIdentifiers.contains(identifier) && !tempFuncIdentifiers.contains(identifier)) {
            System.err.println("Undeclared identifier: "+identifier);
        }
    }
}