
public class MyHashMap {
    //默认初始化大小
    private static final  int DEFALT_INITIAL_CAPACITY = 16;

    //数组，用于存放HashEntity
    HashEntity[] table;
    //元素个数
    private int size;

    public MyHashMap(){
        table = new HashEntity[DEFALT_INITIAL_CAPACITY];
        size=0;
    }

    //实现put方法
    public void put(Object key,Object value){
        //调用hashCode,得到key的索引
        int hashCode = index(key);
        HashEntity entity = table[hashCode];
        while(entity!=null){
            if(entity.hash==hashCode &&(entity.getKey()==key && entity.getKey().equals(key))){
                //如果key重复，则更新value
                entity.value = value;
                return;
            }
            entity = entity.getNext();
        }

        //调用addEntity方法。采用头插法将该值插入到链表的头部
        addEntity(hashCode,key,value);
    }

    private void addEntity(int hashCode,Object key,Object value){
        //将新的entry放到table的index位置第一个，若原来有值则以链表形式存放
        HashEntity entity = new HashEntity(hashCode,key,value,table[hashCode]);
        table[hashCode] = entity;
        //判断size是否达到临界值，若已达到则进行扩容，将table的capacicy翻倍
        size++;
    }

    public Object get(Object key){
        if(key==null) return  null;
        HashEntity entity = this.getEntity(key);
        return entity==null?null:entity.getValue();
    }

    private HashEntity getEntity(Object key){
        int hashCode = index(key);
        HashEntity entity = table[hashCode];
        while (entity!=null){
            if(entity.hash==hashCode &&(entity.getKey()==key || entity.getKey().equals(key))){
                return entity;
            }
            entity = entity.getNext();
        }
        return null;
    }
    //获取key的hash值
    private int index(Object key){
        return key.hashCode()%table.length;
    }

    public int size(){
        return  this.size;
    }

    //单链表的hashEntity
    class HashEntity{
        private final Object key;
        private Object value;
        private HashEntity next;
        private int hash;

        public HashEntity(int hash,Object key,Object value,HashEntity next){
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public HashEntity getNext() {
            return next;
        }

        public void setNext(HashEntity next) {
            this.next = next;
        }

        public Object getKey() {
            return key;
        }
    }
}
