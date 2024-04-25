package ru.nsu.dmustakaev.observer;

import ru.nsu.dmustakaev.observer.context.Context;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<Observer> observers;

    public void addObserver(Observer observer) {
        if(observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        if (observers == null) {
            return;
        }

        observers.remove(observer);
    }

    public void removeObservers() {
        if (observers == null) {
            return;
        }

        observers.clear();
    }

    public void notifyObservers(Context context) {
        if (observers == null) {
            return;
        }

        for (var observer: observers) {
            observer.update(context);
        }
    }
}
