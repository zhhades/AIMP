package cn.neu.aimp.iot.storage;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * @author wangjiawen
 * 图片存储仓库
 */
public class ImgStorage {
    /**
     * 图片仓库大小
     */
    private int pendingListMaxSize = 30;
    private int processedListMaxSize = 30;

    public String id;

    /**
     * 待处理图片的仓库载体
     */
    private LinkedList<BufferedImage> pendingList = new LinkedList<>();

    /**
     * 已处理图片的仓库载体
     */
    private LinkedList<BufferedImage> processedList = new LinkedList<>();

    public ImgStorage(int pendingListMaxSize, int processedListMaxSize) {
        this.pendingListMaxSize = pendingListMaxSize;
        this.processedListMaxSize = processedListMaxSize;
    }

    public ImgStorage(String id) {
        this.id = id;
    }

    /**
     * 待处理图片生成
     * @param img
     */
    public void producePendingImg(BufferedImage img) {
        synchronized (pendingList) {
            while (pendingList.size() + 1 > pendingListMaxSize) {
                System.out.println("待处理图片的仓库已满");
                try {
                    pendingList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pendingList.add(img);
            System.out.println("视频端增加一张待处理图片，现仓库待处理图片数量" + pendingList.size());
            pendingList.notifyAll();
        }
    }

    /**
     * 获取待处理图片
     * @return
     */
    public BufferedImage consumePendingImg() {
        BufferedImage objList;
        synchronized (pendingList) {
            while (pendingList.size() == 0) {
                System.out.println("仓库为空，等待视频端添加图片。");
                try {
                    pendingList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            objList = pendingList.get(0);
            pendingList.remove();
            System.out.println("AI处理端处理了一张待处理图片，现仓库待处理指令数量" + pendingList.size());
            pendingList.notifyAll();
        }
        return objList;
    }

    /**
     * 清楚所有的待处理图片
     */
    public void consumeAllPendingImg() {
        synchronized (pendingList) {
            while (pendingList.size() == 0) {
                System.out.println("仓库为空，等待视频端增加云台控制指令。");
                try {
                    pendingList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pendingList.clear();
            System.out.println("清空所有待处理图片，现仓库待处理指令数量" + pendingList.size());
            pendingList.notifyAll();
        }
    }

    /**
     * 生成已处理图片
     * @param img
     */
    public void produceProcessedImg(BufferedImage img) {
        synchronized (processedList) {
            while (processedList.size() + 1 > processedListMaxSize) {
                System.out.println("已处理图片的仓库已满");
                try {
                    processedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            processedList.add(img);
            System.out.println("AI端增加一张已处理图片，现仓库已处理图片数量" + processedList.size());
            processedList.notifyAll();
        }
    }

    /**
     * 获取已处理图片
     * @return
     */
    public BufferedImage consumeProcessedImg() {
        BufferedImage objList;
        synchronized (processedList) {
            while (processedList.size() == 0) {
                System.out.println("仓库为空，等待AI端添加处理完成的图片。");
                try {
                    processedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            objList = processedList.get(0);
            processedList.remove();
            System.out.println("推送端推送了一张完成的图片，现仓库等待推送的数量" + processedList.size());
            processedList.notifyAll();
        }
        return objList;
    }

    /**
     * 清除全部已处理图片
     */
    public void consumeAllProcessedImg() {
        synchronized (processedList) {
            while (processedList.size() == 0) {
                System.out.println("仓库为空，等待AI端处理新的图片。");
                try {
                    processedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            processedList.clear();
            System.out.println("清空所有已经完成处理图片，现仓库已经完成处理的数量" + processedList.size());
            processedList.notifyAll();
        }
    }
}