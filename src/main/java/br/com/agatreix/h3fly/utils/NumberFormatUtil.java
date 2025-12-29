package br.com.agatreix.h3fly.utils;

import java.text.DecimalFormat;

public class NumberFormatUtil {

    private static final String[] SUFFIX = {
            "",
            "k",
            "M",
            "B",
            "T",
            "Q",
            "QQ",
            "S",
            "SS",
            "OC",
            "N",
            "D",
            "UN",
            "DD",
            "TR",
            "QT",
            "QN",
            "SD",
            "SPD",
            "OD",
            "ND",
            "VG",
            "UVG",
            "DVG",
            "TVG",
            "QTV",
            "QNV",
            "SEV",
            "SPV",
            "OVG",
            "NVG",
            "TG"
    };

    public static String format(double value) {
        int index = 0;

        while (value >= 1000 && index < SUFFIX.length - 1) {
            value /= 1000;
            index++;
        }

        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(value) + SUFFIX[index];
    }
}
