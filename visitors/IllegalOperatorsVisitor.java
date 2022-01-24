package visitors;

import minipython.analysis.DepthFirstAdapter;
import minipython.node.AArithmeticExpression;
import minipython.node.ADivAssignStatement;
import minipython.node.ASubAssignStatement;

//Visitor 5
public class IllegalOperatorsVisitor extends DepthFirstAdapter
{
    @Override
    public void inAArithmeticExpression(AArithmeticExpression node)
    {
        if (node.getExp1().toString().strip().equals("None") || node.getExp2().toString().strip().equals("None")) {
            System.err.println("Invalid operator 'None' used in arithmetic expression");
        }
    }

    @Override
    public void inADivAssignStatement(ADivAssignStatement node)
    {
        if (node.getExpression().toString().strip().equals("None")) {
            System.err.println("Invalid operator 'None' used in div assign statement");
        }
    }

    @Override
    public void inASubAssignStatement(ASubAssignStatement node)
    {
        if (node.getExpression().toString().strip().equals("None")) {
            System.err.println("Invalid operator 'None' used in sub assign statement");
        }
    }
}
