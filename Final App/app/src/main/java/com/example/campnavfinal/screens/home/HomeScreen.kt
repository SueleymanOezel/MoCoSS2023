package com.example.campnavfinal.screens.home

import androidx.compose.runtime.Composable
import com.example.campnavfinal.mvvm.HomeTodoView
import com.example.campnavfinal.mvvm.MainViewModel


@Composable
fun HomeScreen(viewModel: MainViewModel) {
    //  Aufruf der HomeTodoView
    HomeTodoView(viewModel)
    }


