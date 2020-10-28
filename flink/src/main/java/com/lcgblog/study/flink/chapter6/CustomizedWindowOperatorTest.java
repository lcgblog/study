package com.lcgblog.study.flink.chapter6;

import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.sources.SensorSource;
import com.lcgblog.study.flink.util.SensorTimeAssigner;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.evictors.Evictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.EventTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.streaming.runtime.operators.windowing.TimestampedValue;
import org.apache.flink.util.Collector;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomizedWindowOperatorTest {

    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.getConfig().setAutoWatermarkInterval(1000L);

        SingleOutputStreamOperator<SensorReading> readings = env.addSource(new SensorSource()).assignTimestampsAndWatermarks(new SensorTimeAssigner());

        readings.keyBy("id").window(new MyWindowAssigner()).process(new ProcessWindowFunction<SensorReading, Object, Tuple, TimeWindow>() {
            @Override
            public void process(Tuple key, Context context, Iterable<SensorReading> elements, Collector<Object> out) throws Exception {
                System.out.println("--start--"+key);
                elements.forEach(System.out::println);
            }
        }).setParallelism(1).print();

        env.execute();
    }

}
class MyWindowAssigner extends WindowAssigner<Object, TimeWindow>{

    @Override
    public List<TimeWindow> assignWindows(Object element, long timestamp, WindowAssignerContext context) {
        long windowSize = 5 * 1000L;
        long startTimestamp = timestamp - (timestamp % windowSize);
        long endTimestamp = startTimestamp + windowSize;
        System.out.println("assginWindow " + element);
        return Collections.singletonList(new TimeWindow(startTimestamp,endTimestamp));
    }

    @Override
    public Trigger<Object, TimeWindow> getDefaultTrigger(StreamExecutionEnvironment env) {
        System.out.println("create default trigger");
        return EventTimeTrigger.create();
    }

    @Override
    public TypeSerializer<TimeWindow> getWindowSerializer(ExecutionConfig executionConfig) {
        System.out.println("create default serializer");
        return new TimeWindow.Serializer();
    }

    @Override
    public boolean isEventTime() {
        return true;
    }
}
class MyEvictor implements Evictor<SensorReading,Window>{

    @Override
    public void evictBefore(Iterable<TimestampedValue<SensorReading>> elements, int size, Window window, EvictorContext evictorContext) {

    }

    @Override
    public void evictAfter(Iterable<TimestampedValue<SensorReading>> elements, int size, Window window, EvictorContext evictorContext) {

    }
}
class MyTrigger extends Trigger<SensorReading, Window>{

    @Override
    public TriggerResult onElement(SensorReading element, long timestamp, Window window, TriggerContext ctx) throws Exception {
        return null;
    }

    @Override
    public TriggerResult onProcessingTime(long time, Window window, TriggerContext ctx) throws Exception {
        return null;
    }

    @Override
    public TriggerResult onEventTime(long time, Window window, TriggerContext ctx) throws Exception {
        return null;
    }

    @Override
    public void clear(Window window, TriggerContext ctx) throws Exception {

    }
}