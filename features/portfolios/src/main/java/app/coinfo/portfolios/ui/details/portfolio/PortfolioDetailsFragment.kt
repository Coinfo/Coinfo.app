package app.coinfo.portfolios.ui.details.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.coinfo.portfolios.databinding.FragmentPortfolioDetailsBinding
import app.coinfo.portfolios.ui.adapter.PortfolioDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PortfolioDetailsFragment : Fragment() {

    private var _binding: FragmentPortfolioDetailsBinding? = null

    /** This property is only valid between onCreateView and onDestroyView. */
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: PortfolioDetailsAdapter

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null. This will be called between
     * {@link #onCreate(Bundle)} and {@link #onViewCreated(View, Bundle)}.
     * <p>A default View can be returned by calling {@link #Fragment(int)} in your
     * constructor. Otherwise, this method returns null.
     *
     * <p>It is recommended to <strong>only</strong> inflate the layout in this method and move
     * logic that operates on the returned View to {@link #onViewCreated(View, Bundle)}.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPortfolioDetailsBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewAssets.adapter = adapter
        adapter.loadAssets(arguments?.getLong(ARG_PORTFOLIO_ID)!!)
    }

    /**
     * Called when the view previously created by {@link #onCreateView} has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after {@link #onStop()} and before {@link #onDestroy()}.  It is called
     * <em>regardless</em> of whether {@link #onCreateView} returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val ARG_PORTFOLIO_ID = "portfolio_id"
    }
}
