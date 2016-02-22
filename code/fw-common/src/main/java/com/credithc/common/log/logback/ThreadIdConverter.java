package com.credithc.common.log.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class ThreadIdConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        try {
            return String.valueOf(Thread.currentThread().getId());
        } catch (Exception e) {
            return "";
        }
    }

}
