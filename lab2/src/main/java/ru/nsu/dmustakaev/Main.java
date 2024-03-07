package ru.nsu.dmustakaev;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        CLIParser CLIparser = new CLIParser(args);
        InstrctionParser instrParser = new InstrctionParser((CLIparser.hasArg())
                ? new FileInputStream(CLIparser.getArg())
                : System.in);
        instrParser.parseInstructions();
        StackCalculator calculator = new StackCalculator();
        calculator.calculate(instrParser.getInstructions());
    }

}
