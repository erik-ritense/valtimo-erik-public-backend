package com.ritense.valtimo.erikjava;

import org.springframework.stereotype.Component;

@Component
public class HelloWorld2 {

    public void sayHi() {

        var bla = optellen(1, 3);
        System.out.println("HELLO WORLD");
    }

    public Integer optellen(Integer nummer1 , Integer nummer2) {

        var result = nummer1 + nummer2;
        return result;
    }
}
