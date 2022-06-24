package ma.xiaoshuai.cordova.wswUnity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;

import ma.xiaoshuai.cordova.wswAlipay.AuthResult;
import ma.xiaoshuai.cordova.wswAlipay.PayResult;

public class wswUnity extends CordovaPlugin {
  protected static CallbackContext currentCallbackContext;
  public static final String TAG = "Cordova.Plugin.wswUnity";
  public static final String ERROR_INVALID_PARAMETERS = "参数格式错误";
  final static int SDK_PAY_FLAG = 1001;
  final static int SDK_AUTH_FLAG = 2;

  protected static String appId;

  CallbackContext callbackContext;
  @Override
  public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("openMiniProgram")) {
      return openMiniProgram(args, callbackContext);
    }
    return false;
  }

  @SuppressLint("LongLogTag")
  protected boolean openMiniProgram(CordovaArgs args, CallbackContext callbackContext){
    currentCallbackContext = callbackContext;
    final JSONObject params;
    try {
      params = args.getJSONObject(0);
    } catch (JSONException e) {
      callbackContext.error(ERROR_INVALID_PARAMETERS);
      return true;
    }

    WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
    try {
      String appId = params.getString("appId"); // 填应用AppId
      IWXAPI api = WXAPIFactory.createWXAPI(cordova.getActivity(), appId);
      req.userName = params.getString("userName"); // 填小程序原始id
      req.path = params.getString("path");                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
      req.miniprogramType = params.getInt("miniprogramType");// 可选打开 开发版，体验版和正式版

      api.sendReq(req);
    }catch (Exception e){
      callbackContext.error(ERROR_INVALID_PARAMETERS);
      Log.e(TAG,e.getMessage());
    }
    return true;
  }


}

