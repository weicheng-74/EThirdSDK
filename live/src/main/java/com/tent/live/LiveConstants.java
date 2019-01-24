package com.tent.live;

import com.tencent.rtmp.TXLivePlayer;

/**
 * date:2019/1/17
 * email:892865384@qq.com
 * author:TanWeiCheng
 */
public class LiveConstants {
    //传入的 URL 为 RTMP 直播地址
    public static final int type_rtmp = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
    //传入的 URL 为 FLV 直播地址
    public static final int type_flv = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
    //低延迟链路地址（仅适合于连麦场景）
    public static final int type_aac = TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC;
    //    传入的 URL 为 HLS（m3u8）播放地址
    public static final int type_hls = TXLivePlayer.PLAY_TYPE_VOD_HLS;


    //播放器系列的启动模式
    public static final int type_auto_type = 0; // 自动模式

    //极速模式
    public static final int type_quick_type = 1;

    // 普通模式
    public static final int type_command_type = 2;


}
