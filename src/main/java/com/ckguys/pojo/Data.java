package com.ckguys.pojo;

/**
 * 保存垃圾桶数据和以及时间的pojo
 *
 * @author XYQ
 */
public class Data {
    private String state;
    private long time;

    public Data() {
    }

    public Data(String state, long time) {
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
}
