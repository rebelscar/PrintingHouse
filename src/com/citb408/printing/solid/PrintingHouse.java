package com.citb408.printing.solid;

import com.citb408.employees.abstraction.Employee;
import com.citb408.employees.solid.EmployeeHandler;
import com.citb408.utilities.MoneyHandler;

import java.io.*;
import java.util.List;

public class PrintingHouse {
    private final EmployeeHandler employeeHandler;
    private final PrinterHandler printerHandler;
    private final MoneyHandler moneyHandler;
    private final String filePath;
    private Thread ph;

    public PrintingHouse(EmployeeHandler employeeHandler, PrinterHandler printerHandler, MoneyHandler moneyHandler) {
        this(employeeHandler, printerHandler, moneyHandler, "src/com/citb408/save/save.ser");
    }

    public PrintingHouse(EmployeeHandler employeeHandler, PrinterHandler printerHandler, MoneyHandler moneyHandler, String filePath) {
        this.employeeHandler = employeeHandler;
        this.printerHandler = printerHandler;
        this.moneyHandler = moneyHandler;
        this.filePath = filePath;
        this.ph = null;
    }

    public PrinterHandler getPrinterHandler() {
        return printerHandler;
    }

    public boolean addEmployee(Employee employee) {
        return employeeHandler.hire(employee);
    }

    public boolean addPublication(PrintTask task) {
        return printerHandler.addTask(task);
    }

    public void start() {
        ph = new Thread(printerHandler, "Printer Handler");
        ph.start();
        try {
            ph.join();
        } catch (InterruptedException e) {
            System.out.println("The " + ph.getName() + " was interrupted...");
            e.printStackTrace();
        }
        employeeHandler.salariesCost();
    }


//    public void saveToFile() {
//        List<PrintTask> tasks = printerHandler.getTasks();
//
//        try (FileOutputStream fos = new FileOutputStream(filePath);
//            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//
//            for (PrintTask task : tasks) {
//                oos.writeObject(task);
//            }
//
//            oos.writeObject(moneyHandler);
//        } catch (IOException e) {
//            System.err.println(e);
//        }
//    }

//    public void readFromFile() {
//        try (FileInputStream fis = new FileInputStream(filePath);
//             ObjectInputStream ois = new ObjectInputStream(fis)) {
//
//            PrintTask tempTask;
//            for(int i = 0; i < printerHandler.getTasks().size(); i++) {
//                tempTask = (PrintTask) ois.readObject();
//                System.out.println(tempTask);
//            }
//
//            MoneyHandler tempMoneyHandler = (MoneyHandler) ois.readObject();
//            System.out.println(tempMoneyHandler);
//
//        } catch (IOException | ClassNotFoundException e) {
//            System.err.println(e);
//        }
//    }

    // These weren't the original, but they make it easier and are better
    // because you can run reading independently of writing, as it should be
    public void saveToFile() {
        List<PrintTask> tasks = printerHandler.getTasks();

        try (FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(tasks);
            oos.writeObject(moneyHandler);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void readFromFile() {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            List<PrintTask> tasks = null;
            tasks = (List<PrintTask>) ois.readObject();
            MoneyHandler tempMoneyHandler = (MoneyHandler) ois.readObject();

            for (PrintTask task : tasks) {
                System.out.println(task);
            }

            System.out.println(tempMoneyHandler);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        }
        // The thread pool has to be shut down if the command is run alone
        // because it initialises, and they're all waiting so the program never ends
        // so we manually close the thread pool
        if (ph == null){
            try {
                printerHandler.shutdown();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }
}
