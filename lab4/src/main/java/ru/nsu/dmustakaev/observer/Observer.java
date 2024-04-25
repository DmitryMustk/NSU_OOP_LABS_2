package ru.nsu.dmustakaev.observer;

import ru.nsu.dmustakaev.observer.context.Context;

public interface Observer {
    void update(Context context);
}
