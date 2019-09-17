package pl.szymonmazanik.gotapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.szymonmazanik.gotapp.BuildConfig
import pl.szymonmazanik.gotapp.R
import timber.log.Timber

/**
 * This activity holds appbar and [CharacterListFragment]
 * [CharacterListFragment] starts with Navigation Component
 */
class CharacterListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)
        setSupportActionBar(findViewById(R.id.toolbar))
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree()) // Plant Tiber only for DEBUG
    }
}