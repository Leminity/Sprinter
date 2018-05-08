package com.tistory.leminity.sprinter.login.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException

class KakaoLoginLifeCycleObserver(kakaoLoginCallback: IKakaoLoginCallback) : LifecycleObserver {

    private var mKakaoLoginCallback: IKakaoLoginCallback = kakaoLoginCallback
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
            mKakaoLoginCallback.onSessionOpend()
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            mKakaoLoginCallback.onSessionOpenFailed(exception)
        }
    }

    public interface IKakaoLoginCallback {
        fun onSessionOpend()
        fun onSessionOpenFailed(exception: KakaoException?)
    }

}