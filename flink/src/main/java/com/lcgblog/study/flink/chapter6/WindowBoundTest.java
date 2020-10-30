package com.lcgblog.study.flink.chapter6;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


//窗口的目的是把范围内时间的事件放在一个桶子内，watermark的目的是为了确定桶子里该有的元素都到达了
public class WindowBoundTest {
    public static void main(String[] args)throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        //配合AssignerWithPeriodicWatermarks一起使用
        //设置20秒发送一次Watermark, 默认值为200毫秒，它是通过processingTimer实现的，processingTimer = 产生watermark的算子的当前处理时间+该interval。
        // open时设置一个processingTimer，所以不是立即触发的，而是schedule到指定的interval后触发
        // 触发时，1）通过调用TimeAssigner的方法生成一个新的watermark，然后判断是否大于上一个发出的watermark，如果是则发出最新的watermark，并记录发出的wm用于下一次判断。
        //       2）注册下一个processingTimer
        env.getConfig().setAutoWatermarkInterval(20000L);
        //设置事件时间的取值规则，以及wm取值为最新事件时间向前推5秒
        SingleOutputStreamOperator<Tuple3<Integer, Long, String>> dataSource = env.addSource(new CustomSource()).assignTimestampsAndWatermarks(new CustomTimeAssigner(Time.seconds(5)));

        //做一个日志记录
        dataSource.process(new ProcessFunction<Tuple3<Integer, Long, String>, Object>() {
            @Override
            public void processElement(Tuple3<Integer, Long, String> value, Context ctx, Collector<Object> out) throws Exception {
                System.out.println(Thread.currentThread()+"receive "+value + " - current wm " + ctx.timerService().currentWatermark() + " " + ctx.timerService().currentProcessingTime());
            }
        });


        //配置窗口为大小10秒的滚动窗口，窗口为 [0,10) [10,20) ... [50,60)
        SingleOutputStreamOperator<Tuple3<Integer, Long, String>> reduceOutput = dataSource.timeWindowAll(Time.seconds(10))
                .reduce(new ReduceFunction<Tuple3<Integer, Long, String>>() {
                    //使用增量聚合算子（reduce/aggregate） 拿到发出最早收到的时间
                    @Override
                    public Tuple3<Integer, Long, String> reduce(Tuple3<Integer, Long, String> value1, Tuple3<Integer, Long, String> value2) throws Exception {
                        //该聚合算子每收到一个元素，窗口分配器会为该元素分配到指定的窗口内，然后调用该reduce方法，收到wm后，如果wm大于窗口的结束时间，则触发该窗口计算，发出计算结果
                        System.out.println(Thread.currentThread()+"reduce " + value1 + value2 + value1.hashCode());
                        return value1.f1 < value2.f1 ? value1 : value2;
                    }
                });


        reduceOutput.print();


        env.execute();
    }
}

class CustomTimeAssigner extends BoundedOutOfOrdernessTimestampExtractor<Tuple3<Integer, Long, String>>{

    public CustomTimeAssigner(Time maxOutOfOrderness) {
        super(maxOutOfOrderness);
    }

    @Override
    public long extractTimestamp(Tuple3<Integer, Long, String> element) {
        return element.f1;
    }
}

class CustomSource implements SourceFunction<Tuple3<Integer, Long, String>>, Serializable {

    private boolean running = true;

    @Override
    public void run(SourceContext<Tuple3<Integer, Long, String>> ctx) throws Exception {
        int index = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while(running){
            LocalDateTime now = LocalDateTime.now();
            ctx.collect(Tuple3.of(index++, now.toInstant(ZoneOffset.UTC).toEpochMilli(), formatter.format(now)));
            Thread.sleep(1000L);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
