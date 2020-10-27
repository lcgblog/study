package com.lcgblog.study.flink.operators;


import com.lcgblog.study.flink.models.SensorReading;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class TemperatureAverager implements WindowFunction<SensorReading,SensorReading,String, TimeWindow> {

    @Override
    public void apply(String sensorId, TimeWindow window, Iterable<SensorReading> input, Collector<SensorReading> out) throws Exception {
        int count = 0;
        double sum = 0.0;
        for(SensorReading sensorReading : input){
            sum += sensorReading.getTemperature();
            count++;
        }
        double avgTemp = sum / count;
        out.collect(new SensorReading(sensorId, window.getEnd(), avgTemp));
    }
}
