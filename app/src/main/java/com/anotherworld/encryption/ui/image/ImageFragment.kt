package com.anotherworld.encryption.ui.image

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.R.attr
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.*
import android.util.Log
import android.view.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import com.anotherworld.encryption.databinding.FragmentImageBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import androidx.core.graphics.drawable.toBitmap
import com.anotherworld.encryption.*
import okio.ByteString.Companion.toByteString
import java.io.FileOutputStream
import java.util.*
import kotlin.math.absoluteValue

var bitmap : Bitmap? = null

class ImageFragment : Fragment() {

    private lateinit var galleryViewModel: ImageViewModel
    private var _binding: FragmentImageBinding? = null
    private lateinit var preview: ImageView
    private lateinit var code: EditText
    private lateinit var copy_code: ImageButton
    private lateinit var load: ImageButton
    private lateinit var save: ImageButton
    private lateinit var random: ImageButton
    private lateinit var fullscreen: Button
    private lateinit var decode: TextView
    private lateinit var copy_decode: ImageButton
    var secret = ""
    var full: Int = 0 //0 - not in fullscreen; 1 - in fullscreen
    var heightPreview = Data().getHeight()
    var widthPreview = Data().getWidth()

    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71
    var data: Data = Data()

    override fun onResume() {
        super.onResume()
        code.setText(Data().getKeyImage())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === PICK_IMAGE_REQUEST && resultCode === RESULT_OK && attr.data != null) {
            filePath = data?.data
            try {
                lifecycleScope.launch {
                    when{
                        data!!.data!!.lastPathSegment.toString().contains("image") -> {
                            try{
                                bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, filePath)
                                val stream: ByteArrayOutputStream = ByteArrayOutputStream()
                                bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                                val byteArray = stream.toByteArray()
                                val bmp: Bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                                preview.setImageBitmap(bmp)
                                //Log.d("QQQQQ-IMG", byteArray.toByteString().toString())
                                Data().setWidth(preview.width)
                                Data().setHeight(preview.height)
                                when(Data().getTypeImage()){
                                    0 -> {
                                        val a = AES(byteArray, secret, "AES/CBC/PKCS5Padding")
                                        code.setText(a.key)
                                        if(a.encrypt()){
                                            decode.setText(Data().getValue())
                                        }
                                        else Toast.makeText(context, R.string.ops, Toast.LENGTH_SHORT).show()
                                    }
                                    1 -> {
                                        val gms = GMSFORIMAGE(byteArray, secret)
                                        code.setText(gms.key)
                                        if(gms.encrypt()){
                                            decode.setText(Data().getValue())
                                        }
                                        else Toast.makeText(context, R.string.ops, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }catch (e: Exception){
                                Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                            }
                        }
                        data!!.data!!.lastPathSegment.toString().contains("document") -> {
                            try{
                                val name: String = data?.data!!.lastPathSegment.toString().substringAfterLast("/").substringBeforeLast(".").substringBefore("-CIPHER")
                                val content = FileInputStream(File("storage/self/" + data.data!!.path.toString().replaceFirst("/", "").substringAfter("/").replaceFirst(":", "/"))).bufferedReader().use { it.readText() }.toString()
                                preview.setImageBitmap(cour(name, content))
                            }catch (e: Exception){
                                Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                            }
                        }
                        else -> {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun cour(name: String, content: String): Bitmap{
        return when(Data().getTypeImage()){
            0 -> {
                val arrSTR: String = AES(byteArrayOf(), name, "AES/CBC/PKCS5Padding", content).decrypt()
                val lstr = arrSTR.split(",")
                val mas1: Array<Int> = lstr.map { it.toInt() }.toTypedArray()
                val b: ByteArray = mas1.foldIndexed(ByteArray(mas1.size)) { i, a, v -> a.apply { set(i, v.toByte()) } }
                val bmp: Bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
                bmp
            }
            else -> {
                val arrSTR: String = GMSFORIMAGE(byteArrayOf(), name, content).decrypt()
                val lstr = arrSTR.split(",")
                val mas1: Array<Int> = lstr.map { it.toInt() }.toTypedArray()
                val b: ByteArray = mas1.foldIndexed(ByteArray(mas1.size)) { i, a, v -> a.apply { set(i, v.toByte()) } }
                val bmp: Bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
                bmp
            }
        }
    }
    private fun chooseImage() {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        data = Data()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        galleryViewModel = ViewModelProvider(this).get(ImageViewModel::class.java)
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        val root: View = binding.root
        preview = root.findViewById(R.id.preview)
        code = root.findViewById(R.id.code)
        copy_code = root.findViewById(R.id.copy_code)
        load = root.findViewById(R.id.load)
        save = root.findViewById(R.id.save)
        random = root.findViewById(R.id.random)
        fullscreen = root.findViewById(R.id.fullscreen)
        decode = root.findViewById(R.id.decode)
        copy_decode = root.findViewById(R.id.copy_decode)
        random.setOnClickListener { code.setText(Math.random().toString().replace(".", (Math.random() * 10).toInt().toString()).substring(0, 16)) }
        load.setOnClickListener {
            decode.setText("")
            if (code.text.toString().isEmpty()){
                code.setText(Math.random().toString().replace(".", (Math.random() * 10).toInt().toString()).substring(0, 16))
            }
            secret = code.text.toString()
            chooseImage()
        }
        fullscreen.setOnClickListener {
            if(full == 0){
                full = 1
                preview.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            }
            else{
                full = 0
                preview.layoutParams = FrameLayout.LayoutParams(widthPreview, heightPreview)
            }
        }
        copy_decode.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", decode.text.toString())
            clipboard.setPrimaryClip(clip)
            Snackbar.make(it, R.string.copied, Snackbar.LENGTH_SHORT).show()
        }
        copy_code.setOnClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", code.text.toString())
            clipboard.setPrimaryClip(clip)
            Snackbar.make(it, R.string.copied, Snackbar.LENGTH_SHORT).show()
        }
        save.setOnClickListener {
            try {
                if(data.getValue().isNotEmpty() && decode.text.toString().isNotEmpty()){
                    val path = "storage/self/primary/Download/" + code.text.toString() + "-CIPHER" + ".txt"
                    val file = File(path)
                    file.createNewFile()
                    file.writeText(data.getValue())
                    Snackbar.make(it, R.string.success, Snackbar.LENGTH_SHORT).show()
                }
                else if(decode.text.toString().isEmpty()){
                    val bi: Bitmap = preview.drawable.toBitmap()
                    val file = File("storage/self/primary/Download/" + code.text.toString() + "-CIPHER_IMAGE" + ".png")
                    val out = FileOutputStream(file)
                    bi.compress(Bitmap.CompressFormat.PNG, 100, out)
                    out.flush()
                    out.close()
                    Snackbar.make(it, R.string.success, Snackbar.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                Snackbar.make(it, R.string.check, Snackbar.LENGTH_SHORT).show()
            }
        }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}