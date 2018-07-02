
public class MyHashMap {
    //默认初始化大小
    private static final  int DEFALT_INITIAL_CAPACITY = 2;
    //默认负载因子
    private static final float DEFALT_LOAD_FACTOR = 0.75f;
    //临界值
    private int threshold;

    //数组，用于存放HashEntity
    HashEntity[] table;
    //元素个数
    private int size;
    //扩容次数
    private int resize;

    public MyHashMap(){
        table = new HashEntity[DEFALT_INITIAL_CAPACITY];
        threshold = (int)(DEFALT_INITIAL_CAPACITY*DEFALT_LOAD_FACTOR);
        size=0;
    }

    //实现put方法
    public void put(Object key,Object value){
        //调用hashCode,得到key的索引
        int hashCode = index(key,table.length);
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
        size++;
        //判断size是否达到临界值，若已达到则进行扩容，将table的capacicy翻倍
        if(size>threshold){
            resize(table.length*2);
        }
    }

    private  void resize(int capacity){
        HashEntity[] newTable = new HashEntity[capacity];
        //遍历原table，将每个entry都重新计算hash放入newTable中
        for(int i=0;i<table.length;i++){
            HashEntity old = table[i];
            while(old!=null){
                HashEntity next = old.getNext();
                int index = index(old.getKey(),newTable.length);
                old.setNext(newTable[index]);
                old.setHash(index);
                newTable[index] = old;
                old = next;
            }
        }
        //用newTable替table
        table = newTable;
        //修改临界值
        threshold = (int)(table.length*DEFALT_LOAD_FACTOR);
        resize++;
    }

    public Object get(Object key){
        if(key==null) return  null;
        HashEntity entity = this.getEntity(key);
        return entity==null?null:entity.getValue();
    }

    private HashEntity getEntity(Object key){
        int hashCode = index(key,table.length);
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
    private int index(Object key,int length){
        return key.hashCode()%length;
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

        public int getHash() {
            return hash;
        }

        public void setHash(int hash) {
            this.hash = hash;
        }
    }
}
