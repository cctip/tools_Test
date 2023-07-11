// In App.js in a new project

import * as React from 'react';
import { View, Text } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import HomeScreen from '../Pages';
import WebView from '../Pages/webview';



const Stack = createNativeStackNavigator();

function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="webview" component={WebView} />
        <Stack.Screen name="webview1" component={WebView} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

export default App;