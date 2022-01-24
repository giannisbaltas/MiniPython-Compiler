package visitors;

import minipython.analysis.DepthFirstAdapter;
import minipython.node.AFunction;
import minipython.node.AFunctionCall;

import java.util.*;
import java.util.stream.Collectors;

//Visitors 3 and 7 implemented together.
@SuppressWarnings({"Unchecked","rawtypes"})
public class FunctionArgumentValidatorVisitor extends DepthFirstAdapter
{
    //Function names and the number of min(mandatory) and max(mandatory+optional) arguments they accept.
    //Different function definitions may have the same identifier, so we use a list.
    Map<String, List<MinMaxArgs>> declaredFunctionsToArgsMap = new HashMap<>();

    @Override
    public void inAFunction(AFunction node)
    {
        String identifier = node.getIdentifier().toString().strip();
        MinMaxArgs currentFunctionArgs = createMinMaxArgsObjFromFunctionArgs(node.getArgument());
        if (declaredFunctionsToArgsMap.containsKey(identifier)) {
            for (MinMaxArgs existingFunctionArgs : declaredFunctionsToArgsMap.get(identifier)) {
                if (currentFunctionArgs.min == existingFunctionArgs.min ||
                    currentFunctionArgs.max == existingFunctionArgs.max)
                {
                    System.err.println("Multiple declarations of function: '"+identifier+"'");
                    return;
                }
            }
            List<MinMaxArgs> list = declaredFunctionsToArgsMap.get(identifier);
            list.add(currentFunctionArgs);
        } else {
            List<MinMaxArgs> list = new ArrayList<>();
            list.add(currentFunctionArgs);
            declaredFunctionsToArgsMap.put(identifier, list);
        }
    }

    @Override
    public void inAFunctionCall(AFunctionCall node)
    {
        String identifier = node.getIdentifier().toString().strip();

        //If there is a call to a non existent function, the other appropriate visitor will catch that
        if (!declaredFunctionsToArgsMap.containsKey(identifier)) {
            return;
        }

        int providedArgsNum = splitFirstElementToList(node.getArglist()).size();
        boolean foundMatchingFunction = false;
        for (MinMaxArgs args : declaredFunctionsToArgsMap.get(identifier)) {
            if (providedArgsNum >= args.min && providedArgsNum <= args.max) {
                foundMatchingFunction = true;
                break;
            }
        }
        if (!foundMatchingFunction) {
            System.err.println("No definition of function '"+identifier+"' matches the number of provided arguments.");
        }
    }

    private MinMaxArgs createMinMaxArgsObjFromFunctionArgs(LinkedList list)
    {
        List<String> elements = splitFirstElementToList(list);
        if (elements.isEmpty()) {
            return MinMaxArgs.makeEmpty();
        }

        int optional_count = 0;
        for (String s : elements) {
            if (s.startsWith("\"") || Character.isDigit(s.charAt(0))) {
                optional_count++;
            }
        }

        return new MinMaxArgs(elements.size() - optional_count*2, elements.size() - optional_count);
    }

    private List<String> splitFirstElementToList(LinkedList list)
    {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(list.getFirst().toString()
                .split(" "))
                .map(String::strip)
                .filter((s) -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static class MinMaxArgs
    {
        public int min;
        public int max;

        public MinMaxArgs(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public static MinMaxArgs makeEmpty() {
            return new MinMaxArgs(0,0);
        }
    }
}
