package com.oliviermarteaux.localshared.firebase.firestore.utils

import android.content.Context
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post

suspend fun uploadSamplePosts(context: Context, upload: suspend (Post) -> Result<Unit>){
    val posts = generateSamplePosts()
    posts.forEach {
        val internetPhotoUrl = it.photoUrl?:""
        val localPhotoUrl = downloadImageToLocalFile(context,  internetPhotoUrl)
        val post = it.copy(photoUrl = localPhotoUrl.toString())
        upload(post)
    }
}