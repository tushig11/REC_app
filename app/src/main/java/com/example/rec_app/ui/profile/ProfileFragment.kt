package com.example.rec_app.ui.profile

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.rec_app.MainActivity
import com.example.rec_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.io.File
import java.io.FileInputStream

class ProfileFragment : Fragment() {
    private val storage = Firebase.storage
    private val REQUEST_IMAGE = 100
    private var uploadTask: UploadTask? = null
    private lateinit var profileViewModel: ProfileViewModel
    private var imagePath: String? = "https://cdn2.vectorstock.com/i/1000x1000/20/76/man-avatar-profile-vector-21372076.jpg"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        profileViewModel.getCurrentUser().observe(viewLifecycleOwner, {
            name_profile.text = it.toString()
            fnameInput.text = it.fname.toEditable()
            lnameInput.text = it.lname.toEditable()
            emailInput.text = it.email.toEditable()
            phoneInput.text = it.phone.toEditable()

            if (it.imagePath != null){
                imagePath = it.imagePath
            }

            Glide.with(root.context)
                .load(imagePath)
                .override(300, 300)
                .into(root.image_profile);
        })

        root.edit_profile.setOnClickListener(){ editProfile() }
        root.update_profile.setOnClickListener(){ updateProfile() }
        root.logout_profile.setOnClickListener(){
            startActivity(Intent(root.context, MainActivity::class.java))
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(root.context, "Logged out successfully", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_profile.setOnClickListener(){
            takePictureIntent()
        }
    }

    private fun editProfile(){
        updateMode()
    }

    private fun updateProfile(){
        val fname = fnameInput.text.toString().trim()
        val lname = lnameInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()

        // Empty input checking
        if(TextUtils.isEmpty(fname)){
            fnameInput.error = "First name required"
            return
        }
        if(TextUtils.isEmpty(lname)){
            lnameInput.error = "Last name required"
            return
        }

        if(TextUtils.isEmpty(phone)){
            phoneInput.error = "Phone number required"
            return
        }

        profileViewModel.updateProfile(fname, lname, phone)
        displayMode()
    }

    private fun displayMode(){
        //changing UI to update state
        fnameTxt.visibility = View.GONE
        lnameTxt.visibility = View.GONE
        update_profile.visibility = View.GONE
        edit_profile.visibility = View.VISIBLE

        phoneTxt.isFocusable = false
        phoneTxt.isEnabled = false
    }

    private fun updateMode(){
        //changing UI to display state
        fnameTxt.visibility = View.VISIBLE
        lnameTxt.visibility = View.VISIBLE
        update_profile.visibility = View.VISIBLE
        edit_profile.visibility = View.GONE
        phoneTxt.isFocusable = true
        phoneTxt.isEnabled = true

    }

    private fun takePictureIntent(){
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, REQUEST_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            val imageUri = data?.data
            if(uploadTask != null && uploadTask!!.isInProgress){
                Toast.makeText(this.context, "Upload in progress. ", Toast.LENGTH_SHORT).show()
            }else{ uploadImage(imageUri!!) }
        }
    }

    private fun uploadImage(uri: Uri){
        val picturePath = "profile_images/${System.currentTimeMillis()}.${getExtension(uri)}"
        val pictureRef = storage.reference.child("$picturePath")
        uploadTask = pictureRef.putFile(uri)
        uploadTask!!.addOnFailureListener {
            Toast.makeText(this.context, "Upload Failed", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->
            Toast.makeText(this.context, "Upload Success", Toast.LENGTH_SHORT).show()
            pictureRef.downloadUrl.addOnSuccessListener {
                profileViewModel.updateProfileImage(it.toString())
            }

        }
    }

    private fun getExtension(uri: Uri): String{
        val context = this.requireContext()
        val cr = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri))!!
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}