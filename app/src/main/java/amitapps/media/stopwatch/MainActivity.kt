package amitapps.media.stopwatch

import amitapps.media.stopwatch.databinding.ActivityMainBinding
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var seconds = 0
    private var isStopwatchRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            isStopwatchRunning = savedInstanceState.getBoolean("isStopwatchRunning")
        }
        runTimer()
    }

    private fun runTimer() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeat(1000000000) {
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val second = seconds % 60
                val time = "$hours:$minutes:$second"
                binding.timeView.text = time

                if(isStopwatchRunning) seconds++
                delay(1000)
            }
        }
    }
    // Save the state of the stopwatch
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("isStopwatchRunning", isStopwatchRunning)
    }

    override fun onPause() {
        super.onPause()
        isStopwatchRunning = false
    }

    override fun onResume() {
        super.onResume()
        if(isStopwatchRunning) isStopwatchRunning = true
    }

    fun onClickStart(view: View?) {
        isStopwatchRunning = true
    }

    fun onClickStop(view: View) {
        isStopwatchRunning = false
    }

    fun onClickReset(view: View) {
        isStopwatchRunning = false
        seconds = 0
    }
}