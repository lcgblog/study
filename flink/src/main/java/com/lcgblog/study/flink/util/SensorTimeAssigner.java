package com.lcgblog.study.flink.util;

import com.lcgblog.study.flink.models.SensorReading;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class SensorTimeAssigner extends BoundedOutOfOrdernessTimestampExtractor<SensorReading> {

    public SensorTimeAssigner() {
        super(Time.seconds(5));
    }

    @Override
    public long extractTimestamp(SensorReading r) {
        return r.getTimestamp();
    }
}
