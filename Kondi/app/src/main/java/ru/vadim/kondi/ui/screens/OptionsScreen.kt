package ru.vadim.kondi.ui.mainscreens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OptionsScreen(navController: NavController) {

    val coroutine = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)


/*
    BottomSheetSample(state = state) {



        Column(
            modifier = Modifier.fillMaxSize() ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            
            ElevatedButton(onClick = {
                coroutine.launch { state.animateTo(ModalBottomSheetValue.Expanded) }
            }) {
                Text(text = "Календарь")
            }
            

            
            
        }
        
    }
*/
}