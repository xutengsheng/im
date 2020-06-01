package com.xts.im.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.xts.im.db.DaoMaster;
import com.xts.im.db.DaoSession;
import com.xts.im.db.MyOpenHelper;
import com.xts.im.util.SpUtil;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


//app一上来会先走application是有条件的,要求app原来所在的进程被杀死才会走,
//如果仅仅是activity销毁了,不一定走
//Android 系统为了提高app启动的速度,在界面销毁之后,进程不会被杀死, 而是变成一个空进程
public class BaseApp extends Application {
    public static BaseApp sContext;
    public static boolean isLogin;
    public static String mToken;
    public static int mMode;
    private MyOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static Resources getRes() {
        return sContext.getResources();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        sContext = this;

        mToken = (String) SpUtil.getParam(Constants.TOKEN, "");
        if (TextUtils.isEmpty(mToken)) {
            isLogin = false;
        } else {
            isLogin = true;
        }
        initEase();
        initBaidu();
        initUmeng();
        setDatabase();
        initJpush();
    }

    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initUmeng() {
        UMConfigure.setLogEnabled(true);
        //第一个：上下文
        //第二个：友盟的ak
        //第三个：渠道号
        //第四个：设备类型
        //第五个：推送的ak
        UMConfigure.init(this, "5e02b1ba570df3874f0006b8"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        //不配置ak，qq可以分享，微信和新浪不可以
        //不配置ak，登陆都不可以
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("710114978",
                "9e2203c93d2bfe3f082f6cb83485d70c",
                "http://sns.whalecloud.com");
        //第一个appid，第二个：app key
        PlatformConfig.setQQZone("101832572", "37640d0826df43d39450a2ebe6e178aa");
    }

    private void initBaidu() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    private void initEase() {

        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        //options.setAcceptInvitationAlways(true);
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EaseUI.getInstance().init(this, options);

//初始化
        //EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        //通过DaoMaster内部类DevOpenHelper可以获取一个SQLiteOpenHelper 对象
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        // 此处MyDb表示数据库名称 可以任意填写

        ///"MyDb.db" 数据库名字,高版本手机必须以.db结尾
        mHelper = new MyOpenHelper(this, "MyDb.db", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //Android 9 默认使用了wal模式,需要关闭wal模式
        db.disableWriteAheadLogging();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static BaseApp getInstance(){
        return sContext;
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
