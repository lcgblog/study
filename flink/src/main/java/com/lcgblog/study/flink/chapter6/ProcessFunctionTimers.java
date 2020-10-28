package com.lcgblog.study.flink.chapter6;

import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.sources.SensorSource;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class ProcessFunctionTimers {
    public static void main(String[] args)throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);

        DataStreamSource<SensorReading> readings = env.addSource(new SensorSource());

        SingleOutputStreamOperator<String> warnings = readings
                .keyBy(SensorReading::getId)
                .process(new TempIncreaseAlertFunction());

        warnings.print();

        env.execute("ProcessFunctionTimers");
    }
}

class TempIncreaseAlertFunction extends KeyedProcessFunction<String, SensorReading, String> {

    private ValueState<Double> lastTemp;
    private ValueState<Long> currentTimer;

    @Override
    public void open(Configuration parameters) throws Exception {
        lastTemp = getRuntimeContext().getState(new ValueStateDescriptor<>("lastTemp", TypeInformation.of(Double.class)));
        currentTimer = getRuntimeContext().getState(new ValueStateDescriptor<>("timer", TypeInformation.of(Long.class)));
    }

    @Override
    public void processElement(SensorReading value, Context ctx, Collector<String> out) throws Exception {
        Double prevTemp = lastTemp.value();
        lastTemp.update(value.getTemperature());

        Long curTimerTimestamp = currentTimer.value();
        if (prevTemp == null){

        }else if(value.getTemperature() < prevTemp && curTimerTimestamp != null){
            ctx.timerService().deleteEventTimeTimer(curTimerTimestamp);
            currentTimer.clear();
        }else if(value.getTemperature() > prevTemp){
            long timerTs = ctx.timerService().currentProcessingTime() + 1000;
            ctx.timerService().registerProcessingTimeTimer(timerTs);
            currentTimer.update(timerTs);
        }
    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
        out.collect("Temperature of sensor '" + ctx.getCurrentKey() + "'  monotonically increased for 1 second.");
        currentTimer.clear();
    }
}
