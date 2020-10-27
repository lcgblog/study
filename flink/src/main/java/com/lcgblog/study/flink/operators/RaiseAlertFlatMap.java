package com.lcgblog.study.flink.operators;

import com.lcgblog.study.flink.models.Alert;
import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.models.SmokeLevel;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.util.Collector;

public class RaiseAlertFlatMap implements CoFlatMapFunction<SensorReading, SmokeLevel, Alert> {

    private SmokeLevel smokeLevel = SmokeLevel.Low;

    @Override
    public void flatMap1(SensorReading value, Collector<Alert> out) throws Exception {
        if(smokeLevel.equals(SmokeLevel.High) && value.getTemperature() > 100){
            out.collect(Alert.of("Risk of fire", value.getTimestamp()));
        }
    }

    @Override
    public void flatMap2(SmokeLevel value, Collector<Alert> out) throws Exception {
        smokeLevel = value;
    }
}
