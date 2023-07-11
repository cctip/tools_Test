import {useNavigation, useRoute} from '@react-navigation/native';
import * as React from 'react';
import {useRef} from 'react';
import {View, Text, Alert, Button} from 'react-native';
import {WebView as WebView1, WebViewProps} from 'react-native-webview';
import {
  WebViewEvent,
  WebViewNativeEvent,
} from 'react-native-webview/lib/WebViewTypes';

const jscode = `var obj = {
  href: "",
  ...window.location,
};
Object.defineProperty(obj, "href", {
  set: function (newVal) {
    let data={
      type:1,
      url:newVal
    }
    data=JSON.stringify(data)
    window.ReactNativeWebView.postMessage(data)

  },
});
window.open = function () {
  return {
    location: obj,
  };
};`;

function WebView() {
  const router = useRoute();
  const url = router?.params?.url || 'https://bc.game';
  const params = router?.params;
  console.log(params, '参数');

  React.useEffect(() => {
    if (params?.type == 'login') {
      Alert.alert('登陆');
      webRef.current.postMessage(JSON.stringify(params));
    }
  }, [params]);

  const navigation = useNavigation();
  const webRef = useRef<any>(null);

  React.useEffect(() => {
    setTimeout(() => {
      webRef?.current.injectJavaScript(jscode);
    }, 3000);
  }, []);

  const onMessage = event => {
    let data = JSON.parse(event.nativeEvent.data);
    console.log(data);
    if (!data.type) {
      data.type = 'login';
      navigation.navigate('webview', data);
      return;
    }
    if (data.type == 1) {
      navigation.navigate('webview1', {url: data.url});
    }
  };
  return (
    <View style={{flex: 1}}>
      <Button
        title="dsdas"
        onPress={() => {
          webRef.current.postMessage(JSON.stringify('fdsfsdfds'));
        }}></Button>
      <WebView1
        //   webViewKey='123'
        ref={webRef}
        enableApplePay={false}
        messagingWithWebViewKeyEnabled={true}
        javaScriptCanOpenWindowsAutomatically={true}
        mixedContentMode="always"
        // injectedJavaScript={jscode}
        // injectedJavaScriptBeforeContentLoaded={jscode}
        style={{flex: 1, backgroundColor: 'red'}}
        onMessage={onMessage}
        // onNavigationStateChange={onNavigationStateChange}
        source={{uri: url}}></WebView1>
    </View>
  );
}

export default WebView;
