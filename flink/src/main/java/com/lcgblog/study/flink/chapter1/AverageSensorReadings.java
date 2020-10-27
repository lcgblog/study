package com.lcgblog.study.flink.chapter1;

import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.operators.TemperatureAverager;
import com.lcgblog.study.flink.sources.SensorSource;
import com.lcgblog.study.flink.util.SensorTimeAssigner;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

public class AverageSensorReadings {

    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000L);

        DataStream<SensorReading> sensorData =
                env.addSource(new SensorSource(), TypeInformation.of(SensorReading.class))
                   .assignTimestampsAndWatermarks(new SensorTimeAssigner());
        // convert Fahrenheit to Celsius using an inlined map function
        sensorData.map(new MapFunction<SensorReading, SensorReading>() {
            @Override
            public SensorReading map(SensorReading r) throws Exception {
                return new SensorReading(r.getId(),r.getTimestamp(), (r.getTemperature() - 32) * (5.0 / 9.0));
            }
        })
        // organize stream by sensorId
        .keyBy(new KeySelector<SensorReading, String>() {
            @Override
            public String getKey(SensorReading value) throws Exception {
                return value.getId();
            }
        },TypeInformation.of(String.class))
        // group readings in 1 second windows
        .timeWindow(Time.seconds(1))
        .apply(new TemperatureAverager());
        sensorData.print();
        env.execute("AverageSensorReadings");
    }
}
