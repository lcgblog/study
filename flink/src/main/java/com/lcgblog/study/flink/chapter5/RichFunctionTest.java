package com.lcgblog.study.flink.chapter5;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class RichFunctionTest {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000L);

        DataStreamSource<Integer> dataStreamSource = env.fromElements(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);

        SingleOutputStreamOperator<Object> output = dataStreamSource.flatMap(new RichFlatMapFunction<Integer, Object>() {

            private int subTaskIndex = 0;

            @Override
            public void open(Configuration parameters) throws Exception {
                subTaskIndex = getRuntimeContext().getIndexOfThisSubtask();
//                System.out.println(subTaskIndex);
            }

            @Override
            public void flatMap(Integer value, Collector<Object> out) throws Exception {
//                System.out.println(subTaskIndex + ":" + value);
                System.out.println(getRuntimeContext().getNumberOfParallelSubtasks());
                if (value % 2 == subTaskIndex) {
                    out.collect(Tuple2.of(subTaskIndex, value));
                }
            }
        });
        output.print();
        env.execute("RichFunctionTest");
    }
}
