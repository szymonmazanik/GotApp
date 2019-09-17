package pl.szymonmazanik.gotapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.google.android.material.snackbar.Snackbar
import pl.szymonmazanik.gotapp.R
import pl.szymonmazanik.gotapp.databinding.FragmentCharacterListBinding
import pl.szymonmazanik.gotapp.databinding.FragmentCharacterListLandBinding
import pl.szymonmazanik.gotapp.model.entity.Character
import pl.szymonmazanik.gotapp.utils.EventObserver
import pl.szymonmazanik.gotapp.utils.extensions.getViewModel

/**
 * Fragment representing a list of [Character]s. This fragment
 * has different presentations for handset and tablet-size devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a [CharacterDetailFragment] representing item details.
 * On tablets, the fragment presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class CharacterListFragment : Fragment() {

    private val isTablet by lazy { context!!.resources.getBoolean(R.bool.isTablet) }

    private val viewModel by lazy {
        activity?.run {
            getViewModel { CharacterViewModel() }
        } ?: throw Exception("Invalid Activity")
    }

    private val adapter by lazy { CharacterListAdapter(viewModel) }

    /**
     * Binds different layout for different screen sizes
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when {
            isTablet -> {
                DataBindingUtil.inflate<FragmentCharacterListLandBinding>(
                    inflater,
                    R.layout.fragment_character_list_land, //Bind land layout
                    container,
                    false
                ).apply {
                    this.characterList.adapter = adapter
                    this.lifecycleOwner = viewLifecycleOwner
                }.root
            }
            else -> {
                DataBindingUtil.inflate<FragmentCharacterListBinding>(
                    inflater,
                    R.layout.fragment_character_list, //Bind handset layout
                    container,
                    false
                ).apply {
                    this.characterList.adapter = adapter
                    this.lifecycleOwner = viewLifecycleOwner
                }.root
            }
        }
    }

    /**
     * Setups fragment [LiveData]s
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigation()
        setupCharacterList()
        setupErrorSnackbar()
    }

    private fun setupNavigation() {
        viewModel.openDetailsEvent.observe(this, EventObserver {
            openCharacterDetails(it)
        })
    }

    private fun setupCharacterList() {
        viewModel.characters.observe(this, Observer {
            updateList(it)
        })
    }

    /**
     * Displays error [Snackbar] only if value isn't null
     */
    private fun setupErrorSnackbar() {
        viewModel.errorMessage.observe(this, EventObserver {
            it?.let { Snackbar.make(view!!, it, Snackbar.LENGTH_LONG).show() }
        })
    }

    /**
     * Opens details view in different way for tablet and for handset layout
     * For tablet we use navController from childFragmentManager
     * For handset we use [CharacterListFragmentDirections] to go to new fragment
     * @param id of [Character] that should be open
     */
    private fun openCharacterDetails(id: Long) {
        when {
            isTablet -> {
                val navHostFragment =
                    childFragmentManager.findFragmentById(R.id.character_detail_nav_container) as NavHostFragment
                navHostFragment.navController.setGraph(
                    R.navigation.nav_graph_land,
                    Bundle().apply { putLong("characterId", id) }
                )
            }
            else -> {
                val action = CharacterListFragmentDirections.actionCharactersToCharacterDetail()
                action.characterId = id
                findNavController().navigate(action)
            }
        }
    }

    /**
     * Update adapter
     * @param characters [PagedList] of [Character]s
     */
    private fun updateList(characters: PagedList<Character>) = adapter.submitList(characters)
}