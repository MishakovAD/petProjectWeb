package com.project.CinemaTickets.backend.ServerLogic.Worker;

import org.springframework.stereotype.Component;

import java.net.URLConnection;

@Component
public class WorkerImpl extends Thread implements Worker {
    private boolean running = true;

    public void run(URLConnection connection) {
        while (isRunning()) {
            System.out.println("Worker run");
            System.out.println("URL in Worker at method run(): " + connection.getURL().toString());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void start(URLConnection connection) {
        System.out.println("URL in Worker: " + connection.getURL().toString());
        this.run(connection);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
