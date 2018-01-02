package android.forbest.android.base;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author : ZhouHui
 * @Date : 2018/1/2.
 */

public class BaseListBean<T> implements Serializable{

    private List<T> execDatas;

    public List<T> getExecDatas() {
        return execDatas;
    }

    public void setExecDatas(List<T> execDatas) {
        this.execDatas = execDatas;
    }
}
