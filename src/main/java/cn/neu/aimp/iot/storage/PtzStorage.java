package cn.neu.aimp.iot.storage;

import com.github.chen0040.objdetect.models.DetectedObj;

import java.util.LinkedList;
import java.util.List;

/**
 * @author
 * 云台控制指令仓库
 */
public class PtzStorage {
    /**
     * 云台控制指令仓库大小
     */
    private int maxSize = 20;

    public String id;

    /**
     * 云台控制指令的仓库载体
     */
    private LinkedList<List<DetectedObj>> list = new LinkedList<>();

    public PtzStorage(String id, int maxSize) {
        //待实现 根据数据库设置仓库大小
        this.id = id;
        this.maxSize = maxSize;
    }

    /**
     *
     * @param objList
     */
    public void produce(List<DetectedObj> objList) {
        synchronized (list) {
            while (list.size() + 1 > maxSize) {
                System.out.println("云台控制指令的仓库已满");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(objList);
            System.out.println("Ai处理端增加一条云台控制指令，现仓库待处理指令数量" + list.size());
            list.notifyAll();
        }
    }

    public List<DetectedObj> consume() {
        List<DetectedObj> objList;
        synchronized (list) {
            while (list.size() == 0) {
                System.out.println("仓库为空，等待AI处理端增加云台控制指令。");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            objList = list.get(0);
            list.remove();
            System.out.println("云台控制端处理一条云台控制指令，现仓库待处理指令数量" + list.size());
            list.notifyAll();
        }
        return objList;
    }

    public void consumeAll() {
        synchronized (list) {
            while (list.size() == 0) {
                System.out.println("仓库为空，等待AI处理端增加云台控制指令。");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.clear();
            System.out.println("云台控制端处于焦距调节状态，清空所有云台控制指令，现仓库待处理指令数量" + list.size());
            list.notifyAll();
        }
    }
}