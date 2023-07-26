import {useNavigation, useRoute} from '@react-navigation/native';
import * as React from 'react';
import {useRef} from 'react';
import {View, Text, Alert, Button} from 'react-native';
import NativeWebView from '../src/widgets/NativeWebView'

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

  const onMessage = event => {
    let data = JSON.parse(event.nativeEvent.message);
    console.log(data);
  };
  return (
    <View style={{flex: 1}}>
      <NativeWebView
        //   webViewKey='123'
        ref={webRef}
        // injectedJavaScript={jscode}
        // injectedJavaScriptBeforeContentLoaded={jscode}
        style={{flex: 1, backgroundColor: 'red'}}
        onMessage={onMessage}
        // onNavigationStateChange={onNavigationStateChange}
        url={url}></NativeWebView>
    </View>
  );
}

export default WebView;
