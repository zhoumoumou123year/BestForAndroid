package android.forbest.android.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @Description: Rx生命周期管理器
 * @Author : ZhouHui
 * @Date : 2018/1/3.
 */

public class RxManager {

    private static volatile RxManager mRxManager;

    private Map<String, CompositeDisposable> mDisposableMap;

    private RxManager() {
        mDisposableMap = new HashMap<>();
    }

    /**
     * 双重锁单例模式
     *
     * @return
     */
    public static RxManager getInstance() {
        if (mRxManager == null) {
            synchronized (RxManager.class) {
                if (mRxManager == null) {
                    mRxManager = new RxManager();
                }
            }
        }
        return mRxManager;
    }

    /**
     * 根据key添加桥梁
     *
     * @param key
     * @param disposable
     */
    public void add(String key, Disposable disposable) {
        Set<String> set = mDisposableMap.keySet();
        if (set.contains(key)) {
            CompositeDisposable compositeDisposable = mDisposableMap.get(key);
            compositeDisposable.add(disposable);
        } else {
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(disposable);
            mDisposableMap.put(key, compositeDisposable);
        }
    }

    /**
     * 根据key移除桥梁
     *
     * @param key
     * @param disposable
     */
    public void clear(String key, Disposable disposable) {
        Set<String> set = mDisposableMap.keySet();
        if (set.contains(key)) {
            CompositeDisposable compositeDisposable = mDisposableMap.get(key);
            compositeDisposable.clear();
            mDisposableMap.remove(key);
        }
    }

    /**
     * 移除所有的桥梁
     */
    public void clearAll() {
        Set<String> set = mDisposableMap.keySet();
        // 增强for循环在链表集合下的效率较高
        for (String key : set) {
            CompositeDisposable compositeDisposable = mDisposableMap.get(key);
            compositeDisposable.clear();
        }
        mDisposableMap.clear();
    }
}
