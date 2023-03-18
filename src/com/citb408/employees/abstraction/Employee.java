package com.citb408.employees.abstraction;

public abstract class Employee {
    private static final double BASE_SALARY = 150;
    private final EmployeeType employeeType;

    protected Employee(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public static double getBaseSalary() {
        return BASE_SALARY;
    }

    public abstract double getSalary();
}
