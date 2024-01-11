package net.bigyous.gptgodmc.utils;

import net.bigyous.gptgodmc.interfaces.Function;
import java.util.concurrent.ConcurrentLinkedQueue;


public class TaskQueue<T> {
    private volatile ConcurrentLinkedQueue<T> queue;
    private Function<T> task;
    private boolean isExecuting;

    public TaskQueue(Function<T> task){
        this.queue = new ConcurrentLinkedQueue<T>();
        this.task = task;
        this.isExecuting = false;
    }

    public void insert(T object){
        queue.add(object);
        if(!isExecuting){
            this.execute();
        }
    }

    public void execute(){
        Thread worker = new Thread(() -> {
            isExecuting = true;
            while(!queue.isEmpty()){
                task.apply(queue.poll());
            }
            isExecuting = false;
            Thread.currentThread().interrupt();
        });
        worker.start();
    }

}

