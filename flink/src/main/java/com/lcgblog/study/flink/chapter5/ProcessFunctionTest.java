package com.lcgblog.study.flink.chapter5;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.util.Collections;

public class ProcessFunctionTest {

    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000L);

        DataStreamSource<Tuple2<Integer, String>> dataStreamSource = env.fromElements(Tuple2.of(1, "a"), Tuple2.of(2, "b"), Tuple2.of(3, "c"));
        SingleOutputStreamOperator<Object> output = dataStreamSource.process(new ProcessFunction<Tuple2<Integer, String>, Object>() {
            @Override
            public void processElement(Tuple2<Integer, String> value, Context ctx, Collector<Object> out) throws Exception {
                out.collect(value);
                System.out.println("process:"+value);
            }
        });
        output.print();
        env.execute("ProcessFunctionTest");
    }
}
