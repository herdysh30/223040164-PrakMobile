package com.example.mynote.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mynote.ui.theme.Purple40
import com.example.mynote.ui.theme.Purple80
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.NoteScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination<RootGraph>(start = true)
@Composable
fun SplashScreen(navigator: DestinationsNavigator){
    val viewModel = hiltViewModel<SplashViewModel>()
    val scope = rememberCoroutineScope()

    Surface(contentColor = Purple80, color = Purple40){

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            CircularProgressIndicator(color = Color.White)

            Spacer(modifier = Modifier.height(5.dp).fillMaxWidth())

            Text(text = "MyNote App", style = TextStyle(
                fontSize = 18.sp,
                color = Color.White
            ))
            Text(text = "versi 1.0", style = TextStyle(
              fontSize = 18.sp,
                color = Color.White
            ))
        }
    }

    LaunchedEffect(scope) {
        delay(5000)
        viewModel.checkSession(onSuccess = {
            navigator.navigate(NoteScreenDestination)
        }, onError = {
            navigator.navigate(LoginScreenDestination)
        })
    }
}