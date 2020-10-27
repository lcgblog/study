package com.lcgblog.study.flink.sources;

import com.lcgblog.study.flink.models.SmokeLevel;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.Random;

public class SmokeLevelSource extends RichParallelSourceFunction<SmokeLevel> {

    private Boolean running = true;

    @Override
    public void run(SourceContext<SmokeLevel> ctx) throws Exception {

        Random rand = new Random();

        while (running) {
            if(rand.nextGaussian() > 0.8){
                ctx.collect(SmokeLevel.High);
            }else{
                ctx.collect(SmokeLevel.Low);
            }

            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
