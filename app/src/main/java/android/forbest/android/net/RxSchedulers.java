package android.forbest.android.net;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @Description: 调度转换器
 * @Author : ZhouHui
 * @Date : 2017/12/26.
 */

public class RxSchedulers {
    /**
     *
     * @param <T> Api.getInstance().movieService.getGankData("Android",1).compose(RxSchedulers.io_main());
     * @return
     */
    public static <T> ObservableTransformer<T, T> io2Main() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
