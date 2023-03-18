package com.citb408.printing.solid;

import com.citb408.printing.abstraction.OutOfPaperException;
import com.citb408.printing.abstraction.PrintMode;
import com.citb408.publications.abstraction.Publication;
import com.citb408.utilities.Check;

import java.io.Serializable;

public class PrintTask implements Runnable, Serializable {
    public static final long serialVersionUID = 1234L;
    private final Publication publication;
    private final int quantity;
    private int currentCopy;
    private final PrintMode printMode;
    private double pagesPerSecond;
    private int availablePaper;

    public PrintTask(Publication publication, int quantity, PrintMode printMode) {
        this.publication = publication;
        this.quantity = Check.positive(quantity);
        this.printMode = printMode;
        this.currentCopy = 0;
        setTime();
        this.availablePaper = 0;
    }

    public PrintTask(Publication publication, int quantity) {
        this(publication, quantity, PrintMode.COLORLESS); // standard is B&W
    }

    public Publication getPublication() {
        return publication;
    }

    public int getCurrentCopy() {
        return currentCopy;
    }

    public int getQuantity() {
        return quantity;
    }

    public double totalCost() {
        return publication.getCost() * printMode.getMultiplier();
    }

    // It will print the current task and decrease the paper, if the paper runs out at some time while printing
    // the program will stop for a bit exactly when the paper runs out and request a paper delivery. After that it
    // resumes once again with the task.
    @Override
    public void run() {
        long timeBefore = Math.round( (availablePaper / pagesPerSecond) * 1000);
        long timeAfter = getTimeMillis() - timeBefore;
        int leftOverPages = publication.getPages() - availablePaper;
        PrinterHandler.Printer printer = (PrinterHandler.Printer) Thread.currentThread();
        try {
            if (availablePaper < publication.getPages()) {
                Thread.sleep(timeBefore);
                printer.decreaseCurrentPaperQuantity(availablePaper);
                throw new OutOfPaperException(Thread.currentThread().getName() + " is out of paper. Max Capacity: "
                        + PrinterSettings.getMaxPaperQuantity());

            } else {
                Thread.sleep(getTimeMillis());
                printer.decreaseCurrentPaperQuantity(publication.getPages());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (OutOfPaperException e) {
            try {
                printer.refill();
                Thread.sleep(timeAfter);
                printer.decreaseCurrentPaperQuantity(leftOverPages);

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        currentCopy++;
        System.out.println(Thread.currentThread().getName() + " finished printing " +
                publication.getTitle() + " - copy " + currentCopy);
    }

    private long getTimeMillis() {
        return Math.round(getTime() * 1000);
    }

    public void setTime() {
        pagesPerSecond = PrinterSettings.getPagesPerMinute() / 60.0;
    }

    public double getTime() {
        return publication.getPages() / pagesPerSecond;
    }

    public void setAvailablePaper(int currentPaperQuantity) {
        this.availablePaper = currentPaperQuantity;
    }

    @Override
    public String toString() {
        return "{" + publication +
                ", copies=" + quantity +
                ", printMode=" + printMode +
                "}";
    }
}
