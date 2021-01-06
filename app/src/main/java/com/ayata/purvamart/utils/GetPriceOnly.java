package com.ayata.purvamart.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetPriceOnly {
    //Rs 100.00 to 100.00
    Double getPriceOnly(String textPrice) {
        Pattern PRICE_PATTERN = Pattern.compile("(\\d*\\.)?\\d+");
        Matcher matcher = PRICE_PATTERN.matcher(textPrice);
        while (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }
        return 1.00;
    }
}
