package com.lcgblog.study.flink.chapter6;

import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.sources.SensorSource;
import com.lcgblog.study.flink.util.SensorTimeAssigner;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class KeyedProcessFunctionTest {
    public static void main(String[] args)throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(2000L);

        SingleOutputStreamOperator<SensorReading> datasource = env.addSource(new SensorSource()).assignTimestampsAndWatermarks(new SensorTimeAssigner());

        datasource.keyBy(SensorReading::getId)
                .process(new KeyedProcessFunction<String, SensorReading, Tuple2<String,Double>>() {
                    @Override
                    public void onTimer(long timestamp, OnTimerContext ctx, Collector<Tuple2<String, Double>> out) throws Exception {
                        System.out.println(Thread.currentThread() + " - "+ timestamp);
                        System.out.println(ctx.getCurrentKey());
                        System.out.println(ctx.timeDomain());
                    }

                    @Override
                    public void processElement(SensorReading value, Context ctx, Collector<Tuple2<String, Double>> out) throws Exception {
                        if(value.getId().equals("sensor_69")){
                            System.out.println(Thread.currentThread().getName() +" current watermark"+ctx.timerService().currentWatermark());
                            ctx.timerService().registerEventTimeTimer(value.getTimestamp() + 20000);// 20 seconds later to trigger
                        }
                    }
                }).print();
        env.execute("KeyedProcessFunctionTest");
    }
}
