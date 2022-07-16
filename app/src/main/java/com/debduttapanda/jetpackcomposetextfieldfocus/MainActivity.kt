package com.debduttapanda.jetpackcomposetextfieldfocus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.debduttapanda.jetpackcomposetextfieldfocus.ui.theme.JetpackComposeTextFieldFocusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTextFieldFocusTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var text by remember { mutableStateOf("") }
                    val focusRequester = remember { FocusRequester() }
                    var hasFocus by remember { mutableStateOf(false) }
                    val focusManager = LocalFocusManager.current
                    Column() {
                        TextField(
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            Modifier
                                .focusRequester(focusRequester)//attach the focus requester
                                .onFocusChanged {
                                    hasFocus = it.hasFocus
                                }
                        )
                        Button(onClick = {
                            if(!hasFocus){
                                focusRequester.requestFocus()//to request focus
                            }
                            else{
                                focusManager.clearFocus()//to remove focus
                            }
                        }) {
                            Text("Focus")
                        }
                        ////////////////focus on box////////////////////////////////
                        val boxFocusRequester = remember { FocusRequester() }
                        var color by remember { mutableStateOf(Color.Black) }
                        Box(
                            Modifier
                                .size(100.dp)
                                .clickable { boxFocusRequester.requestFocus() }
                                .border(2.dp, color)
                                .focusRequester(boxFocusRequester)
                                .onFocusChanged { color = if (it.isFocused) Color.Green else Color.Black }
                                .focusable()
                        )
                        /////////////////////////
                        val focusRequester1 = remember { FocusRequester() }
                        var value by remember { mutableStateOf("apple") }
                        var borderColor by remember { mutableStateOf(Transparent) }
                        TextField(
                            value = value,
                            onValueChange = {
                                value = it.apply {
                                    if (length > 5)
                                        focusRequester1.captureFocus() //capture focus, then no one can take focus, clear focus also will not work
                                    else
                                        focusRequester1.freeFocus()//free the focus, opposite of capture focus
                                }
                            },
                            modifier = Modifier
                                .border(2.dp, borderColor)
                                .focusRequester(focusRequester1)
                                .onFocusChanged { borderColor = if (it.isCaptured) Red else Transparent }
                        )
                        Button(onClick = {
                            focusManager.clearFocus()
                        }) {
                            Text("DeFocus")
                        }/**/
                        /*
                        * today we learnt about
                        * focus on jetpack compose
                        * we can control focus on text field and even in box
                        * that means each and every composable can be focused
                        * we learnt about
                        * 1. focusRequester
                        * 2. how to focus programmatically
                        * 3. how to clear focus programmatically
                        * 4. how to capture focus
                        * 5. how to free focus
                        * 6. make a box focusable
                        * 7. react to focus change
                        * */
                    }
                }
            }
        }
    }
}