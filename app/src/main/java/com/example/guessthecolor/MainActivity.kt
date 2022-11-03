package com.example.guessthecolor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.guessthecolor.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appDB : UserDatabase
    private lateinit var binding: ActivityMainBinding
    private  var clicks : Int = 0
    private  var userScore : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appDB = UserDatabase.getDatabase(this)

        cleanTheSlate()


        var color : Array<String>? = null



        var abc: String
        var score : Int
        val randomIndex = (0..4).random()



        GlobalScope.launch {
            var ad = arrayListOf("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")
            abc = appDB.UserDAO().getData()[randomIndex].color.toString()
            score = appDB.UserDAO().getData()[0].userScore.toString().toInt()
            binding.score.text = score.toString()

            setColor(abc , binding.button)

            val array = Array(abc.length) { abc[it].toString() }
            val nameArray = array.toMutableList()

            ad = removeRedundancy(ad , array)

            for(i in abc.length..9){
                nameArray.add(ad.random().toString())
            }

            val shuffled = nameArray.shuffled()

            setCharacters(shuffled)
            color = array
            userScore = score
        }

        binding.textView1.setOnClickListener {
            checkColor(color , binding.textView1)
        }
        binding.textView2.setOnClickListener {
            checkColor(color , binding.textView2)
        }
        binding.textView3.setOnClickListener {
            checkColor(color , binding.textView3)
        }
        binding.textView4.setOnClickListener {
            checkColor(color , binding.textView4)
        }
        binding.textView5.setOnClickListener {
            checkColor(color , binding.textView5)
        }
        binding.textView6.setOnClickListener {
            checkColor(color , binding.textView6)
        }
        binding.textView7.setOnClickListener {
            checkColor(color , binding.textView7)
        }
        binding.textView8.setOnClickListener {
            checkColor(color , binding.textView8)
        }
        binding.textView9.setOnClickListener {
            checkColor(color , binding.textView9)
        }
        binding.textView10.setOnClickListener {
            checkColor(color , binding.textView10)
        }

        binding.hintButton.setOnClickListener {
            val temp = color!!.toMutableList().shuffled()

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Hint")
                .setCancelable(false)
                .setMessage(temp.toString())
                .setPositiveButton("Got It!"){ _,_ ->

                }.show()
        }

    }

    private fun cleanTheSlate() {
        binding.letter1.visibility = View.INVISIBLE
        binding.letter2.visibility = View.INVISIBLE
        binding.letter3.visibility = View.INVISIBLE
        binding.letter4.visibility = View.INVISIBLE
        binding.letter5.visibility = View.INVISIBLE
        binding.letter6.visibility = View.INVISIBLE
    }

    @SuppressLint("ResourceAsColor")
    private fun setColor(abc: String, button: ImageView) {
        when(abc){
            "RED"-> button.setImageResource(R.drawable.red)
            "GREEN"-> button.setImageResource(R.drawable.green)
            "YELLOW"-> button.setImageResource(R.drawable.yellow)
            "PURPLE"-> button.setImageResource(R.drawable.purple)
            "ORANGE"-> button.setImageResource(R.drawable.orange)
            "BLUE"-> button.setImageResource(R.drawable.blue)
        }
    }


    private fun removeRedundancy(ad: java.util.ArrayList<String>, array: Array<String>) : ArrayList<String>{
        for (i in 0..ad.lastIndex){
            for (j in 0..array.lastIndex){
                if (ad[i]==array[j]){
                    ad.drop(i)
                }
            }
        }
        return ad
    }


    private fun checkColor(color: Array<String>?, Textview: TextView ) {
        if (color!!.size-1 == clicks){
            gameCompleted()
        }
        if (color[clicks]==Textview.text){
            Toast.makeText(this,"correct",Toast.LENGTH_SHORT).show()
            Textview.visibility = View.INVISIBLE
            correctText(Textview.text.toString() , clicks)
            clicks += 1

        }
        else{
            Toast.makeText(this,"wrong",Toast.LENGTH_SHORT).show()
        }

    }

    private fun gameCompleted() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Hurray You won")
            .setCancelable(false)
            .setPositiveButton("next"){ _,_ ->
                restartActivity()
            }.show()

        updateUserScore(userScore)


    }

    @SuppressLint("SetTextI18n")
    private fun updateUserScore(userScore: Int) {
       GlobalScope.launch {
           appDB.UserDAO().updateScore(userScore+10)
           binding.score.text = (userScore+10).toString()
       }
    }

    private fun restartActivity() {
        val intent = intent
        finish()
        startActivity(intent)
    }

    private fun correctText(String: String, clicks: Int) {
        when(clicks){
            0-> {
                binding.letter1.text = String
                binding.letter1.visibility = View.VISIBLE
            }
            1-> {
                binding.letter2.text = String
                binding.letter2.visibility = View.VISIBLE
            }
            2-> {
                binding.letter3.text = String
                binding.letter3.visibility = View.VISIBLE
            }
            3-> {
                binding.letter4.text = String
                binding.letter4.visibility = View.VISIBLE
            }
            4-> {
            binding.letter5.text = String
            binding.letter5.visibility = View.VISIBLE
            }
            5->{
                binding.letter6.text = String
                binding.letter6.visibility = View.VISIBLE
            }
        }
    }


    private fun setCharacters(nameArray: List<String>) {
        binding.textView1.text = nameArray[0]
        binding.textView2.text = nameArray[1]
        binding.textView3.text = nameArray[2]
        binding.textView4.text = nameArray[3]
        binding.textView5.text = nameArray[4]
        binding.textView6.text = nameArray[5]
        binding.textView7.text = nameArray[6]
        binding.textView8.text = nameArray[7]
        binding.textView9.text = nameArray[8]
        binding.textView10.text = nameArray[9]
    }
}