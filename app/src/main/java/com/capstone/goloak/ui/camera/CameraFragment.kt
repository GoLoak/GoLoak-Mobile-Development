package com.capstone.goloak.ui.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.R
import com.capstone.goloak.ViewModelFactory
import com.capstone.goloak.databinding.FragmentCameraBinding
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.ml.Waste
import com.capstone.goloak.ui.OnSuccessSubmitActivity
import com.capstone.goloak.ui.profile.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class CameraFragment : Fragment() {

    private var photoFile: File? = null
    private lateinit var image: Bitmap
    private lateinit var currentPhotoPath: String
    private lateinit var trashCategory: String

    private var _binding: FragmentCameraBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = SettingPreferences.getInstance(requireActivity().dataStore)
        val profileViewModel = ViewModelProvider(this, ViewModelFactory(pref))[ProfileViewModel::class.java]

        profileViewModel.loading.observe(viewLifecycleOwner) {
            isLoading(it)
        }

        profileViewModel.getId().observe(viewLifecycleOwner) { userId ->
            profileViewModel.getUserProfile(userId)
            profileViewModel.data.observe(viewLifecycleOwner) {
                binding.tvAddress.text = it?.address
            }
        }

        binding.captureImage.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val pref = SettingPreferences.getInstance(requireActivity().dataStore)
        val cameraViewModel = ViewModelProvider(this, ViewModelFactory(pref))[CameraViewModel::class.java]
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = BitmapFactory.decodeFile(photoFile?.absolutePath)
            binding.captureImage.setImageBitmap(imageBitmap)
            image = imageBitmap
            val bitmap88 = imageBitmap.copy(Bitmap.Config.ARGB_8888, true)
            val prediction = predict(bitmap88)
            val max = prediction.apply { sortByDescending { it.score }}.take(Int.MAX_VALUE)
            val trashLabels = max[0].label.toString()
            val foodScore = max[0].score * 100
            val percentage = String.format("%3f", foodScore)
            if (percentage > 50.toString()) {
                trashCategory = trashLabels
                binding.tvTrashName.visibility = View.VISIBLE
                binding.tvTrashName.text = trashCategory
                Log.d(TAG, "$trashCategory (${percentage})")
            } else {
                binding.tvTrashName.visibility = View.INVISIBLE
                binding.captureImage.setImageResource(R.drawable.camera_plus)
                snackBar("Gagal membaca gambar, silahkan foto ulang.")
            }

            val requestImageFile = photoFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
            var pointCount = 0

            when (trashLabels) {
                "Botol HDPEM" -> {
                    pointCount = 120
                }
                "Botol Kaca" -> {
                    pointCount = 150
                }
                "Botol Plastik" -> {
                    pointCount = 100
                }
                "Kaleng" -> {
                    pointCount = 200
                }
                "Kardus" -> {
                    pointCount = 200
                }
                "Kertas" -> {
                    pointCount = 10
                }
            }

            binding.btnSubmit.setOnClickListener {
                val nameTrash = binding.tvTrashName.text.toString().trim()
                val trashQty = binding.edtTrashQty.text.trim()
                val address = binding.tvAddress.text.toString().trim()

                when {
                    photoFile == null -> {
                        snackBar("Silakan foto sampahnya dulu.")
                    }
                    nameTrash != trashLabels -> {
                        snackBar("Silakan foto ulang sampai nama sampah terbaca.")
                    }
                    trashQty.isEmpty() || trashQty.toString().toInt() < 1 -> {
                        snackBar("Silakan masukkan jumlah sampah.")
                    }
                    address.isEmpty() -> {
                        snackBar("Gagal memuat data alamat.")
                    }
                    else -> {
                        cameraViewModel.loading.observe(viewLifecycleOwner) {
                            isBtnClicked(it)
                        }

                        cameraViewModel.getId().observe(viewLifecycleOwner) { userId ->
                            cameraViewModel.postSellTrash(
                                userId,
                                binding.edtTrashQty.text.toString().toInt(),
                                pointCount*binding.edtTrashQty.text.toString().toInt(),
                                trashCategory.toRequestBody("text/plain".toMediaType()),
                                MultipartBody.Part.createFormData(
                                    "file",
                                    photoFile?.name,
                                    requestImageFile!!
                                )
                            )
                            cameraViewModel.data.observe(viewLifecycleOwner) {
                                if (it == "success") {
                                    val intent =
                                        Intent(context, OnSuccessSubmitActivity::class.java)
                                    startActivity(intent)
                                    requireActivity().finish()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            context?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    photoFile = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.e(TAG, ex.message.toString())
                        null
                    }
                    photoFile?.also {
                        context?.let { context ->
                            val photoURI: Uri = FileProvider.getUriForFile(
                                context,
                                "com.capstone.goloak.fileprovider",
                                it
                            )
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            resultLauncher.launch(takePictureIntent)
                        }
                    }
                }
            }
        }
    }

    private fun predict(img: Bitmap): MutableList<Category>{
        val model = Waste.newInstance(requireActivity())
        val image = TensorImage.fromBitmap(img)
        val outputs = model.process(image)
        val probability = outputs.probabilityAsCategoryList
        model.close()
        return probability
    }

    private fun snackBar(message: String){
        Snackbar.make(
            requireActivity().window.decorView.rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isEnableButton(button: Button?, isEnabled: Boolean) {
        if (!isEnabled) {
            button?.isEnabled = isEnabled
            button?.setTextColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.black))
            button?.background = ContextCompat.getDrawable(requireActivity().applicationContext, R.drawable.shape_rectangle_disabled)
        } else {
            button?.isEnabled = isEnabled
            button?.setTextColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.white))
            button?.background = ContextCompat.getDrawable(requireActivity().applicationContext, R.drawable.shape_rectangle_green)
        }
    }

    private fun isLoading(loading: Boolean) { binding.tvAddress.text = if (loading) getString(R.string.loading) else "" }

    private fun isBtnClicked(state: Boolean) {
        if (state) {
            isEnableButton(binding.btnSubmit, false)
            binding.progressBar.visibility = View.VISIBLE
        } else {
            isEnableButton(binding.btnSubmit, true)
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val TAG = "CameraFragment"
    }
}
