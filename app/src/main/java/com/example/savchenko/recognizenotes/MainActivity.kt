package com.example.savchenko.recognizenotes

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.app.Service
import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.MediaRecorder
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.savchenko.recognizenotes.dialogs.choose_locale.ChooseLocaleDialog
import com.example.savchenko.recognizenotes.interfaces.LocaleSetter
import kotlinx.android.synthetic.main.activity_main.*
import ru.yandex.speechkit.gui.RecognizerActivity
import java.io.File
import java.io.IOException
import java.util.*
import ru.yandex.speechkit.Recognizer
import ru.yandex.speechkit.SpeechKit
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.media.MediaPlayer
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import ru.yandex.speechkit.RecognizerListener


class MainActivity : AppCompatActivity(), LocaleSetter {
    private val REQ_CODE_SPEECH_INPUT = 100
    private val REQ_CODE_SPEECH_YANDEX = 101
    private val TAG = "MainActivity"
    private var usesLocal:Locale = Locale.getDefault()
    private var mPlayer: MediaPlayer? = null
    private var playing = false
    private var recording = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SpeechKit.getInstance().configure(applicationContext, "41a32ce6-731d-4af4-9cca-433f56aba378")
        setContentView(R.layout.activity_main)
        etTaskTitle.setSelection(etTaskTitle.text.length)
        btnSpeak.setOnClickListener({
            if(!recording) {
                startRecording()
                recording = true
            }else {
                stopRecording()
                recording = false
            }
//            yandexRecognize()
        })
        btnPlay.setOnClickListener({
            playRecord()
        })
        rlTextLayout.setOnClickListener({
            etTaskTitle.requestFocus()
            showKeyboard()
        })
        hideKeyboard()
    }

    private fun playRecord() {
        if(playing){
            stopPlaying()
            playing = false
        }else{
            startPlaying()
            playing = true
        }
    }

     private fun startPlaying() {
        mPlayer = MediaPlayer()
        try {
            mPlayer?.setDataSource(filesDir.path + "/test.3gp")
            mPlayer?.prepare()
            mPlayer?.start()
        } catch (e:IOException) {
            Log.e(TAG, "prepare() failed")
        }
    }


    private fun stopPlaying() {
        mPlayer?.release()
        mPlayer = null
    }


    fun hideKeyboard(){
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun showKeyboard(){
        val imm = this.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etTaskTitle, 0)
    }

    override fun setLocal(local: Locale) {
        usesLocal = local
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, usesLocal)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speach recognize")
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(applicationContext, "not supported", Toast.LENGTH_SHORT).show()
        }
    }

    private fun yandexRecognize(){
        val intent = Intent(this, RecognizerActivity::class.java)
        intent.putExtra(RecognizerActivity.EXTRA_MODEL, Recognizer.Model.QUERIES)
        intent.putExtra(RecognizerActivity.EXTRA_LANGUAGE, usesLocal.language  + "-" + usesLocal.country )
        startActivityForResult(intent, REQ_CODE_SPEECH_YANDEX)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
//                    stopRecording()
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    Log.i(TAG, result[0])
                    etTaskTitle.append(" " + result[0])
                }
            }
            REQ_CODE_SPEECH_YANDEX -> {
                println(data?.getStringExtra(RecognizerActivity.EXTRA_RESULT))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.choose_language_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.nav_choose_language -> {
                val chooseLocaleDialog = ChooseLocaleDialog()
                chooseLocaleDialog.localSetter = this
                chooseLocaleDialog.show(fragmentManager, "chooseLocale")
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private var mRecorder: MediaRecorder? = null

    private fun startRecording() {
        mRecorder = MediaRecorder()
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        val file = File(filesDir.path + "/test.3gp")
        file.createNewFile()
        mRecorder!!.setOutputFile(file.path)
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            mRecorder!!.prepare()
        } catch (e: IOException) {
            Log.e(TAG, "prepare() failed")
        }

        mRecorder!!.start()
    }

    private fun stopRecording() {
        mRecorder!!.stop()
        mRecorder!!.release()
        mRecorder = null
    }

//    private val REQUEST_PERMISSION_CODE = 1
//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun createAndStartRecognizer() {
//        val context = this?: return
//
//        if (ContextCompat.checkSelfPermission(context!!, RECORD_AUDIO) != PERMISSION_GRANTED) {
//            requestPermissions(arrayOf(RECORD_AUDIO), REQUEST_PERMISSION_CODE)
//        } else {
//            // Reset the current recognizer.
//            resetRecognizer()
//            // To create a new recognizer, specify the language, the model - a scope of recognition to get the most appropriate results,
//            // set the listener to handle the recognition events.
//            recognizer = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.NOTES, this)
//            // Don't forget to call start on the created object.
//            recognizer?.start()
//        }
//    }
//
//    fun resetRecognizer() {
//        if (recognizer != null) {
//            recognizer?.cancel();
//            recognizer = null;
//        }
//    }
}
