package com.citb408.printing.solid;

import com.citb408.printing.abstraction.PrintMode;
import com.citb408.publications.abstraction.Publication;
import com.citb408.utilities.Check;
import com.citb408.utilities.MoneyHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrinterHandler implements Runnable {
    private final List<PrintTask> tasks;
    private final MoneyHandler moneyHandler;

    private final int numberOfPrinters;
    private final Printer[] printers;
    private final LinkedBlockingQueue<PrintTask> queue;
    private AtomicBoolean isShutDownInitiated;

    public PrinterHandler(int numberOfPrinters, List<PrintTask> tasks, MoneyHandler moneyHandler) {
        this.numberOfPrinters = Check.positive(numberOfPrinters);

        queue    = new LinkedBlockingQueue<>();
        printers = new Printer[numberOfPrinters];
        isShutDownInitiated = new AtomicBoolean(false);

        for (int i = 0; i < numberOfPrinters; i++) {
            printers[i] = new Printer("" + (i+1));
            printers[i].start();
        }

        this.tasks = tasks;
        this.moneyHandler = moneyHandler;
    }

    public List<PrintTask> getTasks() {
        return tasks;
    }

    public PrinterHandler(int numberOfPrinters, MoneyHandler moneyHandler) {
        this(numberOfPrinters, new ArrayList<>(), moneyHandler);
    }

    public void execute(PrintTask task) throws InterruptedException {
        synchronized (queue) {
            if (!isShutDownInitiated.get()) {
                queue.add(task);
                queue.notifyAll();
            } else {
                throw new InterruptedException("Printers are shutting down, unable to execute task.");
            }
        }
    }

    public void execute() {
        try {
            for (PrintTask task : tasks) {
                execute(task);
            }
            shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() throws InterruptedException {
        isShutDownInitiated = new AtomicBoolean(true);

        // Mark
        for (Printer printer : printers) {
            printer.join();
        }

        if (queue.isEmpty()) {
            System.out.println("Shutting down printers.");
            for (int i = 0; i < numberOfPrinters; i++) {
                printers[i] = null;
            }
        } else {
            System.out.println("Queue not empty.");
        }
    }

    public boolean addTask(PrintTask printTask) {
        if(!tasks.contains(printTask)) {
            tasks.add(printTask);
            return true;
        }
        return false;
    }

    public void addPublication(Publication publication, int copies) {
        PrintTask task = new PrintTask(publication, copies);
        addTask(task);
    }

    public void addPublication(Publication publication, int copies, PrintMode printMode) {
        PrintTask task = new PrintTask(publication, copies, printMode);
        addTask(task);
    }

    @Override
    public void run() {
        execute();
    }

    public class Printer extends Thread {
        private int currentPaperQuantity;

        public Printer(String name) {
            super("Printer " + name);
            this.currentPaperQuantity = PrinterSettings.getMaxPaperQuantity();
        }

        public int getCurrentPaperQuantity() {
            return currentPaperQuantity;
        }

        public void setCurrentPaperQuantity(int currentPaperQuantity) {
            this.currentPaperQuantity = currentPaperQuantity;
        }

        public void decreaseCurrentPaperQuantity(int amount) {
            this.currentPaperQuantity -= amount;
        }

        protected synchronized void refill() {
            setCurrentPaperQuantity(PrinterSettings.getMaxPaperQuantity());
            System.out.println(getName() + " was refilled with paper!");
            moneyHandler.addExpenses(refillCost());
        }

        private double refillCost() {
            return PrinterSettings.getMaxPaperQuantity() * 0.001;
        }

        private double costs(PrintTask task) {
            if (task.getQuantity() >= PrinterSettings.getDiscountLimit()) {
                return discountPrice(task);
            } else {
                return task.totalCost();
            }
        }

        private double discountPrice(PrintTask task) {
            return task.totalCost() - (task.totalCost() * (PrinterSettings.getDiscountPercentage() / 100.0));
        }

        @Override
        public void run() {
            while(!isShutDownInitiated.get() || !queue.isEmpty()) {
                PrintTask task;
                synchronized(queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait(500);
                            if (isShutDownInitiated.get()) {
                                queue.wait(2000);
                                if (queue.isEmpty())
                                    return;
                            }

                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                            Thread.currentThread().interrupt();
                        }
                    }

                    task = queue.poll();
                }

                try {
                    for (int i = task.getCurrentCopy(); i < task.getQuantity(); i++) {
                        task.setAvailablePaper(currentPaperQuantity);
                        task.run();
                        moneyHandler.addIncome(costs(task));
                    }
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
