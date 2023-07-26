import React from "react";
import {
    UIManager, 
    requireNativeComponent, 
    type ViewStyle
} from 'react-native'

type NativeEvent = {
    nativeEvent:{
        message:string;
    }
}

type NativeWebViewProps = {
    url:string;
    onMessage:(event:NativeEvent)=>void,
    style:ViewStyle
}

const ComponentName = "NativeWebView"

export default UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<NativeWebViewProps>(ComponentName)
    : ()=>{
        throw new Error(ComponentName+" is not found")
    };