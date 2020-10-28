package com.lcgblog.study.flink.chapter6;

import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.sources.SensorSource;
import com.lcgblog.study.flink.util.SensorTimeAssigner;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

public class SideOutputs {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getCheckpointConfig().setCheckpointInterval(10 * 1000);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000L);

        SingleOutputStreamOperator<SensorReading> readings = env.addSource(new SensorSource()).assignTimestampsAndWatermarks(new SensorTimeAssigner());
        SingleOutputStreamOperator<SensorReading> monitorReadings = readings.process(new FreezingMonitor());
        monitorReadings.getSideOutput(new OutputTag<String>("freezing-alarms", TypeInformation.of(String.class))).print();

        readings.print();

        env.execute("SideOutputs");
    }
}

class FreezingMonitor extends ProcessFunction<SensorReading, SensorReading> {

    private OutputTag<String> freezingAlarmOutput;

    @Override
    public void open(Configuration parameters) throws Exception {
        freezingAlarmOutput = new OutputTag<>("freezing-alarms",TypeInformation.of(String.class));
    }

    @Override
    public void processElement(SensorReading r, Context ctx, Collector<SensorReading> out) throws Exception {
        if (r.getTemperature() < 32.0) {
            System.out.println("emit side output " + r.getId());
            ctx.output(freezingAlarmOutput, "Freezing alarm for " + r.getId());
        }
        out.collect(r);
    }
}
