public class StringClassPair {
    private String key;
    private Class value;


    public StringClassPair(String s, Class c){
        this.key = s;
        this.value = c;
    }

    public String getKey(){
        return key;
    }

    public Class getValue(){
        return value;
    }
}
