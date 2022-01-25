package com.lcgblog.study.webflux.a0121;

import reactor.core.publisher.Flux;
import java.util.List;

public class FluxDemo1 {

    public static void main(String[] args) {
        Flux<Integer> a = Flux.fromIterable(List.of(1, 2, 3));
        Flux<Integer> b = Flux.fromIterable(List.of(3, 4, 5, 6));
        Flux<Integer> c = a.concatWith(b);
        c.log().subscribe();
        Flux<Integer> d = Flux.merge(a,b);
        d.log().subscribe();

    }

}
