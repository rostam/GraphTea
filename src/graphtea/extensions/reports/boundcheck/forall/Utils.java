package graphtea.extensions.reports.boundcheck.forall;

/**
 * Created by rostam on 30.09.15.
 * @author M. Ali Rostami
 */
public class Utils {
    public static double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0)
            power_of_ten *= 10.0;
        return Math.round(value * power_of_ten)
                / power_of_ten;
    }

    public static double[] round(double[] array, int prec) {
        for (int i = 0; i < array.length; i++)
            array[i] = round(array[i], prec);
        return array;

    }
}
