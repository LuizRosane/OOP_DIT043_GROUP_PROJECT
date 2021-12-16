package BusinessLogic;

public class Intern extends Employee {

    private final double ACADEMIC_BONUS = 1000.00;

    private int gpa;

    public Intern (String internID, String internName, double internGrossSalary, int gpa) throws Exception {

        super(internID, internName, internGrossSalary);
        this.gpa = gpa;

        if (this.gpa < 0 || this.gpa > 10) {
            throw new Exception(this.gpa + " outside range. Must be between 0-10.");
        }
    }

    public double getGpa(){
        return this.gpa;
    }

    public void setGpa(int gpa) { this.gpa = gpa; }

    @Override
    public double getGrossSalary() {

        double grossSalary = 0.0;

        if (gpa > 5 && gpa < 8) {
            grossSalary = super.getGrossSalary();
        } else if (gpa >= 8) {
            grossSalary = super.getGrossSalary() + ACADEMIC_BONUS;
        } else {
            grossSalary = 0.0;
        }
        return grossSalary;
    }

    @Override
    public double getNetSalary(){
        return this.getGrossSalary();
    }


    @Override
    public String toString() {
        return super.getEmployeeName() + "'s gross salary is " + String.format("%.2f", getGrossSalary()) + " SEK per month. GPA: " + this.gpa;
    }



}

