package com.citb408.employees.solid;

import com.citb408.employees.abstraction.*;

public class MachineOperator extends Employee {

    public MachineOperator() {
        super(EmployeeType.MACHINE_OPERATOR);
    }

    @Override
    public double getSalary() {
        return Employee.getBaseSalary();
    }
}
