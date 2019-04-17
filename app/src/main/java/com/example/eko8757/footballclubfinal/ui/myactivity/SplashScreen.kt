package com.example.eko8757.footballclubfinal.ui.myactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.eko8757.footballclubfinal.R
import org.jetbrains.anko.*

class SplashScreen : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private var SPLASH_DELAY: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashUI().setContentView(this)

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    class splashUI: AnkoComponent<SplashScreen> {
        override fun createView(ui: AnkoContext<SplashScreen>) = with(ui) {
            relativeLayout {
                setBackgroundResource(R.color.colorPrimaryDark)
                imageView(R.drawable.soccer_ball).lparams(width = matchParent) {
                    centerInParent()
                    width = dip(150)
                    height = dip(150)
                }
            }
        }
    }

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}
