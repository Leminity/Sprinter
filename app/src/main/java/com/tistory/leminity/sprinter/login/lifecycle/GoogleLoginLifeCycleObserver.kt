package com.tistory.leminity.sprinter.login.lifecycle

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleLoginLifeCycleObserver(iGoogleLoginCallback: IGoogleLoginCallback) : LifecycleObserver {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    var mIGoogleLoginCallback: IGoogleLoginCallback = iGoogleLoginCallback

    fun getSignInIntent(): Intent {
        return mGoogleSignInClient?.signInIntent
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun registerCallbackLoginGoogle() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        var parentActivity = mIGoogleLoginCallback.getActivity()
        parentActivity?.let {
            mGoogleSignInClient = GoogleSignIn.getClient(parentActivity, gso)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun checkExistSignedUser() {
        var context = mIGoogleLoginCallback.getContext()
        context?.let {
            var account = GoogleSignIn.getLastSignedInAccount(context)
            account?.let {
                mIGoogleLoginCallback.onExistSignedUser(account)
            }

        }
    }


    interface IGoogleLoginCallback {
        fun getContext(): Context?
        fun getActivity(): Activity?
        fun onExistSignedUser(googleSignInAccount: GoogleSignInAccount)
    }

}