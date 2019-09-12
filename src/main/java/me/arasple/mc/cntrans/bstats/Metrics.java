package me.arasple.mc.cntrans.bstats;

import io.izzel.taboolib.module.inject.TSchedule;
import me.arasple.mc.cntrans.CNTrans;

/**
 * @author Arasple
 */
public class Metrics {

    private static MetricsBukkit metrics;

    @TSchedule
    public static void init() {
        metrics = new MetricsBukkit(CNTrans.getPlugin());
    }

    public static MetricsBukkit getMetrics() {
        return metrics;
    }

}