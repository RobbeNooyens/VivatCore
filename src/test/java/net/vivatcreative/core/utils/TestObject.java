package net.vivatcreative.core.utils;

public class TestObject {
    public int number;
    public String string;

    @Override
    public boolean equals(Object o){
        TestObject tO = (TestObject) o;
        if(tO.number != number) return false;
        if(!tO.string.equals(string)) return false;
        return true;
    }

    @Override
    public int hashCode(){
        return 31 * number + 31 * string.hashCode();
    }

}
