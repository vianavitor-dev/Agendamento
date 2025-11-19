package com.devops.agendamento.utils;

import org.springframework.cglib.core.Local;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class SimpleCardTranslator {
    private static String getDayNameInPtBr(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayPtBr = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.of("pt", "BR"));

        return StringUtils.capitalize(dayPtBr).replace("-feira", "");
    }

    public static String getIntervalInPtBr(LocalDate start, LocalDate end) {
        // Verifica se o cliente agendou ápenas 1 dia ou não
        boolean isOnlyOneDay = start.isEqual(end);

        // Recebe a data marcada como dias da semana
        String startDayName = SimpleCardTranslator.getDayNameInPtBr(start);
        String endDayName = SimpleCardTranslator.getDayNameInPtBr(end);

        // Formata os dias da semana para informar os dias agendados
        String interval = isOnlyOneDay
                ? "Apenas %s".formatted(startDayName)
                : "%s até %s".formatted(startDayName, endDayName);

        return interval;
    }
}
