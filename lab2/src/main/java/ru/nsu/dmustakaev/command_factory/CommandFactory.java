package ru.nsu.dmustakaev.command_factory;

import ru.nsu.dmustakaev.command_factory.exceptions.ClassLoaderException;
import ru.nsu.dmustakaev.command_factory.exceptions.CreatingCommandException;
import ru.nsu.dmustakaev.command_factory.exceptions.ResourseException;
import ru.nsu.dmustakaev.commands.Command;

import java.io.InputStream;
import java.util.Properties;

public class CommandFactory {
    private final Properties properties;

    public CommandFactory() {
        ClassLoader classLoader = CommandFactory.class.getClassLoader();
        if (classLoader == null) {
            throw new ClassLoaderException();
        }
        try (InputStream resourceIn = classLoader.getResourceAsStream("commands.properties")) {
            properties = new Properties();
            properties.load(resourceIn);
        }catch (Exception ex){
            throw new ResourseException();
        }
    }

    public Command create(String commandName) {
        try {
            String className = properties.getProperty(commandName.toUpperCase());
            return (Command) Class.forName(className).getDeclaredConstructor().newInstance();
        }catch (Exception ex){
            throw new CreatingCommandException();
        }
    }
}
