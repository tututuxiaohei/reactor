package com.mazhenxing.reactor.test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class FluxTest {

    public static void main(String[] args) {
        generate();
    }

    public static void just(){
        Flux<String> just = Flux.just("11");
        Flux.just("11").subscribe(System.out::print);
    }

    public static void generate(){
        Flux.generate(sink -> {
            sink.next(range());
            sink.complete();
        })
                .subscribeOn(Schedulers.elastic())
                .map(x -> x + "1")
                .subscribeOn(Schedulers.single())
                .map(x -> x +"2")
                .toStream()
                .forEach(System.out::print);


        Flux.generate(ArrayList::new, (list, sink) -> {
            sink.next("");
            sink.complete();
            return list;
        });
    }

    public static Random range(){
        Random random = new Random();
        System.out.println("random");
        return random;
    }


    public static void take(){
        Flux.range(1, 1000).take(10).toStream().forEach(System.out::println);
    }


    public static void  scheduler(){
        Flux.create(sink -> {
            sink.next(Thread.currentThread().getName());
            sink.complete();
        })
                .publishOn(Schedulers.single())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .publishOn(Schedulers.elastic())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .subscribeOn(Schedulers.parallel())
                .toStream()
                .forEach(System.out::println);
    }


    public static void  buffer(){
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
    }
}
