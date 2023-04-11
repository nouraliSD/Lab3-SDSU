package com.lab3.cardgame

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {


    private lateinit var imageViewCard1: ImageView
    private lateinit var imageViewCard2: ImageView

    private lateinit var textViewPlayer1: TextView
    private lateinit var textViewPlayer2: TextView

    private lateinit var textViewTieGame: TextView

    private lateinit var textViewWinner: TextView

    private lateinit var buttonDeal: Button

    lateinit var messageTV: TextView

    private lateinit var random: Random

    // both players start at 0
    private var player1 = 0
    private var player2 = 0


    private var cardsArray = intArrayOf(
        R.drawable.hearts2,
        R.drawable.hearts3,
        R.drawable.hearts4,
        R.drawable.hearts5,
        R.drawable.hearts6,
        R.drawable.hearts7,
        R.drawable.hearts8,
        R.drawable.hearts9,
        R.drawable.hearts10,
        R.drawable.hearts12,
        R.drawable.hearts13,
        R.drawable.hearts14,
        R.drawable.hearts15
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing variable for video view on below line.
        messageTV = findViewById(R.id.idTVMessage)
        // registering context menu on below line.
        registerForContextMenu(messageTV)

        // Link those objects with their respective id's that we have given in .XML file

        random = Random

        //init objects
        imageViewCard1 = findViewById(R.id.iv_card1)
        imageViewCard2 = findViewById(R.id.iv_card2)

        imageViewCard1.setImageResource(R.drawable.card_back)
        imageViewCard2.setImageResource(R.drawable.card_back)

        textViewPlayer1 = findViewById(R.id.tv_player1)
        textViewPlayer2 = findViewById(R.id.tv_player2)

        textViewTieGame = findViewById(R.id.tv_tie)
        textViewTieGame.visibility = View.INVISIBLE

        textViewWinner = findViewById(R.id.tv_winner)
        textViewWinner.visibility = View.INVISIBLE
        buttonDeal = findViewById(R.id.b_deal)

        // here you have to register a view for context menu you can register any view
        // like listview, image view, textview, button etc
        registerForContextMenu(textViewPlayer1)

        buttonDeal.setOnClickListener {
            //hide war label
            textViewTieGame.visibility = View.INVISIBLE
            textViewWinner.visibility = View.INVISIBLE

            //generate cards
            val card1 = random.nextInt(cardsArray.size)
            val card2 = random.nextInt(cardsArray.size)

            //set images
            setCardImage(card1, imageViewCard1)
            setCardImage(card2, imageViewCard2)

            //compare the cards
            compareCards(card1, card2)

            winner(player1, player2)

        }
    }


    private fun setCardImage(number: Int, image: ImageView) {
        image.setImageResource(cardsArray[number])
    }

    private fun compareCards(card1: Int, card2: Int) {
        //compare the cards
        if (card1 > card2) {
            player1++
            textViewPlayer1.text = "Player 1: $player1"
        } else if (card1 < card2) {
            player2++
            textViewPlayer2.text = "Player 2: $player2"
        } else {
            //show Tie label
            textViewTieGame.visibility = View.VISIBLE
        }
    }

    private fun winner(player1: Int, player2: Int) {
        if (player1 == 10 && player2 == 10) {
            textViewWinner.text = "Tie Game"
            textViewWinner.visibility = View.VISIBLE
            reset()
            Alert("Tie Game")
        }
        if (player1 == 10) {
            textViewWinner.text = "Player 1 Wins"
            textViewWinner.visibility = View.VISIBLE
            reset()
            Alert("Player 1 Wins")
        }
        if (player2 == 10) {
            textViewWinner.text = "Player 2 Wins"
            textViewWinner.visibility = View.VISIBLE
            reset()
            Alert("Player 2 Wins")
        }
    }

    private fun reset() {
        player1 = 0
        player2 = 0
        textViewPlayer1.text = "Player 1: $player1"
        textViewPlayer2.text = "Player 2: $player2"
    }

    private fun Alert(Winner: String) {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("Done Playing ?")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("END GAME", DialogInterface.OnClickListener {
                    dialog, id -> finish()
            })
            // negative button text and action
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(Winner)
        // show alert dialog
        alert.show()
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // on below line we are setting header title for menu.
        menu!!.setHeaderTitle("Choose Player Name")
        // on below line we are adding menu items
        menu.add(0, v!!.getId(), 0, "Mike")
        menu.add(0, v!!.getId(), 0, "Alex")
        menu.add(0, v!!.getId(), 0, "Nova")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // on below line we are setting the selected item text for message text view
        messageTV.text = item.title
        return true
    }
}

