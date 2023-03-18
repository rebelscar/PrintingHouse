package com.citb408.employees.solid;

import com.citb408.employees.abstraction.Employee;
import com.citb408.employees.abstraction.EmployeeType;
import com.citb408.utilities.MoneyHandler;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeHandler {
    private final List<Employee> employees;
    private final MoneyHandler moneyHandler;
    private boolean profitAboveLimit;

    public EmployeeHandler(List<Employee> employees, MoneyHandler moneyHandler) {
        this.employees = employees;
        this.moneyHandler = moneyHandler;
        this.profitAboveLimit = false;
    }

    public void salariesCost() {
        checkIncome();

        double total = 0;
        for(Employee employee : employees) {
            total += employee.getSalary();
        }
        moneyHandler.addExpenses(total);
    }

    private void modifyManagerSalary(boolean flag) {
        Manager manager;
        for(Employee employee : employees) {
            if(employee.getEmployeeType() == EmployeeType.MANAGER) {
                manager = (Manager) employee;
                manager.setBonus(flag);
            }
        }
        profitAboveLimit = flag;
    }

    private void addBonus() {
        if(!profitAboveLimit)
            modifyManagerSalary(true);
    }

    private void removeBonus() {
        if(profitAboveLimit)
            modifyManagerSalary(false);
    }

    private void checkIncome() {
        BigDecimal limit = BigDecimal.valueOf(ManagerBonus.getLimit());

        if(moneyHandler.getProfit().compareTo(limit) > 0)
            addBonus();
        else
            removeBonus();
    }

    public boolean hire(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            this.employees.add(employee);
            return true;
        }
        return false;
    }

    public boolean fire(Employee employee) {
        if (employee != null && employees.contains(employee)) {
            this.employees.remove(employee);
            return true;
        }
        return false;
    }


}
