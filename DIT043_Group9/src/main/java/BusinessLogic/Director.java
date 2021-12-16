package BusinessLogic;

import Util.Truncate;

public class Director extends Manager{

    private final double DIRECTOR_TAX_LESS_THAN_30K = 0.10;
    private final double DIRECTOR_TAX_BETWEEN_30K_50K = 0.20;
    private final double DIRECTOR_TAX_MORE_THAN_50K = 0.40;
    private final double DIRECTOR_30K = 30000;

    private final double ADDITIONAL_SALARY = 5000.00;


    private String department;

    public Director(String employeeID, String employeeName, double employeeGrossSalary, String degree, String dept) throws Exception {
        super(employeeID, employeeName, employeeGrossSalary, degree);

        if (dept.equals("Business") || dept.equals("Human Resources") || dept.equals("Technical")){
            this.department = dept;
        } else {
            throw new Exception("Department must be one of the options: Business, Human Resources or Technical.");
        }
    }

    public String getDepartment(){
        return this.department;
    }

    public void setDepartment(String newDepartment) throws Exception{
        if (newDepartment.equals("Business") || newDepartment.equals("Human Resources") || newDepartment.equals("Technical")){
            this.department = newDepartment;
        } else {
            throw new Exception("Department must be one of the options: Business, Human Resources or Technical.");
        }
    }

    @Override
    public double getGrossSalary(){
        double salary = super.getGrossSalary() + ADDITIONAL_SALARY;
        return salary;
    }

    @Override
    public double getNetSalary(){
        double salary = this.getGrossSalary();
        double tax = 0.0;

        if (salary < 30000){
            double discount = this.getGrossSalary() * DIRECTOR_TAX_LESS_THAN_30K;
            return salary - discount;

        } else if (salary >= 30000 && salary <= 50000){
            double discount = this.getGrossSalary() * DIRECTOR_TAX_BETWEEN_30K_50K;
            return salary - discount;

        } else {
            return salary - (DIRECTOR_30K * DIRECTOR_TAX_BETWEEN_30K_50K) - ((salary-DIRECTOR_30K) * DIRECTOR_TAX_MORE_THAN_50K);
        }

    }

    @Override
    public String toString(){
        return super.getDegree() + " " + super.getEmployeeName() + "'s gross salary is " +
                String.format("%.2f", Truncate.truncateValue(this.getGrossSalary(), 2)) +
                " SEK per month. Dept: " + this.department;
    }


}
