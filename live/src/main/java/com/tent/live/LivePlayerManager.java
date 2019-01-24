package com.tent.live;

import android.content.Context;
import android.util.Log;

import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;


/**
 * date:2019/1/17
 * email:892865384@qq.com
 * author:TanWeiCheng
 */

/**
 * 播放器管理封装
 */
public final class LivePlayerManager {

    private static LivePlayerManager LivePlayerManager;
    private Context context;
    private TXLivePlayer mLivePlayer;
    private String Tag = LivePlayerManager.class.getSimpleName();
    private TXCloudVideoView mView;

    public LivePlayerManager(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (LivePlayerManager == null) {
            synchronized (LivePlayerManager.class) {
                if (LivePlayerManager == null) {
                    LivePlayerManager = new LivePlayerManager(context);

                }
            }
        }
    }

    public static LivePlayerManager getLivePlayerManager() {
        return LivePlayerManager;
    }

    /**
     * 设置view
     *
     * @param txCloudVideoView
     * @return
     */
    public LivePlayerManager setTXCloudVideoView(TXCloudVideoView txCloudVideoView) {
        this.mView = txCloudVideoView;
        return this;
    }

    public TXCloudVideoView getmView() {
        return mView;
    }

    /**
     * 设置播放器对象
     *
     * @param mLivePlayer
     * @return
     */
    public LivePlayerManager setPlayer(TXLivePlayer mLivePlayer) {
        this.mLivePlayer = mLivePlayer;
        return this;
    }

    /**
     * 关联 player 与 播放view
     */
    public LivePlayerManager relation() {
        if (mView == null) {
            throw new RuntimeException("init mView()");
        }
        getmLivePlayer().setPlayerView(mView);
        return this;
    }

    public LivePlayerManager relationByPlayer(TXLivePlayer txLivePlayer) {
        txLivePlayer.setPlayerView(mView);
        return this;
    }


    /**
     * 设置控制台输出
     *
     * @param isConsoleEnable
     * @return
     */
    public LivePlayerManager set(boolean isConsoleEnable) {
        //setConsoleEnabled
        //设置是否在 Android Studio 的控制台打印 SDK 的相关输出。
        TXLiveBase.setConsoleEnabled(isConsoleEnable);
        //setLogLevel
        //设置是否允许 SDK 打印本地 log，SDK 默认会将 log 写到 sdcard 上的 log / tencent / liteav 文件夹下。
        //如果您需要我们的技术支持，建议将次开关打开，在重现问题后提供 log 文件，非常感谢您的支持。
        TXLiveBase.setLogLevel(TXLiveConstants.LOG_LEVEL_DEBUG);
        return this;
    }

    /**
     * 获取当前播放器对象。
     *
     * @return
     */
    public TXLivePlayer getmLivePlayer() {
        //创建 player 对象
        if (mLivePlayer == null) {
            mLivePlayer = new TXLivePlayer(context);
        }
        return mLivePlayer;
    }

    /**
     * 直接new 一个player 对象。
     *
     * @return
     */
    public TXLivePlayer getNewMLivePlayer() {
        return new TXLivePlayer(context);
    }

    /**
     * 获取播放器对象
     *
     * @param context
     * @return
     */
    public TXLivePlayer getmLivePlayer(Context context) {
        //创建 player 对象
        if (mLivePlayer == null) {
            mLivePlayer = new TXLivePlayer(context);
        }
        return mLivePlayer;
    }

    /**
     * 动态获取一个视频播放View
     *
     * @return
     */
    public TXCloudVideoView getTxCloudVideoView() {
        checkNull();
        TXCloudVideoView txCloudVideoView = new TXCloudVideoView(context);
        return txCloudVideoView;
    }

    //暂停
    public void playPause() {
        getmLivePlayer().pause();
    }

    //继续播放
    public void playResume() {
        getmLivePlayer().resume();
    }

    //销毁
    public void onDestroy() {
        getmLivePlayer().stopPlay(true); // true 代表清除最后一帧画面
        mView.onDestroy();
    }

    /**
     * 开启播放
     *
     * @param flvUrl
     * @param type
     */
    public LivePlayerManager startPlayLive(String flvUrl, int type) {
        checkNull();
        if (flvUrl == null) {
            Log.e(Tag, "链接都为空，你播条毛？");
        }
        int mtype = 0;
        switch (type) {
            case LiveConstants.type_aac:
                mtype = TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC;
                break;
            case LiveConstants.type_flv:
                mtype = TXLivePlayer.PLAY_TYPE_LIVE_FLV;//推荐 FLV
                break;
            case LiveConstants.type_rtmp:
                mtype = TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC;
                break;
            case LiveConstants.type_hls:
                mtype = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                break;
        }
        try {
            getmLivePlayer(context).startPlay(flvUrl, mtype);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private void checkNull() {
        if (context == null) {
            throw new RuntimeException("context 为空， 请初始化该类init()");
        }
    }

    /**
     * 获取版本号码
     *
     * @return
     */
    public static String getVersionLive() {
        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "liteav sdk version is : " + sdkver);
        return sdkver;
    }

    //======================== 控制器系列======================

    /**
     * @param type 0; // 自动模式
     *             1 //极速模式
     *             2 //普通模式。
     * @return
     */
    public TXLivePlayConfig getTXLivePlayConig(int type) {
        TXLivePlayConfig mPlayConfig = new TXLivePlayConfig();
        switch (type) {
            case LiveConstants.type_auto_type:
                mPlayConfig.setAutoAdjustCacheTime(true);
                mPlayConfig.setMinAutoAdjustCacheTime(1);
                mPlayConfig.setMaxAutoAdjustCacheTime(5);
                break;
            case LiveConstants.type_quick_type:
                mPlayConfig.setAutoAdjustCacheTime(true);
                mPlayConfig.setMinAutoAdjustCacheTime(1);
                mPlayConfig.setMaxAutoAdjustCacheTime(1);
                break;
            case LiveConstants.type_command_type:
                mPlayConfig.setAutoAdjustCacheTime(false);
                mPlayConfig.setMinAutoAdjustCacheTime(5);
                mPlayConfig.setMaxAutoAdjustCacheTime(5);
                break;
        }
        return mPlayConfig;
    }


    //======================================= 没空封装了 系列-=======================================
    //清晰度无缝切换   直播会看。 三种模式切换
    public LivePlayerManager setRenderMode(int type) {

        // 设置填充模式
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        // 设置画面渲染方向
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);

        return this;
    }

    //截流录制
    public void startRecord() {

    }

    public void snapshot() {
        //截图
    }
}
