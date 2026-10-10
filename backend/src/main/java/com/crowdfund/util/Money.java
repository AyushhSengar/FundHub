package com.crowdfund.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.http.HttpStatus;

import com.crowdfund.exception.AppException;

/** Money is handled with BigDecimal (2 decimals) so totals never drift. Standard currency is INR (₹). */
public final class Money {

    public static final String CURRENCY_CODE = "INR";
    public static final String CURRENCY_SYMBOL = "₹";
    public static final Locale LOCALE_IN = Locale.forLanguageTag("en-IN");

    private static final BigDecimal MAX = new BigDecimal("1000000000");

    private Money() {
    }

    public static BigDecimal parsePositive(BigDecimal value, String label) {

        if (value == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, label + " is required.");
        }

        if (value.signum() <= 0) {
            throw new AppException(HttpStatus.BAD_REQUEST, label + " must be greater than zero.");
        }

        if (value.stripTrailingZeros().scale() > 2) {
            throw new AppException(HttpStatus.BAD_REQUEST,
                    label + " can have at most 2 decimal places.");
        }

        if (value.compareTo(MAX) > 0) {
            throw new AppException(HttpStatus.BAD_REQUEST, label + " is too large.");
        }

        return value.setScale(2, RoundingMode.UNNECESSARY);
    }

    /** Reads a number stored in Firestore (Long or Double) as a 2-decimal BigDecimal. */
    public static BigDecimal fromFirestore(Object raw) {

        if (raw instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue()).setScale(2, RoundingMode.HALF_UP);
        }

        return BigDecimal.ZERO.setScale(2);
    }

    /** Formats a monetary amount into standard Indian Rupee (₹) representation with Indian digit grouping. */
    public static String formatINR(BigDecimal amount) {
        if (amount == null) {
            return CURRENCY_SYMBOL + "0.00";
        }
        BigDecimal scaled = amount.setScale(2, RoundingMode.HALF_UP);
        long wholePart = scaled.longValue();
        int cents = scaled.remainder(BigDecimal.ONE).movePointRight(2).abs().intValue();

        String s = Long.toString(Math.abs(wholePart));
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        if (len <= 3) {
            sb.append(s);
        } else {
            String lastThree = s.substring(len - 3);
            String remaining = s.substring(0, len - 3);
            StringBuilder remSb = new StringBuilder();
            int rLen = remaining.length();
            for (int i = 0; i < rLen; i++) {
                if (i > 0 && (rLen - i) % 2 == 0) {
                    remSb.append(",");
                }
                remSb.append(remaining.charAt(i));
            }
            sb.append(remSb).append(",").append(lastThree);
        }
        String sign = (wholePart < 0 || scaled.signum() < 0) ? "-" : "";
        return String.format("%s%s%s.%02d", sign, CURRENCY_SYMBOL, sb.toString(), cents);
    }
}

