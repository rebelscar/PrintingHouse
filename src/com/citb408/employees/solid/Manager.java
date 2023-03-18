package com.citb408.employees.solid;

import com.citb408.employees.abstraction.Employee;
import com.citb408.employees.abstraction.EmployeeType;

public class Manager extends Employee {
    private boolean bonus;

    public Manager() {
        super(EmployeeType.MANAGER);
        bonus = false;
    }

    @Override
    public double getSalary() {
        if(bonus)
            return Employee.getBaseSalary() + calculateBonus();
        else
            return Employee.getBaseSalary();
    }

    private double calculateBonus() {
        return (ManagerBonus.getPercentage() / 100) * Employee.getBaseSalary();
    }

    public void setBonus(boolean isOverLimit) {
        bonus = isOverLimit;
    }
}
