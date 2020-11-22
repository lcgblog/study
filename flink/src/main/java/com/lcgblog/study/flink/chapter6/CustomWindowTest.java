package com.lcgblog.study.flink.chapter6;

import com.lcgblog.study.flink.models.SensorReading;
import com.lcgblog.study.flink.sources.SensorSource;
import com.lcgblog.study.flink.util.SensorTimeAssigner;
import java.util.Collection;
import java.util.Collections;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.triggers.EventTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class CustomWindowTest {

  public static void main(String[] args)throws Exception {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.getCheckpointConfig().setCheckpointInterval(10 * 1000);
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
    env.getConfig().setAutoWatermarkInterval(1000L);
    DataStream<SensorReading> sensorData = env.addSource(new SensorSource())
        .assignTimestampsAndWatermarks(new SensorTimeAssigner());
    DataStream<Tuple4<String, Long, Long, Integer>> countsPerThirtySecs =  sensorData.keyBy(sensorDataReading -> sensorDataReading.getId())
        .window(new ThirtySecondsWindows())
        .trigger(new OneSecondIntervalTrigger())
        .process(new CountFunction());
    countsPerThirtySecs.print();
    env.execute();
  }
}

//窗口分配器负责把事件分配到指定窗口中如[0,30) , [30,60)
class ThirtySecondsWindows extends WindowAssigner<Object, TimeWindow> {

  private transient static final long windowSize = 30 * 1000L; //30 seconds

  @Override
  public Collection<TimeWindow> assignWindows(Object o, long ts,
      WindowAssignerContext ctx) {
    long startTime = ts - (ts % windowSize);
    long endTime = startTime + windowSize;
    //emit the corresponding time window [startTime, endTime)
    System.out.println("created window " + startTime + "," + endTime);
    return Collections.singleton(new TimeWindow(startTime, endTime));
  }

  //指定默认触发器，如果没有指定触发器，则将使用该默认触发器，该触发器的逻辑是当watermark>=window.end-1，则触发
  @Override
  public Trigger<Object, TimeWindow> getDefaultTrigger(StreamExecutionEnvironment env) {
    System.out.println("getDefaultTrigger");
    return EventTimeTrigger.create();
  }

  //指定窗口的序列化器，在指定处理函数时调用
  @Override
  public TypeSerializer<TimeWindow> getWindowSerializer(ExecutionConfig executionConfig) {
    System.out.println("getWindowSerializer");
    return new TimeWindow.Serializer();
  }

  @Override
  public boolean isEventTime() {
    return true;
  }
}

class OneSecondIntervalTrigger extends Trigger<SensorReading, TimeWindow> {

  //每个元素
  @Override
  public TriggerResult onElement(SensorReading r, long timestamp, TimeWindow window,
      TriggerContext ctx) throws Exception {
    System.out.println("onElement " + r);
    final ValueState<Boolean> firstSeen =
        ctx.getPartitionedState(new ValueStateDescriptor<Boolean>("firstSeen", Types.BOOLEAN));
    if(firstSeen.value() == null || !firstSeen.value()){
      System.out.println("firstSeen " + r);
      //compute time for next early firing by rounding watermark to second
      long t = ctx.getCurrentWatermark() + (1000 - (ctx.getCurrentWatermark() % 1000));
      ctx.registerEventTimeTimer(t);
      //register timer for the window end
      ctx.registerEventTimeTimer(window.getEnd());
      firstSeen.update(true);
    }
    return TriggerResult.CONTINUE;
  }

  @Override
  public TriggerResult onProcessingTime(long timestamp, TimeWindow window, TriggerContext ctx)
      throws Exception {
    System.out.println("onProcessingTime");
    //continue, don't use processing time
    return TriggerResult.CONTINUE;
  }

  @Override
  public TriggerResult onEventTime(long timestamp, TimeWindow window, TriggerContext ctx)
      throws Exception {
    System.out.println("onEventTime");
    if(timestamp == window.getEnd()){
      //final evaluation and purge window state
      return TriggerResult.FIRE_AND_PURGE;
    }else{
      long t = ctx.getCurrentWatermark() + (1000 - ctx.getCurrentWatermark() % 1000);
      if(t < window.getEnd()){
        System.out.println("register " + t);
        ctx.registerEventTimeTimer(t);
      }
      return TriggerResult.FIRE;
    }
  }

  @Override
  public void clear(TimeWindow window, TriggerContext ctx) throws Exception {
    System.out.println("clear");
    final ValueState<Boolean> firstSeen =
        ctx.getPartitionedState(new ValueStateDescriptor<Boolean>("firstSeen", Types.BOOLEAN));
    firstSeen.clear();
  }
}

class CountFunction extends
    ProcessWindowFunction<SensorReading, Tuple4<String, Long, Long, Integer>, String, TimeWindow> {

  @Override
  public void process(String key, Context ctx, Iterable<SensorReading> readings,
      Collector<Tuple4<String, Long, Long, Integer>> out) throws Exception {

    int cnt = 0;
    for(SensorReading sensorReading : readings){
      System.out.println("process " + sensorReading);
      cnt++;
    }
    System.out.println();
    long evalTime = ctx.currentWatermark();
    out.collect(Tuple4.of(key, ctx.window().getEnd(), evalTime, cnt));
  }
}
