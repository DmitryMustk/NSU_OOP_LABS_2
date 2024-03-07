package ru.nsu.dmustakaev;

import ru.nsu.dmustakaev.commands.Command;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class CommandFactory {
    private final Properties properties;

    public CommandFactory() throws IOException {
        ClassLoader classLoader = CommandFactory.class.getClassLoader();
        InputStream resourceStream = classLoader.getResourceAsStream(".resources/commands.properties");
        properties = new Properties();
        properties.load(resourceStream);
    }

    public Command create(String commandName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String className = properties.getProperty(commandName.toUpperCase());
        return (Command) Class.forName(className).getDeclaredConstructor().newInstance();
    }
}
