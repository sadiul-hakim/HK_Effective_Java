package xyz.sadiulhakim.creating_and_destroying_objects.item_1;

import java.util.EnumSet;

public class Main {
    public static void main(String[] args) {
        var hk = new Hakim();
        hk.sayHi();
        EnumSet<Type> set = EnumSet.of(Type.A, Type.B, Type.C, Type.D);
        System.out.println(set.getClass().getName());
    }
}


enum Type{
    A,
    B,
    C,
    D
}

interface Person{
    default void sayHi() {
        System.out.println("Hi");
    }

    static void sayName(){
        System.out.println(getName());
    };

    private static String getName(){
        return "Hakim";
    }
}

class Hakim implements Person{

}
