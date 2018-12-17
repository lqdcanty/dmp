package com.efida.sports.dmp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shun on 17/5/12.
 */
public class SeqWorkerUtil {
    private final long        workerId;
    private final static long twepoch            = 1361753741828L;
    private long              sequence           = 0L;
    private final static long workerIdBits       = 4L;
    public final static long  maxWorkerId        = -1L ^ -1L << workerIdBits;
    private final static long sequenceBits       = 10L;

    private final static long workerIdShift      = sequenceBits;
    private final static long timestampLeftShift = sequenceBits + workerIdBits;
    public final static long  sequenceMask       = -1L ^ -1L << sequenceBits;

    private long              lastTimestamp      = -1L;

    //序列号
    private static int        seqNo              = 0;

    public SeqWorkerUtil(final long workerId) {
        super();
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String
                .format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    this.lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.lastTimestamp = timestamp;
        long nextId = ((timestamp - twepoch << timestampLeftShift))
                      | (this.workerId << this.workerIdShift) | (this.sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    private static SeqWorkerUtil idWorker;

    /**
     * 构建一个唯一的主键 <br/>
     * 采用Twitter 的 snowflake <br/>
     * 结构如下(每部分用-分开): <br/>
     * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br/>
     * snowflake每秒能够产生26万个ID <br/>
     * @return
     */
    public static String buildSingleId() {
        if (idWorker == null) {
            idWorker = new SeqWorkerUtil(2);
        }
        return String.valueOf(idWorker.nextId());
    }

    /**
     * 低频次调用下uuid
     * 
     * @return
     */
    public static String generateTimeSequence() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date(System.currentTimeMillis()));
        return format + getRandomString(4) + getSeqNo();
    }

    private synchronized static String getSeqNo() {
        seqNo++;
        seqNo = seqNo % 100;
        if (seqNo == 0) {
            seqNo = 1;
        }
        if (seqNo < 10) {
            return "0" + 1;
        }
        return seqNo + "";
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度  
        String base = "0123456789abcdefghijklmnuvwxyz";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int) (base.length() * Math.random());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}