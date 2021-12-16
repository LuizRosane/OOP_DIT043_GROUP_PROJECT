package BusinessLogic;

import Util.Truncate;

import java.util.Objects;

public class Employee implements Comparable<Employee>{
    private final double EMPLOYEE_TAX = 0.1;

    final private String employeeID;
    private String employeeName;
    private double salary;

    public Employee(String employeeID, String employeeName, double employeeSalary) throws Exception{
        if (employeeID.isBlank()){
            throw new Exception("ID cannot be blank.");
        }

        if (employeeName.isBlank()){
            throw new Exception("Name cannot be blank.");
        }

        if (employeeSalary <= 0.0){
            throw new Exception("Salary must be greater than zero.");
        }

        this.employeeID = employeeID;
        this.employeeName = employeeName;

        employeeSalary = Truncate.truncateValue(employeeSalary, 2);
        this.salary = employeeSalary;
    }

    public String getEmployeeID(){
        return this.employeeID;
    }

    public String getEmployeeName(){
        return this.employeeName;
    }

    public double getRawSalary(){
        return this.salary;
    }

    public double getGrossSalary(){
        return this.getRawSalary();
    }

    public void setEmployeeName(String newEmployeeName) throws Exception{
        if (newEmployeeName.isBlank()){
            throw new Exception("Name cannot be blank.");
        }
        this.employeeName = newEmployeeName;
    }

    public void setSalary(double newEmployeeSalary) throws Exception{
        if (newEmployeeSalary <= 0.0){
            throw new Exception("Salary must be greater than zero.");
        }
        this.salary = newEmployeeSalary;
    }

    public double getNetSalary(){
        double employeeNetSalary = this.getGrossSalary() - (this.getGrossSalary() * EMPLOYEE_TAX);
        return  employeeNetSalary;
    }

    @Override
    public String toString(){
        return this.employeeName + "'s gross salary is " + String.format("%.2f", Truncate.truncateValue(this.getGrossSalary(), 2)) + " SEK per month.";
    }

    @Override
    public boolean equals(Object anotherObject){
        if (this == anotherObject){
            return true;
        } else if (anotherObject == null){
            return false;
        } else if (anotherObject instanceof Employee){
            Employee anotherEmployee = (Employee) anotherObject;

            if (this.employeeID.equals(anotherEmployee.getEmployeeID())){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode(){
        int hash = Objects.hash(this.employeeID);
        return hash;
    }

    public int compareTo(Employee anotherEmployee){
        if (this.getGrossSalary() == anotherEmployee.getGrossSalary()){
            return 0;
        } else if (this.getGrossSalary() > anotherEmployee.getGrossSalary()){
            return 1;
        } else {
            return -1;
        }
    }



}
