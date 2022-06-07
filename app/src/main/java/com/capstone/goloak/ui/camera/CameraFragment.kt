package com.capstone.goloak.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.capstone.goloak.R
import com.capstone.goloak.databinding.FragmentCameraBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCameraBinding? = null
    private lateinit var cameraExecutor : ExecutorService

    private var cameraSelector : CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture : ImageCapture? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private var binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        //   val dashboardViewModel =
        //       ViewModelProvider(this)[CameraViewModel::class.java]

        _binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return _binding!!.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
        //     }

        val navBar : BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        navBar?.visibility = View.GONE

    }


    //PERMISION FOR CAMERA
    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireActivity(),
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission( requireActivity(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()

        //PERMISION FOR CAMERA
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }


        _binding!!.switchCamera.setOnClickListener(this)
        _binding!!.captureImage.setOnClickListener(this)
    }

    private fun startCamera(){
        val cameraProvideFuture = ProcessCameraProvider.getInstance(requireActivity())

        cameraProvideFuture.addListener({
            val cameraProvider : ProcessCameraProvider = cameraProvideFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(_binding!!.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            }catch (exc : Exception){
                Toast.makeText(requireActivity(), "Gagal Memuat Camera", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(requireActivity()))
    }


    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.switchCamera ->{
                cameraSelector = if (cameraSelector.equals(CameraSelector.DEFAULT_BACK_CAMERA)) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
                startCamera()
            }
            R.id.captureImage ->{

            }
        }
    }

    override fun onResume() {
        super.onResume()
        startCamera()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
