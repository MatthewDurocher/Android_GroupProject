package ca.college.usa

import android.content.Context
import android.widget.ImageView
import org.json.JSONObject
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.Serializable
import java.util.*

//
class State(
    var name: String,
    var code: String,
    var capital: String,
    var area: Int,
    var union: String,
    var wikiUrl: String,

        ) : Comparable<Any?>, Serializable {

    // Comparison Function to sort the list after reading into JSON
    override fun compareTo(other: Any?): Int {
        return this.toString().compareTo(other.toString())
    }

    // Returns a String with a short description of the State
    override fun toString(): String {
        return name
    }

    // To insert an image in a provided ImageView
    // The image must be found in res/drawable and should contain
    // a name equal to the state code
    fun flagInImageView(iv: ImageView) {
        val uri = "@drawable/" + this.code.lowercase(Locale.getDefault())



        val imageResource = ctx!!.resources.getIdentifier(uri, null, ctx!!.packageName)

        val res = ctx!!.getDrawable(imageResource)



        iv.setImageDrawable(res)
    }

    companion object {
        private var ctx: Context? = null // required for JSON

        // Deserialize a list of states from a file in JSON format
        fun readData(ctx: Context?, fileName: String): ArrayList<State> {
            val mylist = ArrayList<State>()

            // Needed for drawables
            Companion.ctx = ctx
            try {
                // load the data in an ArrayList
                val jsonString = readJson(fileName, Companion.ctx)
                val json = JSONObject(jsonString)
                val states = json.getJSONArray("states")

                // Loop through the list in the json array
                for (i in 0 until states.length()) {
                    val e = State()
                    e.name = states.getJSONObject(i).getString("name")
                    e.drawable = states.getJSONObject(i).getString("code")
                    e.capital = states.getJSONObject(i).getString("capital")
                    e.area = states.getJSONObject(i).getInt("area")
                    e.union = states.getJSONObject(i).getString("union")
                    e.wikiUrl = states.getJSONObject(i).getString("wiki")
                    mylist.add(e)
                }
            } catch (e: JSONException) {
                // Log the error
                e.printStackTrace()
            }
            return mylist
        }

        // Returns a String with the contents of the JSON file
        private fun readJson(fileName: String, context: Context?): String? {
            var json: String? = null
            json = try {
                val `is` = context!!.assets.open(fileName)
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer, "ISO-8859-1")
            } catch (ex: IOException) {
                // Log the error
                ex.printStackTrace()
                return null
            }
            return json
        }
    }
}