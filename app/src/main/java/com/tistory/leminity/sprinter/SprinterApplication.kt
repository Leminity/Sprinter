package com.tistory.leminity.sprinter

import android.app.Application
import android.content.Context
import com.kakao.auth.*

class SprinterApplication : Application() {

    private inner class KakaoSDKAdapter : KakaoAdapter() {

        /**
         * custom setup to Kakao Session Config
         */
        override fun getSessionConfig(): ISessionConfig {
            return object: ISessionConfig {
                override fun isSaveFormData(): Boolean {
                    return false
                }

                override fun getAuthTypes(): Array<AuthType> {
                    return Array<AuthType>(1) {AuthType.KAKAO_TALK_ONLY}
                }

                override fun isSecureMode(): Boolean {
                    return false
                }

                override fun getApprovalType(): ApprovalType {
                    return ApprovalType.INDIVIDUAL
                }

                override fun isUsingWebviewTimer(): Boolean {
                    return false
                }
            }
        }

        override fun getApplicationConfig(): IApplicationConfig {
            return object: IApplicationConfig {
                override fun getApplicationContext(): Context {
                    return this@SprinterApplication.applicationContext
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSDK.init(KakaoSDKAdapter())
    }
}