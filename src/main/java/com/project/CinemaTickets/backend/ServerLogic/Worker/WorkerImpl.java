package com.project.CinemaTickets.backend.ServerLogic.Worker;

import org.springframework.stereotype.Component;

@Component
public class WorkerImpl extends Thread implements Worker {
    public void run() {
        System.out.println("Worker run");
    }

    public void start() {
        this.run();
    }
}
