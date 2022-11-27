package ca.college.usa

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.decodeFromString

/**
 * Full Name: Matthew Durocher, Irina Salikhova
 *
 * Student ID: 41036621
 *
 * Course: CST3104
 *
 * Term:  Fall 2022
 *
 * Assignment: Team Project
 *
 * Date : November 4, 2022
 */

@Serializable
data class State(
    var name: String,
    var code: String,
    var capital: String,
    var area: Int,
    var union: String,
    var wiki: String
    ) {

    /* Assigns the given State's flag to an ImageView */
    fun flagInImageView(iv: ImageView) {
        val imageResource =
            iv.context.resources.getIdentifier(this.code, "drawable", iv.context.packageName)
        val res: Drawable? = AppCompatResources.getDrawable(iv.context, imageResource)
        iv.setImageDrawable(res)
    }

    /*
    The companion object is Kotlin's version of static or 'class' behaviour.
    Think of it as a singleton object that accompanies all instances of the class
     */
    companion object {
        //List of all data class instances
        var states : ArrayList<State>? = null

        // Deserialize a list of states from a file in JSON format
        fun readData(context: Context): ArrayList<State> {
            states = ArrayList()

            try {
                // load the data in an ArrayList
                val jsonString = readJson(context, "usa.json")!!
                val json = JSONObject(jsonString)
                val jArray = json.getJSONArray("states")

                // Initialize all individual state objects

                for (i in 0 until jArray.length()) {
                    val stateString = jArray.getJSONObject(i).toString()
                    // Initializes the parametrized type from the json string "{'name':'Canada'...}"
                    val newState = Json.decodeFromString<State>(stateString)
                    states!!.add(newState)
                }
            } catch (e: JSONException) {
                // Log the error
                e.printStackTrace()
            }

            return states as ArrayList<State>
        }

        // Returns a String with the contents of the JSON file
        private fun readJson(context: Context, fileName: String): String? {
            val json = try {
                val stream: InputStream = context.assets.open(fileName)
                val size = stream.available() //estimate number of readable bytes
                val buffer = ByteArray(size) // Creates a ByteArray of size 'size'
                stream.read(buffer) //Takes bytes from input and stores them in 'buffer'
                stream.close()
                String(buffer, charset("ISO-8859-1"))
            } catch (ex: IOException) {
                // Log the error
                ex.printStackTrace()
                return null
            }
            return json
        }
    }
}