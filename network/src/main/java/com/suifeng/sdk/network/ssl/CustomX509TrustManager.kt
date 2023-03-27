package com.suifeng.sdk.network.ssl

import com.suifeng.sdk.utils.log.LogUtil
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.ArrayList
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class CustomX509TrustManager : X509TrustManager {

    private val trustManagers: ArrayList<X509TrustManager> = ArrayList()

    init {
        initX509TrustManager()
    }

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        trustManagers.forEach {
            try {
                it.checkClientTrusted(chain, authType)
            } catch (e: Exception) {
                LogUtil.d(e)
            }
        }
        throw CertificateException("None of the TrustManagers trust this certificate chain")
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        trustManagers.forEach {
            try {
                it.checkServerTrusted(chain, authType)
                return
            } catch (e: Exception) {
                LogUtil.d(e)
            }
        }
        throw CertificateException("None of the TrustManagers trust this certificate chain")
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        val certificates = arrayListOf<X509Certificate>()
        trustManagers.forEach {
            it.acceptedIssuers.forEach {
                certificates.add(it)
            }
        }
        return certificates.toTypedArray()
    }

    private fun initX509TrustManager() {
        try {
            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
            trustManagers.add(tmf.trustManagers[0] as X509TrustManager)
        } catch (e: Exception) {
            LogUtil.d(e)
        }
    }

    fun getSSLContext(): SSLContext {
        val sslContext = SSLContext.getInstance("TLS")
        val array = arrayOfNulls<X509TrustManager>(trustManagers.size)
        trustManagers.toArray(array)
        sslContext.init(null, array, null)
        return sslContext
    }

}
