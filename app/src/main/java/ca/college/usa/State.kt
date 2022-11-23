package ca.college.usa

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

data class State(
    var name: String? = null,
    var code: String? = null,
    var capital: String? = null,
    var area: Int? = null,
    var union: String? = null,
    var wiki: String? = null
    ) {

    /*
    The companion object is Kotlin's version of static or 'class' behaviour.
    Think of it as a singleton object that accompanies all instances of the class
     */
    companion object {
        //List of all data class instances
        val states = ArrayList<State>()

        fun flagInImageView(iv: ImageView, state: State) {
            val imageResource =
                iv.context.resources.getIdentifier(state.code, "drawable", iv.context.packageName)
            val res: Drawable? = AppCompatResources.getDrawable(iv.context, imageResource)
            iv.setImageDrawable(res)
        }

        // Deserialize a list of states from a file in JSON format
        fun readData(context: Context, fileName: String) {

            try {
                // load the data in an ArrayList
                val jsonString = readJson(context, fileName)!!
                val json = JSONObject(jsonString)
                val jArray = json.getJSONArray("states")

                // Initialize all individual state objects
                for (i in 0 until jArray.length()) {
                    val e = State()
                    e.name = jArray.getJSONObject(i).getString("name")
                    e.code = jArray.getJSONObject(i).getString("code")
                    e.capital = jArray.getJSONObject(i).getString("capital")
                    e.area = jArray.getJSONObject(i).getInt("area")
                    e.union = jArray.getJSONObject(i).getString("union")
                    e.wiki = jArray.getJSONObject(i).getString("wiki")
                    states.add(e)
                }
            } catch (e: JSONException) {
                // Log the error
                e.printStackTrace()
            }
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