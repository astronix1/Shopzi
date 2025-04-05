package com.example.shopzi.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.shopzi.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopzi.AppUtil
import com.example.shopzi.viewmodel.AuthViewModel

@Composable
fun SignupScreen(modifier: Modifier = Modifier, authViewModel: AuthViewModel = viewModel()) {

    var email by remember{
        mutableStateOf("")
    }
    var name by remember{
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Let’s Get You Started!",
            modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif
            )
        )

        Spacer(modifier = Modifier.height(15.dp))
        Image(painter = painterResource(R.drawable.act),
            contentDescription = "image",
            modifier
                .fillMaxWidth()
                .height(258.dp)
            )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = name, onValueChange = {
            name = it
        },
            label = {
                Text(text = "Name")
            },
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = email, onValueChange = {
            email = it
        },
            label = {
                Text(text = "Enter email address")
            },
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = password, onValueChange = {
            password = it
        },
            label = {
                Text(text = "Enter password")
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()

        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                authViewModel.signup(email, name, password){success, errorMsg->
                    if(success){

                    }else{
                        AppUtil.showToast(context, errorMsg ?: "Something went wrong")
                    }

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "SignUp",
                style = TextStyle(fontSize = 17.sp)
            )
        }

    }
}
@Preview
@Composable
fun Pre1(modifier: Modifier = Modifier) {
    SignupScreen()
}