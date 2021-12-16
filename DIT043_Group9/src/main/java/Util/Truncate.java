package Util;

public class Truncate {

    public static double truncateValue(double value, int decimalPoints){
        final double TRUNCATING_TOOL = 10.0;
        double truncatedValue = ( (int) (value * Math.pow(TRUNCATING_TOOL, decimalPoints)) ) / Math.pow(TRUNCATING_TOOL, decimalPoints);
        return truncatedValue;
    }
}
