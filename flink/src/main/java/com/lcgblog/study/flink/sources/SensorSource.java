package com.lcgblog.study.flink.sources;

import com.lcgblog.study.flink.models.SensorReading;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SensorSource extends RichParallelSourceFunction<SensorReading> {

    private Boolean running = true;

    @Override
    public void run(SourceContext<SensorReading> ctx) throws Exception {
        Random rand = new Random();
        int taskIdx = this.getRuntimeContext().getIndexOfThisSubtask();

        List<Tuple2<String, Double>> curFTemp = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            curFTemp.add(Tuple2.of("sensor_" + (taskIdx * 10 + i), 65 + (rand.nextGaussian() * 20)));
        }

        while (running) {
            curFTemp = curFTemp.stream().map(t -> Tuple2.of(t.f0, t.f1 + (rand.nextGaussian() + 0.5)))
                    .collect(Collectors.toList());
            Long curTime = Calendar.getInstance().getTimeInMillis();
            curFTemp.forEach(t->{
                ctx.collect(new SensorReading(t.f0, curTime, t.f1));
            });
            Thread.sleep(5000);
        }

    }

    @Override
    public void cancel() {
        running = false;
    }
}
