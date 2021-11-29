package cn.neu.aimp.iot.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * 基础流封装模块
 * @author wangjiawen
 */
@Setter
@Getter
public class BStreamer {
    private int width;
    private int height;
    private String url;

    public BStreamer(String url) {
        this.url = url;
    }

    public BStreamer(String url, int w, int h) {
        this.url = url;
        if (w > 0 && h > 0) {
            this.width = w;
            this.height = h;
        }
    }
}
