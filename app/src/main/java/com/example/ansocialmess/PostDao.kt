package com.example.ansocialmess

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db = Firebase.firestore
    val postCollections = db.collection("posts")
    val auth = Firebase.auth

    fun addPost(text:String){
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch {
            val userDao = UserDao()
            val user =  userDao.getUserById(currentUserId).await().toObject(User::class.java)!!

            val currentTime = System.currentTimeMillis()
            val post = Post(text,user, currentTime)
            postCollections.document().set(post)
        }
    }

    fun getPostById(postId: String) : Task<DocumentSnapshot> {
        return postCollections.document(postId).get()
    }

    fun updateLikes(postId :String){
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)

            val isLiked = post?.likedBy?.contains(currentUserId)
            if(isLiked == true){
                post.likedBy.remove(currentUserId)
            }
            else{
                post?.likedBy?.add(currentUserId)
            }
            postCollections.document(postId).set(post!!)
        }
    }
}