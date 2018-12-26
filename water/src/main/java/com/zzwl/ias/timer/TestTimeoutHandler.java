package com.zzwl.ias.timer;

/**
 * Created by HuXin on 2017/11/30.
 */
public class TestTimeoutHandler implements TimeoutHandler {
    @Override
    public void timeout() {
        System.out.println("timeout");
    }
}
