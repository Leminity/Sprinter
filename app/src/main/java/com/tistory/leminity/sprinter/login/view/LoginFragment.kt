package com.tistory.leminity.sprinter.login.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.kakao.util.exception.KakaoException
import com.tistory.leminity.sprinter.R
import com.tistory.leminity.sprinter.login.lifecycle.KakaoLogin
import javax.xml.transform.Result

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class LoginFragment : Fragment() {

    private lateinit var mKakaoLogin: KakaoLogin

    private var listenerLogin: OnLoginFragmentListener? = null


    @BindView(R.id.login_button_kakao)
    private lateinit var mLoginButtonKakao: com.kakao.usermgmt.LoginButton

    @BindView(R.id.login_button_facebook)
    private var mLoginButtonFacebook: com.facebook.login.widget.LoginButton ?= null
    private var mCallbackManager: CallbackManager ?= null

    companion object {
        const val TAG = "LoginFragment"

        @JvmStatic
        fun newInstance() = LoginFragment()
//                LoginFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        registerLoginCallbackKakao()
        registerLoginCallbackFacebook()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoginFragmentListener) {
            listenerLogin = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnLoginFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerLogin = null
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterLoginCallback()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun registerLoginCallbackKakao() {
        mKakaoLogin = KakaoLogin(object : KakaoLogin.IKakaoSessionCallback {
            override fun onSessionOpend() {
            }

            override fun onSessionOpenFailed(exception: KakaoException?) {
            }
        })
        lifecycle.addObserver(mKakaoLogin)

    }

    fun unregisterLoginCallback() {
        lifecycle.removeObserver(mKakaoLogin)

    }

    fun registerLoginCallbackFacebook() {
        mCallbackManager = CallbackManager.Factory.create()

        mLoginButtonFacebook?.setReadPermissions("email")
        mLoginButtonFacebook?.setFragment(this)

        mLoginButtonFacebook?.registerCallback(mCallbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                var resultString = if(result == null) "empty" else result.toString()
                Toast.makeText(context, resultString, Toast.LENGTH_LONG).show()
            }

            override fun onCancel() {
                Toast.makeText(context, "cancel", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
            }
        })
    }

    interface OnLoginFragmentListener {
        fun onFragmentInteraction(uri: Uri)
    }
}
