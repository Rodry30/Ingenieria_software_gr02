package com.foodgest.shared.utils;

import java.text.Normalizer;
import java.util.Locale;

public class SlugGenerator {

    private SlugGenerator() {}

    public static String generate(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        return normalized.replaceAll("-{2,}", "-");
    }
}

