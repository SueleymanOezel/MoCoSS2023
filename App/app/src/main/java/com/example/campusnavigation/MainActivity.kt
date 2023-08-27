package com.example.campusnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.campusnavigation.bottomnavigation.Navigation
import com.example.campusnavigation.mvvm.MainViewModel
import com.example.campusnavigation.screens.ImageData
import com.example.campusnavigation.ui.theme.CampusNavigationTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        MainViewModel(
            (application as MyApplication).todoDb.todoDao()
            // (application as MyApplication).db.roomDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CampusNavigationTheme() {





/*
@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }
    val barCodeVal = remember { mutableStateOf("") }

    AndroidView(
        factory = { AndroidViewContext ->
            PreviewView(AndroidViewContext).apply {
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        modifier = Modifier.fillMaxSize().padding(50.dp),
        update = { previewView ->
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val barcodeAnalyser = BarCodeAnalyser { barcodes ->
                    barcodes.forEach { barcode ->
                        barcode.rawValue?.let { barcodeValue ->
                            barCodeVal.value = barcodeValue
                            Toast.makeText(context, barcodeValue, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )
}

 */



                /*
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colors.background
                                ) {

                                    MainTodoView(viewModel)



                */


                /*
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                navController = navController,
                                onItemClick = { route ->
                                    navController.navigate(route)
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            Navigation(navController = navController)
                        }
                    }


                }
            }
        }
    }



                 */

                val navController = rememberNavController()
                Navigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}


/*

                var code by remember {
                    mutableStateOf("")
                }
                val context = LocalContext.current
                val lifecycleOwner = LocalLifecycleOwner.current
                val cameraProviderFuture = remember {
                    ProcessCameraProvider.getInstance(context)
                }
                var hasCameraPermission by remember {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                }
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { granted ->
                        hasCameraPermission = granted
                    }
                )
                LaunchedEffect(key1 = true) {
                    launcher.launch(Manifest.permission.CAMERA)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (hasCameraPermission) {
                        AndroidView(
                            factory = { context ->
                                val previewView = PreviewView(context)
                                val preview = androidx.camera.core.Preview.Builder().build()
                                val selector = CameraSelector.Builder()
                                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                    .build()
                                preview.setSurfaceProvider(previewView.surfaceProvider)
                                val imageAnalysis = ImageAnalysis.Builder()
                                    .setTargetResolution(
                                        Size(
                                            previewView.width,
                                            previewView.height
                                        )
                                    )
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                    .build()
                                imageAnalysis.setAnalyzer(
                                    ContextCompat.getMainExecutor(context),
                                    QrCodeAnalyzer { result ->
                                        code = result
                                    }
                                )
                                try {
                                    cameraProviderFuture.get().bindToLifecycle(
                                        lifecycleOwner,
                                        selector,
                                        preview,
                                        imageAnalysis
                                    )
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                previewView

                            },
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            //text= "Scanner Screen",
                            // fontWeight = FontWeight.Bold,
                            //            color = Color.Black,
                            //            fontSize = 30.sp,
                            //            textAlign = TextAlign.Center
                            text = code,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp)

                        )
                    }
                }
            }
        }
    }
}
*/


