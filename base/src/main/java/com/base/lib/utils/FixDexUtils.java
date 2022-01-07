package com.base.lib.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by ysq on 2018/11/27.
 */

public class FixDexUtils {

    public static HashSet<File> loadDex = new HashSet<>();

    public static void LoadFixedDex(Context context){
        if (context == null){
            return;
        }
        //遍历所有修复的dex
        //File dir = context.getDir(MyFileName.DIR, context.MODE_PRIVATE);
        File dir = context.getExternalFilesDir("app");
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++){
            File file = files[i];
            if (file.getName().endsWith(".dex")){
                //将所有的dex文件加到集合中
                loadDex.add(file);
            }
        }
        //dex合并之前的dex
        doDexInject(context, dir, loadDex);
    }


    public static void doDexInject(Context context, File fileDir, HashSet<File> loadDex){
        String optimizeDir = fileDir.getAbsolutePath() + File.separator + "opt_dex";
        File fopt = new File(optimizeDir);
        if (!fopt.exists()){
            fopt.mkdir();
        }
        Log.i("why","loadDex长度==="+loadDex.size());
        //用PathClassLoader加载应用程序的dex
        PathClassLoader pathClass = (PathClassLoader) context.getClassLoader();
            try {
                for (File file: loadDex){
                    //用DexClassLoader加载指定某个dex文件(修复的dex)
                    DexClassLoader classLoader = new DexClassLoader(file.getAbsolutePath(), optimizeDir,null, context.getClassLoader());
                    //合并
                    Object dexObj = getPathList(classLoader);
                    Object pathObj = getPathList(pathClass);

                    Object dexElements = getDexElements(dexObj);
                    Object pathDexElements = getDexElements(pathObj);
                    //合并完成
                    Object mDexElements = combineArray(dexElements, pathDexElements);
                    //重写给PathList里面的lement[] dexElements;赋值
                    Object pathList = getPathList(pathClass);
                    setFeild(pathList, pathList.getClass(),"dexElements", mDexElements);
                    Log.i("why","doDexInject===完成  ");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    public static Object getPathList(Object baseDexClassLoder) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getFeild(baseDexClassLoder, Class.forName("dalvik.system.BaseDexClassLoader"),"pathList");
    }

    public static Object getFeild(Object obj, Class<?> cl, String feild) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cl.getDeclaredField(feild);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static void setFeild(Object obj,Class<?> cl,String feild,Object value) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cl.getDeclaredField(feild);
        declaredField.setAccessible(true);
        declaredField.set(obj,value);
    }

    public static Object getDexElements(Object dexPathList ) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getFeild(dexPathList,dexPathList.getClass(),"dexElements");
    }

    /**
     * 两个数组合并
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

}
