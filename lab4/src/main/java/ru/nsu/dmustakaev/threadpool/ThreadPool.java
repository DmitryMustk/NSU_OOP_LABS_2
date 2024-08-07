package ru.nsu.dmustakaev.threadpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.IntStream;

public class ThreadPool {
    private static final Logger logger = LogManager.getLogger(ThreadPool.class);

    private final BlockingQueue<Task> queue;
    private final Set<TaskWorker> workers = new HashSet<>();
    private volatile boolean isRunning = true;

    public ThreadPool(int threadCount, int queueSize) {
        queue = new ArrayBlockingQueue<>(queueSize);
        for (int i = 0; i < threadCount; i++) {
            TaskWorker tw = new TaskWorker();
            tw.setName(Integer.toString(i));
            tw.start();
            workers.add(tw);
        }
    }

    public void addTask(Task command) throws InterruptedException {
        if (!isRunning) {
            throw new RejectedExecutionException();
        }
        try {
            queue.put(command);
        } catch (InterruptedException e) {
            logger.error(e);
            throw e;
        }
    }

    public void shutdown() {
        isRunning = false;
        interruptAll();
        workers.forEach(w -> {
            try {
                w.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("double interrupt detected");
            }
        });
    }

    public void interruptAll() {
        if(isRunning) throw new RuntimeException("can't join interrupt threads");
        for (TaskWorker w : workers) {
            w.interrupt();
        }
    }

    private final class TaskWorker extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                try {
                    Task task = queue.take();
                    logger.info("Executing task '" + task.getName() + "'");
                    task.performWork();
                } catch (InterruptedException ignored) {}
            }
        }
    }
}
