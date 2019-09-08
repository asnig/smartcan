package com.ckguys.pojo;

/**
 * 保存垃圾桶数据和以及时间的pojo
 *
 * @author XYQ
 */
public class Data {
    /**
     * 垃圾桶id
     */
    private String id;
    /**
     * 垃圾桶的数据
     */
    private String state;
    /**
     * 传送数据的时间
     */
    private long time;

    public Data() {
    }

    public Data(String id, String state, long time) {
        this.id = id;
        this.state = state;
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
