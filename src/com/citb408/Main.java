package com.citb408;

import com.citb408.employees.abstraction.*;
import com.citb408.employees.solid.*;
import com.citb408.printing.abstraction.PrintMode;
import com.citb408.printing.solid.*;
import com.citb408.publications.abstraction.*;
import com.citb408.publications.solid.*;
import com.citb408.utilities.MoneyHandler;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright 2022 Georgi Angelov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Main {

    public static void main(String[] args) {
        // Money
        MoneyHandler moneyHandler = new MoneyHandler();

        // Employees
        List<Employee> employees = new ArrayList<>();
        Employee e1 = new MachineOperator();
        Employee e2 = new MachineOperator();
        Employee e3 = new Manager();
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        EmployeeHandler employeeHandler = new EmployeeHandler(employees, moneyHandler);

        // Publications
        Publication book1 = new Book("The Shining", 333);
        Publication book2 = new Book("IT", 312);
        Publication book3 = new Book("Structure and Interpretation of Computer Programs", 687);
        Publication newspaper1 = new Newspaper("NOVA 31.05.22", 43);
        Publication newspaper2 = new Newspaper("BTV 05.06.22", 39);
        Publication poster1 = new Poster("Slipknot", 1);

        // Print Tasks
        PrinterHandler printerHandler = new PrinterHandler(3, moneyHandler);
        printerHandler.addPublication(book1, 3);
        printerHandler.addPublication(book2, 4, PrintMode.COLORED);
        printerHandler.addPublication(book3, 2);
        printerHandler.addPublication(newspaper1, 10);
        printerHandler.addPublication(newspaper2, 8, PrintMode.COLORED);
        printerHandler.addPublication(poster1, 15, PrintMode.COLORED);

        // Main control
        PrintingHouse printingHouse = new PrintingHouse(employeeHandler, printerHandler, moneyHandler);

        printingHouse.start();
        printingHouse.saveToFile();
        printingHouse.readFromFile();
    }
}
