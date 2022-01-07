package com.zt.dex

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.base.lib.base.base_act.BaseFrgActivity
import com.base.lib.net.L
import com.base.lib.permissions.RxPermissions
import com.base.lib.utils.down.DownHelper
import com.zt.dex.databinding.ActivityMainBinding
import dalvik.system.DexClassLoader
import java.io.File

@Route(path = "/dex/main")
class MainActivity : BaseFrgActivity<ActivityMainBinding>() {

    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            showDexToast();
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        bind.bt1.setOnClickListener {
            downDexFile("http://cdn.topjoytec.com/6vc005qdxhS-4oRJk3rvcA.dex", "/Method1.dex");
        }

        bind.bt2.setOnClickListener {
            showDexToast();
        }

        bind.bt3.setOnClickListener {
            downDexFile("http://cdn.topjoytec.com/z-HRXmKo5KZ-fpyNtRRGmw.dex", "/Method2.dex");
        }

        bind.bt4.setOnClickListener {
            getAllFiles(getExternalFilesDir("app")!!.absolutePath, ".dex")
            Toast.makeText(this@MainActivity, Method2.getStr(), Toast.LENGTH_LONG).show();
        }
    }

    private fun downDexFile(url: String, name: String) {
        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            .subscribe { aBoolean ->
                if(aBoolean) {
                    Thread {
                        val file = File(getExternalFilesDir("app")!!.absolutePath + name)
                        DownHelper.getIns().down(file, url, object : DownHelper.DownListener {
                            override fun onPrepare() {

                            }

                            override fun onWrite(num: Long) {

                            }

                            override fun onProgress(progress: Long) {

                            }

                            override fun onEnd() {
                                if(name.equals("/Method1.dex")) {
                                    mHandler.sendEmptyMessage(1)
                                }
                            }

                            override fun onError() {

                            }
                        })
                    }.start()
                }
            }
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_main
    }

    fun showDexToast() {
        val file = File(getExternalFilesDir("app")!!.absolutePath + "/" + "Method1.dex")
        if(file.exists()) {
            val cl = DexClassLoader(
                file.absolutePath,
                getExternalFilesDir("app")!!.absolutePath, null, classLoader
            )
            var libProviderClazz: Class<*>? = null

            try {
                libProviderClazz = cl.loadClass("Method1")
                libProviderClazz.newInstance()

                val getAuthor = libProviderClazz.getDeclaredMethod("getStr") //获得私有方法
                getAuthor.isAccessible = true //调用方法前，设置访问标志
                val s = getAuthor.invoke(libProviderClazz) as String //使用方法

                Toast.makeText(this@MainActivity, s, Toast.LENGTH_SHORT).show()
            } catch (exception: Exception) {
                L.i("why", "showDexToast error $exception")
                exception.printStackTrace()
            }
        }
    }

    /**
     * 获取指定目录内所有文件路径
     * @param dirPath 需要查询的文件目录
     * @param _type 查询类型，比如mp3什么的
     */
    fun getAllFiles(dirPath: String?, _type: String?): JSONArray? {
        val f = File(dirPath)
        if (!f.exists()) { //判断路径是否存在
            return null
        }
        val files = f.listFiles()
            ?: //判断权限
            return null
        val fileList = JSONArray()
        for (_file in files) { //遍历目录
            if (_file.isFile && _file.name.endsWith(_type!!)) {
                val _name = _file.name
                val filePath = _file.absolutePath //获取文件路径
                val fileName = _file.name.substring(0, _name.length - 4) //获取文件名
                Log.i("why","fileName:"+fileName);
                Log.i("why","filePath:"+filePath);
                try {
                    val _fInfo = JSONObject()
                    _fInfo.put("name", fileName)
                    _fInfo.put("path", filePath)
                    fileList.add(_fInfo)
                } catch (e: java.lang.Exception) {
                }
            } else if (_file.isDirectory) { //查询子目录
                getAllFiles(_file.absolutePath, _type)
            } else {
            }
        }
        return fileList
    }

}