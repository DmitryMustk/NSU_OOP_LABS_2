package ru.nsu.dmustakaev.context;

import ru.nsu.dmustakaev.context.exceptions.GetValueByMissingVariable;
import ru.nsu.dmustakaev.context.exceptions.PeekEmptyContextStackException;
import ru.nsu.dmustakaev.context.exceptions.PopEmptyStackException;
import ru.nsu.dmustakaev.context.exceptions.VariableRewriteException;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Context {
    Stack<Double> stack;
    Map<String, Double> vars;

    public Context() {
        stack = new Stack<>();
        vars = new HashMap<>();
    }

    public void addVariable(String var, Double value) throws VariableRewriteException {
        if(vars.containsKey(var)) {
            throw new VariableRewriteException();
        }
        vars.put(var, value);
    }

    public Double peekStack() throws PeekEmptyContextStackException {
        try {
            return stack.peek();
        } catch (EmptyStackException e) {
            throw new PeekEmptyContextStackException();
        }
    }

    public Double popStack() throws PopEmptyStackException {
        try {
            return stack.pop();
        } catch (EmptyStackException e) {
            throw new PopEmptyStackException();
        }
    }

    public void putOnStack(Double value) {
        stack.add(value);
    }

    public Double getValueByVariable(String var) {
        if (!vars.containsKey(var)) {
            throw new GetValueByMissingVariable();
        }
        return vars.get(var);
    }

    public boolean isCorrectVariableName(String name) {
        Pattern namePattern = Pattern.compile("^[a-zA-Z]\\w*$");
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }
}
