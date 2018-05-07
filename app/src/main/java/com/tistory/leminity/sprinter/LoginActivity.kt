package com.tistory.leminity.sprinter

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import com.tistory.leminity.sprinter.framework.SLog
import com.tistory.leminity.sprinter.login.view.LoginFragment
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity(), LoginFragment.OnLoginFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //frameLayoutLogin
        attachLoginFragment()

        var appHash = getKeyHash()
        if(appHash == null)
            appHash = "hashNotExist"
        SLog.d(appHash)
    }

    private fun attachLoginFragment() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.frameLayoutLogin, LoginFragment.newInstance(), LoginFragment.TAG)
                .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getKeyHash(): String? {
        var pkgInfo = packageManager.getPackageInfo(applicationContext.packageName, PackageManager.GET_SIGNATURES)

        if (pkgInfo == null) {
            return null
        }

        for (signature in pkgInfo.signatures) {
            try {
                val messageDigest = MessageDigest.getInstance("SHA")
                messageDigest.update(signature.toByteArray())
                return Base64.encodeToString(messageDigest.digest(), Base64.NO_WRAP)
            } catch (e: NoSuchAlgorithmException) {

            }
        }

        return null
    }
}
