package BusinessLogic;

import Util.Truncate;

public class Manager extends Employee {


    private final double MANGER_TAX =0.10;
    private final double BSc_BONUS = 1.10;
    private final double MSc_BONUS = 1.20;
    private final double PhD_BONUS = 1.35;

    private String degree;

    public Manager(String employeeID, String employeeName, double salary, String degree) throws Exception{
        super(employeeID, employeeName, salary);

        if (degree.equals("BSc") || degree.equals("MSc") || degree.equals("PhD")){
            this.degree = degree;
        } else {
            throw new Exception("Degree must be one of the options: BSc, MSc or PhD.");
        }
    }


    public String getDegree(){
        return this.degree;
    }

    public void setDegree(String newDegree) throws Exception{
        if (newDegree.equals("BSc") || newDegree.equals("MSc") || newDegree.equals("PhD")){
            this.degree = newDegree;
        } else {
            throw new Exception("Degree must be one of the options: BSc, MSc or PhD.");
        }
    }

    public double getGrossSalary(){
        double salary = (super.getGrossSalary() * this.calculateDegreeBonus());
        return salary;
    }

    @Override
    public double getNetSalary() {
        double salary = this.getGrossSalary();
        double discount = salary * MANGER_TAX;
        double netSalary = salary - discount;
        return netSalary;
    }

    private double calculateDegreeBonus(){
        double degreeBonus = 0.0;
        if (this.degree.equals("BSc")){
            degreeBonus = BSc_BONUS;
        } else if (this.degree.equals("MSc")){
            degreeBonus = MSc_BONUS;
        } else if (this.degree.equals("PhD")){
            degreeBonus = PhD_BONUS;
        }
        return degreeBonus;
    }

    @Override
    public String toString(){
        return this.degree + " " + super.getEmployeeName() + "'s gross salary is " + String.format("%.2f", Truncate.truncateValue(this.getGrossSalary(), 2)) + " SEK per month.";
    }
}
