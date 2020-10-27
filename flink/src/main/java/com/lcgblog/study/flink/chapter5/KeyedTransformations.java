package com.lcgblog.study.flink.chapter5;

import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.sources.SensorSource;
import com.lcgblog.study.flink.util.SensorTimeAssigner;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KeyedTransformations {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000L);

        SingleOutputStreamOperator<SensorReading> sensorData = env.addSource(new SensorSource())
                .assignTimestampsAndWatermarks(new SensorTimeAssigner());
        KeyedStream<SensorReading, String> keyedSensorData = sensorData.keyBy(SensorReading::getId);
        SingleOutputStreamOperator<SensorReading> output = keyedSensorData.reduce(new ReduceFunction<SensorReading>() {
            @Override
            public SensorReading reduce(SensorReading value1, SensorReading value2) throws Exception {
                if (value1.getTemperature() > value2.getTemperature()) {
                    return value1;
                } else {
                    return value2;
                }
            }
        }).filter(new FilterFunction<SensorReading>() {
            @Override
            public boolean filter(SensorReading value) throws Exception {
                return value.getId().equals("sensor_1");
            }
        });

        output.print();
        env.execute("KeyedTransformations");
    }
}
