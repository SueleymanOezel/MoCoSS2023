package com.example.campnavfinal.screens.home

import androidx.compose.runtime.Composable
import com.example.campnavfinal.mvvm.HomeNoteView
import com.example.campnavfinal.mvvm.MainViewModel


@Composable
fun HomeScreen(viewModel: MainViewModel) {
    //  Aufruf der HomeTodoView
    HomeNoteView(viewModel)
    }


