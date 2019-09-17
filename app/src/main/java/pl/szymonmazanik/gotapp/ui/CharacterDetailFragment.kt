package pl.szymonmazanik.gotapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import pl.szymonmazanik.gotapp.R
import pl.szymonmazanik.gotapp.databinding.FragmentCharacterDetailBinding
import pl.szymonmazanik.gotapp.utils.extensions.getCharacterById
import pl.szymonmazanik.gotapp.utils.extensions.getViewModel
import timber.log.Timber


/**
 * A fragment representing a single Character detail screen.
 * This fragment is either contained in a [CharacterListFragment] in two-pane mode (on tablets)
 * or opened as [CharacterDetailFragment] on handsets.
 */
class CharacterDetailFragment : Fragment() {

    private val viewModel by lazy {
        activity?.run {
            getViewModel { CharacterViewModel() }
        } ?: throw Exception("Invalid Activity")
    }

    private val args: CharacterDetailFragmentArgs by navArgs()

    private var showError = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentCharacterDetailBinding>(
            inflater,
            R.layout.fragment_character_detail,
            container,
            false
        ).apply {
            this.lifecycleOwner = viewLifecycleOwner
            viewModel.characters.getCharacterById(args.characterId)
                ?.let { this.character = it } ?: showError()
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (showError) Snackbar.make(view!!, R.string.detail_error, Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun showError() {
        showError = true
        Timber.e("Character with id ${args.characterId} not bound")
    }
}