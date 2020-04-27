package com.mazhenxing.reactor.reactor;

import reactor.core.publisher.Flux;

public class MyFlux {

    public static Flux createFlux(String str){

        switch (str){
            //just()  可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
            case "just" : return Flux.just("11");
            
        }
        return null;
    }
}
