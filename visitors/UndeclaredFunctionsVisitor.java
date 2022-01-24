package visitors;

import minipython.analysis.DepthFirstAdapter;
import minipython.node.*;

import java.util.HashSet;
import java.util.Set;

//Visitor 2
public class UndeclaredFunctionsVisitor extends DepthFirstAdapter
{
    Set<String> declaredFunctions = new HashSet<>();
    Set<String> calledFunctions = new HashSet<>();


    @Override
    public void inAFunction(AFunction node)
    {
        declaredFunctions.add(node.getIdentifier().toString().strip());
    }

    @Override
    public void inAFunctionCall(AFunctionCall node)
    {
        calledFunctions.add(node.getIdentifier().toString().strip());
    }

    @Override
    public void outAGoal(AGoal node)
    {
        for(String s : calledFunctions) {
            if (!declaredFunctions.contains(s)) {
                System.err.println("Use of undeclared function: '"+s+"'");
            }
        }
    }
}
