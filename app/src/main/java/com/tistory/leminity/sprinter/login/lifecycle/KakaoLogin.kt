package com.tistory.leminity.sprinter.login.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException

class KakaoLogin(kakaoSessionCallback: IKakaoSessionCallback) : LifecycleObserver {

    private var mKakaoSessionCallback: IKakaoSessionCallback = kakaoSessionCallback
    private lateinit var mKakaoCallback: SessionCallback

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun registerCallbackLoginKakao() {
        mKakaoCallback = SessionCallback()

        Session.getCurrentSession().addCallback(mKakaoCallback)
        Session.getCurrentSession().checkAndImplicitOpen()

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unregisterCallbackLoginKakao() {
        Session.getCurrentSession().removeCallback(mKakaoCallback)

    }

    private inner class SessionCallback : ISessionCallback {

        override fun onSessionOpened() {
            mKakaoSessionCallback.onSessionOpend()
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            mKakaoSessionCallback.onSessionOpenFailed(exception)
        }
    }

    public interface IKakaoSessionCallback {
        fun onSessionOpend()
        fun onSessionOpenFailed(exception: KakaoException?)
    }

}