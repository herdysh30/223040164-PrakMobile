package com.example.ppm03_latihan2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppm03_latihan2.ui.theme.Ppm03_latihan2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ppm03_latihan2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistrationForm(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RegistrationForm(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // State to store input values
    val name = remember { mutableStateOf(TextFieldValue("")) }
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val phone = remember { mutableStateOf(TextFieldValue("")) }
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val address = remember { mutableStateOf(TextFieldValue("")) }

    // Check if all fields are filled
    val isFormValid = remember {
        derivedStateOf {
            name.value.text.isNotBlank() && username.value.text.isNotBlank() &&
                    phone.value.text.isNotBlank() && email.value.text.isNotBlank() &&
                    address.value.text.isNotBlank()
        }
    }

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(text = "Nama", modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth())
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Username", modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth())
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Nomor Telepon", modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth())
        TextField(
            value = phone.value,
            onValueChange = { phone.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Email", modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth())
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Alamat Rumah", modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth())
        TextField(
            value = address.value,
            onValueChange = { address.value = it },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    if (isFormValid.value) {
                        Toast.makeText(context, "Halo, ${name.value.text}!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Semua inputan harus diisi!", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.weight(1f),

            ) {
                Text(text = "Simpan")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    name.value = TextFieldValue("")
                    username.value = TextFieldValue("")
                    phone.value = TextFieldValue("")
                    email.value = TextFieldValue("")
                    address.value = TextFieldValue("")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Reset")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationForm() {
    Ppm03_latihan2Theme {
        RegistrationForm()
    }
}
