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
class State : Comparable<Any?>, Serializable {
    private var name: String? = null // state name

    // Accessor
    var drawable: String? = null // 2-letter code
        private set
    private var capital: String? = null // state capital
    private var area = 0 // state area in square km
    private var union: String? = null // entry date into union
    private var wikiUrl: String? = null // wiki URL of the state

    // Default Constructor
    constructor() {
        setName(null)
        setCode(null)
        setCapital(null)
        setArea(0)
        setUnion(null)
        setWikiUrl(null)
    }

    // Constructor
    constructor(
        name: String?,
        code: String?,
        capital: String?,
        area: Int,
        union: String?,
        wikiUrl: String?
    ) {
        setName(name)
        setCode(code)
        setCapital(capital)
        setArea(area)
        setUnion(union)
        setWikiUrl(wikiUrl)
    }

    // Comparison Fonction to sort the list after reading into JSON
    override fun compareTo(other: Any?): Int {
        return this.toString().compareTo(other.toString())
    }

    // Accessor
    fun getName(): String? {
        return name
    }

    // Mutator
    fun setName(name: String?) {
        this.name = name
    }

    // Accesseur de l'attribut code
    fun getCode(): String? {
        return drawable
    }

    // Accessor
    fun setCode(code: String?) {
        drawable = code
    }

    //Accessor
    fun getCapital(): String? {
        return capital
    }

    // Mutator
    fun setCapital(capital: String?) {
        this.capital = capital
    }

    // Accessor
    fun getArea(): Int {
        return area
    }

    // Mutator
    fun setArea(area: Int) {
        this.area = area
    }

    // Accessor
    fun getWikiUrl(): String? {
        return wikiUrl
    }

    // Mutator
    fun setWikiUrl(wikiUrl: String?) {
        this.wikiUrl = wikiUrl
    }

    // Accessor
    fun getUnion(): String? {
        return union
    }

    // Mutator
    fun setUnion(union: String?) {
        this.union = union
    }

    // Returns a String with a short description of the State
    override fun toString(): String {
        return getName()!!
    }

    // To insert an image in a provided ImageView
    // The image must be found in res/drawable and should contain
    // a name equal to the state code
    fun flagInImageView(iv: ImageView) {
        val uri = "@drawable/" + drawable!!.lowercase(Locale.getDefault())
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