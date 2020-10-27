package com.lcgblog.study.flink.chapter5;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Collections;

public class SplitStreamTest {
    public static void main(String[] args)throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000L);

        DataStreamSource<Tuple2<Integer, String>> dataStreamSource = env.fromElements(Tuple2.of(1, "a"), Tuple2.of(2, "b"), Tuple2.of(3, "c"));
        SplitStream<Tuple2<Integer, String>> splitStream = dataStreamSource.split(new OutputSelector<Tuple2<Integer, String>>() {
            @Override
            public Iterable<String> select(Tuple2<Integer, String> value) {
                if (value.f0 > 1) {
                    return Collections.singletonList("large");
                } else {
                    return Collections.singletonList("small");
                }
            }
        });
        DataStream<Tuple2<Integer, String>> large = splitStream.select("large");
        large.print();
        env.execute("SplitStream");
    }
}
