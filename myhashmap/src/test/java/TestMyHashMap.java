import org.junit.Test;

public class TestMyHashMap {
    @Test
    public void readWriteSimpleValue() {
        MyHashMap hm = new MyHashMap();
        hm.put("a","1");
        hm.put("b","2");
        hm.put("a","11");

        System.out.println(hm.get("a"));
        System.out.println(hm.get("b"));
    }
}
