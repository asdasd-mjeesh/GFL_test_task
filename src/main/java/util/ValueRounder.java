package util;

import java.text.DecimalFormat;

public final class ValueRounder {

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.###");

    private ValueRounder(DecimalFormat decimalFormat) {  }

    public static Double round(Double value) {
        String roundedValue = decimalFormat.format(value);
        roundedValue = roundedValue.replace(",", ".");
        return Double.parseDouble(roundedValue);
    }
}
