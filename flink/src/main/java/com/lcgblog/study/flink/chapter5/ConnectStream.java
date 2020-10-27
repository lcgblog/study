package com.lcgblog.study.flink.chapter5;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Map;

public class ConnectStream {
    public static void main(String[] args)throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000L);

        DataStreamSource<Tuple2<Integer, String>> source1 = env.fromElements(Tuple2.of(1, "a"), Tuple2.of(2, "b"), Tuple2.of(3, "c"));
        DataStreamSource<Tuple2<Integer, Long>> source2 = env.fromElements(Tuple2.of(1, 2L), Tuple2.of(2, 4L), Tuple2.of(3, 6L));

//        ConnectedStreams<Tuple2<Integer, String>, Tuple2<Integer, Long>> connectedStreams = source1.connect(source2).keyBy(0,0);
        ConnectedStreams<Tuple2<Integer, String>, Tuple2<Integer, Long>> connectedStreams = source1.connect(source2.broadcast());
        SingleOutputStreamOperator<Object> output = connectedStreams.flatMap(new CoFlatMapFunction<Tuple2<Integer, String>, Tuple2<Integer, Long>, Object>() {

            private Map<Integer, Tuple2<Integer, String>> value1Map = new HashMap<>();
            private Map<Integer, Tuple2<Integer, Long>> value2Map = new HashMap<>();

            @Override
            public void flatMap1(Tuple2<Integer, String> value, Collector<Object> out) throws Exception {
                value1Map.put(value.f0, value);
                if (value2Map.containsKey(value.f0)) {
                    out.collect(Tuple2.of(value.f0, value.f1 + value2Map.get(value.f0).f1));
                }
            }

            @Override
            public void flatMap2(Tuple2<Integer, Long> value, Collector<Object> out) throws Exception {
                value2Map.put(value.f0, value);
                if (value1Map.containsKey(value.f0)) {
                    out.collect(Tuple2.of(value.f0, value.f1 + value1Map.get(value.f0).f1));
                }
            }
        });
        output.print();
        env.execute("ConnectStream");
    }
}
