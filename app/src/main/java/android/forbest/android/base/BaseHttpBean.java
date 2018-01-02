package android.forbest.android.base;

import java.io.Serializable;

/**
 * @Description: http请求bean, 根据后台返回的实际情况而定
 * @Author : ZhouHui
 * @Date : 2017/12/20.
 */

public class BaseHttpBean<T, V> implements Serializable{

    private String execResult;
    private String execMsg;
    private String execCode;
    private T execData;
    private V execDatas;

    public String getExecResult() {
        return execResult;
    }

    public void setExecResult(String execResult) {
        this.execResult = execResult;
    }

    public String getExecMsg() {
        return execMsg;
    }

    public void setExecMsg(String execMsg) {
        this.execMsg = execMsg;
    }

    public String getExecCode() {
        return execCode;
    }

    public void setExecCode(String execCode) {
        this.execCode = execCode;
    }

    public T getExecData() {
        return execData;
    }

    public void setExecData(T execData) {
        this.execData = execData;
    }

    public V getExecDatas() {
        return execDatas;
    }

    public void setExecDatas(V execDatas) {
        this.execDatas = execDatas;
    }
}
