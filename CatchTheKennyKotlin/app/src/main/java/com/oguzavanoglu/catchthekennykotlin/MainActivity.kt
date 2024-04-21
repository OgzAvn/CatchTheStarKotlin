package com.oguzavanoglu.catchthekennykotlin

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.R
import com.oguzavanoglu.catchthekennykotlin.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var score: Int = 0
    private val random = Random()
    var randomImageRunnable : Runnable = Runnable{ }
    var TimerRunnable : Runnable = Runnable{ }
    var handler : Handler = Handler(Looper.getMainLooper())
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        randomImageRunnable = object : Runnable{
            override fun run() {
                randomImage()
                handler.postDelayed(randomImageRunnable,500)
            }

        }

        handler.post(randomImageRunnable)

        TimerRunnable = object : Runnable{
            override fun run() {
                countDownTimer = object : CountDownTimer(10000,1000) {
                    override fun onTick(millisUntilFinished: Long) {

                        binding.timeTextView.text = "Time : ${millisUntilFinished/1000}"
                    }

                    override fun onFinish() {

                        showalertDialog()

                    }

                }.start()
            }

        }
        handler.post(TimerRunnable)


    }


    fun showalertDialog(){

        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.setTitle("Restart")
        alertDialog.setMessage("Are you sure to restart game?")
        alertDialog.setPositiveButton("Yes",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                handler.removeCallbacks(TimerRunnable)
                handler.removeCallbacks(randomImageRunnable)
                score = 0
                binding.scoreTextView.text = "Score : ${score}"
                handler.post(randomImageRunnable)
                handler.post(TimerRunnable)
            }

        })

        alertDialog.setNegativeButton("No",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                handler.removeCallbacks(TimerRunnable)
                handler.removeCallbacks(randomImageRunnable)
                binding.imageView.isEnabled = false
            }

        })

        alertDialog.show()
    }


    fun imageClicked(view: View) {

        score = score!! + 1
        binding.scoreTextView.text = "Score : ${score}"
    }

    fun randomImage(){

        val emptySpaceX : Int = binding.linearLayout.width
        val emptySpaceY : Int = binding.linearLayout.height

        val maxX : Int = emptySpaceX - binding.imageView.width
        val maxY : Int = emptySpaceY - binding.imageView.height

        val randomX : Int = random.nextInt(maxX + 1 )
        val randomY : Int = random.nextInt(maxY + 1)

        binding.imageView.x = randomX.toFloat()
        binding.imageView.y = randomY.toFloat()


    }
}