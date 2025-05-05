package xyz.sadiulhakim.creating_and_destroying_objects.item_3;

public enum Elvis {
    INSTANCE;

    public void leaveTheBuilding() {
        System.out.println("Whoa baby, I'm outta here!");
    }
}

class Main{
    public static void main(String[] args) {
        Elvis.INSTANCE.leaveTheBuilding();
    }
}