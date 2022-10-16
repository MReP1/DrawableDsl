package little.goose.drawabledsl

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import little.goose.drawable.State
import little.goose.drawable.color.colorSelector

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.bt_hello_world)

        button.backgroundTintList = colorSelector {
            withState(State.PRESSED) { color = getColor(R.color.purple_200) }
            defState { color = getColor(R.color.teal_200) }
        }

        button.setOnClickListener { }

    }
}