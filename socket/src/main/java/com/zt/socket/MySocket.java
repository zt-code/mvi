package com.zt.socket;

import android.os.Handler;
import android.util.Log;

import com.base.lib.base.base_act.ActManager;
import com.base.lib.net.L;
import com.base.lib.utils.AppUtil;
import com.base.lib.utils.ToastUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.java_websocket.handshake.ServerHandshake;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.ByteBuffer;

import dalvik.system.DexClassLoader;

public class MySocket {

    private JWebSocketClient client;
    private int SeqId = 1000;
    private boolean isClose;

    private static MySocket helper;
    public static MySocket getIns() {
        if (helper == null) {
            synchronized (MySocket.class) {
                if (helper == null) {
                    helper = new MySocket();
                }
            }
        }
        return helper;
    }

    /**
     * 初始化websocket连接
     */
    public void initSocketClient() {
        isClose = false;
        URI uri = URI.create("ws://192.168.1.11:19999/chat");
        client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                L.e("JWebSocketClient", "收到的消息 String：" + message);
            }

            @Override
            public void onMessage(ByteBuffer bytes) {
                super.onMessage(bytes);
                try {
                    ProtocolModule.CommonProtocol builder = ProtocolModule.CommonProtocol.parseFrom(bytes);
                    int code = builder.getCommHeader().getCommandId();
                    if(code == ProtocolConstants.AUTH_LOGIN_TOKEN) {
                        L.e("JWebSocketClient", "登录成功 getCommHeader：" + builder.getCallbackPack());
                        File file = new File(AppUtil.getContext().getExternalFilesDir("MyDex").getAbsolutePath() + "/" + "Method1.dex");
                        byte[] dexBytes = builder.getCallbackPack().getLoginBack().getDexAlgorithm().toByteArray();
                        byteToFile(dexBytes, file.getAbsolutePath());
                        showDexToast();

                    }else if(code == ProtocolConstants.HEART_BEAT) {
                        L.e("JWebSocketClient", "收到的心跳包");
                        SeqId++;
                    }

                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                    L.e("JWebSocketClient", "接收的消息失败 ByteBuffer：" + e.getMessage());
                }
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                L.e("JWebSocketClient", "websocket连接成功  ");

                boolean isLogin = false; //用户是否登录过
                if(isLogin) {
                    ProtocolModule.CommonHeader.Builder commBuilder = ProtocolModule.CommonHeader.newBuilder();
                    commBuilder.setCommandId(ProtocolConstants.AUTH_LOGIN_TOKEN)
                            .setSeqId(SeqId)
                            .setVersion(1);

                    ProtocolModule.LoginPack.Builder loginPackBuilder = ProtocolModule.LoginPack.newBuilder()
                            .setClientVersion("")
                            .setMobile("")
                            .setToken("")
                            .setDeviceId(AppUtil.getDeviceID())
                            .setOs("android")
                            .setClientVersion("1.0");

                    ProtocolModule.CommonProtocol.Builder builder = ProtocolModule.CommonProtocol.newBuilder();
                    //1
                    builder.setCommHeader(commBuilder);
                    //3
                    builder.setRequestPack(ProtocolModule.RequestMessage.newBuilder().setLoginPack(loginPackBuilder));
                    byte[] bytes = builder.build().toByteArray();
                    //发送登录请求
                    L.e("JWebSocketClient", "开始登录");
                    sendMsg(bytes);
                }

                //心跳测试
                mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
            }
        };
        connect();
    }

    public void showDexToast() {
        File file = new File(AppUtil.getContext().getExternalFilesDir("MyDex").getAbsolutePath() + "/" + "Method1.dex");
        if(file.exists()) {
            DexClassLoader cl = new DexClassLoader(
                file.getAbsolutePath(),
                    AppUtil.getContext().getExternalFilesDir("MyDex").getAbsolutePath(),
                null,
                    AppUtil.getContext().getClassLoader()
            );
            Class libProviderClazz = null;

            try {
                libProviderClazz = cl.loadClass("Method1");
                libProviderClazz.newInstance();

                /*Method getAuthor = libProviderClazz.getDeclaredMethod("getStr", new Class[]{String.class}); //获得私有方法
                getAuthor.setAccessible(true); //调用方法前，设置访问标志
                Object obj = getAuthor.invoke(libProviderClazz, "输入方法哈哈哈"); //使用方法*/

                Method getAuthor = libProviderClazz.getDeclaredMethod("getStr"); //获得私有方法
                getAuthor.setAccessible(true); //调用方法前，设置访问标志
                Object obj = getAuthor.invoke(libProviderClazz); //使用方法

                if(obj != null) {
                    L.e("JWebSocketClient", "obj != null");
                    ToastUtil.getIns().show(ActManager.getLastActivity(), obj.toString());
                }else {
                    ToastUtil.getIns().show(ActManager.getLastActivity(), "obj == null");
                    L.e("JWebSocketClient", "obj == null");
                }

            } catch (Exception exception) {
                L.e("JWebSocketClient", "showDexToast error $exception"+exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    public void bytoFile() {

    }

    /**
     * byte[] 转 file
     * @param bytes
     * @param path
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static void byteToFile(byte[] bytes, String path) {
        try {
            // 根据绝对路径初始化文件
            File localFile = new File(path);
            if (!localFile.exists()) {
                localFile.createNewFile();
            }
            // 输出流
            OutputStream os = new FileOutputStream(localFile);
            os.write(bytes);
            os.close();
            Log.i("JWebSocketClient", "写入文件成功  "+localFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("JWebSocketClient", "写入文件失败  "+e.getMessage());
        }
    }

    /**
     * 连接websocket
     */
    private void connect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void out() {
        new Thread() {
            @Override
            public void run() {
                isClose = true;
                if(client != null) {
                    client.close();
                }
            }
        }.start();
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    public void sendMsg(byte[] msg) {
        if (null != client) {
            client.send(msg);
        }
    }

    // -------------------------------------websocket心跳检测------------------------------------------------

    private static final long HEART_BEAT_RATE = 10 * 1000;//每隔10秒进行一次对长连接的心跳检测
    private Handler mHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if(isClose) return;

            if (client != null) {
                if (client.isClosed()) {
                    reconnectWs();
                }else {
                    ProtocolModule.CommonHeader.Builder commBuilder = ProtocolModule.CommonHeader.newBuilder();
                    commBuilder.setCommandId(ProtocolConstants.HEART_BEAT)
                            .setSeqId(SeqId)
                            .setVersion(1);
                    ProtocolModule.CommonProtocol.Builder builder = ProtocolModule.CommonProtocol.newBuilder();
                    builder.setCommHeader(commBuilder);
                    //发送登录请求
                    L.e("JWebSocketClient", "发送心跳包");
                    sendMsg(builder.build().toByteArray());
                }
            } else {
                //如果client已为空，重新初始化连接
                client = null;
                initSocketClient();
            }
            //每隔一定的时间，对长连接进行一次心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    L.e("JWebSocketClient", "开启重连");
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 获取指定目录内所有文件路径
     * @param dirPath 需要查询的文件目录
     * @param _type 查询类型，比如mp3什么的
     */
    private void getAllFiles(String dirPath, String _type) {
        File f = new File(dirPath);
        if (f.exists()) { //判断路径是否存在
            File[] files = f.listFiles();
            for(File file: files) {
                if(file.getName().endsWith(_type)) {
                    L.e("JWebSocketClient","fileName:"+file.getAbsolutePath());
                }
            }
        }

    }

}
