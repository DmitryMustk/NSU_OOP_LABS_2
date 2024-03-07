package ru.nsu.dmustakaev;

import java.io.InputStream;
import java.util.*;

public class InstrctionParser {
    private final InputStream inputStream;
    private final List<Instruction> instructions;

    InstrctionParser(InputStream inputStream) {
        this.inputStream = inputStream;
        instructions = new ArrayList<>();
    }

    public void parseInstructions() {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            line = removeCommentsFromLine(line);
            if (line.equals("\n") || line.isEmpty()) {
                continue;
            }
            List<String> tokens = new ArrayList<>(List.of(line.split(" ")));
            instructions.add(new Instruction(
                    tokens.getFirst(), (tokens.size() > 1) ? tokens.subList(1, tokens.size()) : null));
        }
    }

    private String removeCommentsFromLine(String line) {
        return line.replaceAll("#.*", "");
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }
}
