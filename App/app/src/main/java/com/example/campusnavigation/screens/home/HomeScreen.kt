package com.example.campusnavigation.screens.home

import androidx.compose.runtime.Composable
import com.example.scan.mvvm.HomeTodoView
import com.example.scan.mvvm.MainViewModel


@Composable
fun HomeScreen(viewModel: MainViewModel) {
    //  Aufruf der HomeTodoView
    HomeTodoView(viewModel)
    }


