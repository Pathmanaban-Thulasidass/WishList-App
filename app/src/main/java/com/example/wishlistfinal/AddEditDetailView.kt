package com.example.wishlistfinal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id : Long,
    viewmodel : WishViewModel,
    navController: NavController
){


    //This three variable are used to build the Snack Bar
    val snackMessage = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    if(id !=0L){
        val wish = viewmodel.getAWishById(id).collectAsState(initial = Wish(0L,"",""))
        viewmodel.wishTitleState = wish.value.title
        viewmodel.wishDescriptionState = wish.value.description
    }
    else{
        viewmodel.wishTitleState = ""
        viewmodel.wishDescriptionState = ""
    }

    Scaffold(
        topBar =  {
            AppBarView(title =
                if(id != 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish)
            ) {
                navController.navigateUp()
            }
        },
        scaffoldState = scaffoldState
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WishTextField(
                label = "title",
                value = viewmodel.wishTitleState,
                onValueChanged = {
                    viewmodel.onWishTitleChange(it)
                }
            )

            WishTextField(
                label = "description",
                value = viewmodel.wishDescriptionState,
                onValueChanged = {
                    viewmodel.onWishDescriptionChange(it)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if(viewmodel.wishTitleState.isNotBlank() &&
                        viewmodel.wishDescriptionState.isNotBlank()){
                        if(id != 0L){
                            viewmodel.updateWish(
                                Wish(
                                    id = id,
                                    title = viewmodel.wishTitleState.trim(),
                                    description = viewmodel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "Your wish is Updated"
                        }
                        else{
                            viewmodel.addWish(
                                Wish(
                                    title = viewmodel.wishTitleState.trim(),
                                    description = viewmodel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "Wish has been created"
                        }
                        navController.navigateUp()
                    }
                    else{
                        snackMessage.value = "Enter fields to create a Wish"
                    }

                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    }

                }) {
                Text(
                    text = if(id != 0L) stringResource(id = R.string.update_wish)
                            else stringResource(id = R.string.add_wish),
                    style = TextStyle(fontSize = 18.sp)
                )
            }

        }
    }

}

@Composable
fun WishTextField(
    label : String,
    value : String,
    onValueChanged : (String) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label , color = Color.Black)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color.Red
        )
    )
}

