package com.lcgblog.study.flink.chapter6;

import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.sources.SensorSource;
import com.lcgblog.study.flink.util.SensorTimeAssigner;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.List;

public class StateTest {
    public static void main(String[] args)throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        SingleOutputStreamOperator<SensorReading> dataSource = env.addSource(new SensorSource()).assignTimestampsAndWatermarks(new SensorTimeAssigner());
        dataSource.keyBy(SensorReading::getId).timeWindow(Time.seconds(5))
                .process(new ProcessWindowFunction<SensorReading, List<String>, String, TimeWindow>() {

                    private ValueState<String> key;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        key = getRuntimeContext().getState(new ValueStateDescriptor<String>("key",String.class));
                        System.out.println(Thread.currentThread().getName() + " - open");
                    }

                    @Override
                    public void process(String id, Context context, Iterable<SensorReading> elements, Collector<List<String>> out) throws Exception {
                        System.out.println(Thread.currentThread().getName() + " - " + id + " - " + key.value());
                        if(id.equals("sensor_5")){
                            key.update(Thread.currentThread().getName() + " -  haha");
                        }
                    }
                }).print();
        env.execute("StateTest");
    }
}
