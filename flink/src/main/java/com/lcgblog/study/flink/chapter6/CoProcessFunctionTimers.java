package com.lcgblog.study.flink.chapter6;

import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.sources.SensorSource;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoProcessFunction;
import org.apache.flink.util.Collector;

public class CoProcessFunctionTimers {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);

        DataStreamSource<Tuple2<String, Long>> filterSwitches = env.fromElements(Tuple2.of("sensor_2", 10 * 1000L), Tuple2.of("sensor_7", 20 * 1000L));

        DataStreamSource<SensorReading> readings = env.addSource(new SensorSource());

        SingleOutputStreamOperator<SensorReading> forwardReadings = readings.connect(filterSwitches).keyBy("id", "f0")
                .process(new ReadingFilter());
        forwardReadings.print();
        env.execute("CoProcessFunctionTimers");
    }


}

class ReadingFilter extends CoProcessFunction<SensorReading, Tuple2<String,Long>, SensorReading>{

    private ValueState<Boolean> enableForwarding;
    private ValueState<Long> stopTimer;

    @Override
    public void open(Configuration parameters) throws Exception {
        enableForwarding = getRuntimeContext().getState(new ValueStateDescriptor<>("enableForwarding", Boolean.class));
        stopTimer = getRuntimeContext().getState(new ValueStateDescriptor<>("stopTimer", Long.class));
    }

    @Override
    public void processElement1(SensorReading value, Context ctx, Collector<SensorReading> out) throws Exception {
        if(enableForwarding.value() != null && enableForwarding.value()){
            out.collect(value);
        }
    }

    @Override
    public void processElement2(Tuple2<String, Long> value, Context ctx, Collector<SensorReading> out) throws Exception {
        enableForwarding.update(true);
        Long curTimer = stopTimer.value();
        if(curTimer == null){
            curTimer = 0L;
        }
        long newTimer = ctx.timerService().currentProcessingTime() + value.f1;
        if (newTimer > curTimer){
            ctx.timerService().deleteProcessingTimeTimer(curTimer);
            ctx.timerService().registerProcessingTimeTimer(newTimer);
            stopTimer.update(newTimer);
        }
    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<SensorReading> out) throws Exception {
        enableForwarding.clear();
        stopTimer.clear();
    }
}
