package ru.nsu.dmustakaev.game_object_factory;

import java.io.InputStream;
import java.util.Properties;

public class GameObjectFactory {
    private final Properties properties;

    public GameObjectFactory() {
        var classLoader = GameObjectFactory.class.getClassLoader();
        if(classLoader == null) {
            throw new ClassLoaderException();
        }
        try (InputStream resourseIn = classLoader.getResourceAsStream("game_object.properties")){
            properties = new Properties();
            properties.load(resourseIn);
        } catch (Exception exception) {
            throw new ResourceException();
        }
    }


}
