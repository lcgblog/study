package com.lcgblog.study.flink.chapter5;

import com.lcgblog.study.flink.models.Alert;
import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.models.SmokeLevel;
import com.lcgblog.study.flink.operators.RaiseAlertFlatMap;
import com.lcgblog.study.flink.sources.SensorSource;
import com.lcgblog.study.flink.sources.SmokeLevelSource;
import com.lcgblog.study.flink.util.SensorTimeAssigner;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class MultiStreamTransformations {
    public static void main(String[] args) throws Exception {
        LocalStreamEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000L);

        SingleOutputStreamOperator<SensorReading> tempReadings =
                env.addSource(new SensorSource())
                   .assignTimestampsAndWatermarks(new SensorTimeAssigner());

        SingleOutputStreamOperator<SmokeLevel> smokeReadings =
                env.addSource(new SmokeLevelSource())
                   .setParallelism(1);

        KeyedStream<SensorReading, String> keyedTempReadings = tempReadings.keyBy(SensorReading::getId);
        SingleOutputStreamOperator<Alert> alerts = keyedTempReadings.connect(smokeReadings.broadcast())
                .flatMap(new RaiseAlertFlatMap());
        alerts.print();
        env.execute("Multi-Stream Transformations Example");
    }
}
