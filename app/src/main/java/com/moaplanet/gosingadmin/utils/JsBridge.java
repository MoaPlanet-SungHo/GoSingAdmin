package com.moaplanet.gosingadmin.utils;

import android.webkit.JavascriptInterface;

import com.moaplanet.gosingadmin.common.interfaces.JsReceiver;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class JsBridge implements JsReceiver {

    private JsReceiver jsReceiver;

    public JsBridge(JsReceiver jsReceiver) {
        this.jsReceiver = jsReceiver;
    }

    @JavascriptInterface
    public void showHTML(String html) {
        if (html.indexOf('{') == -1) {
            onJsReceiverFail();
            return;
        }
        String resultJson = html.substring(html.indexOf('{'), html.indexOf('}') + 1);
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            if (jsonObject.get("suc_cd").equals("0000")) {
                onJsReceiverSuccess(resultJson);
            } else {
                onJsReceiverFail();
            }
        } catch (JSONException e) {
            Logger.d("Json Error\n" + e);
            onJsReceiverFail();
        }
    }

    @Override
    public void onJsReceiverSuccess(String resultMsg) {
        jsReceiver.onJsReceiverSuccess(resultMsg);
    }

    @Override
    public void onJsReceiverFail() {

    }
}
