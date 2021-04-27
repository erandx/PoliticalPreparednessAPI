package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import com.example.android.politicalpreparedness.network.models.Election

class ElectionsFragment: Fragment() {

    /** A way to delay viewModel creation until a lifecycle method is to use lazy. This requires
     * viewModel to not be referenced before onViewCreated(), which we do in this Fragment.
     */
    private val viewModel: ElectionsViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, ElectionsViewModelFactory(activity.application)).get(ElectionsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // DONE: Add binding values
        val binding : FragmentElectionBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_election, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        //Done: Link elections to voter info
        viewModel.navigateToUpcomingElection.observe(viewLifecycleOwner, Observer { election ->
            election?.let {
                this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it))
                viewModel.displayElectionDetailsComplete()
                Log.d(TAG, "${election?.id}, ${election?.name}, ${election?.division}")
            }
        })

        //Done: Initiate Election recycler + Followed recycler adapters
        val electionAdapter = ElectionListAdapter(ElectionListAdapter.ElectionClickListener { election ->
            viewModel.displayElectionDetails(election)
        })
        // HasStableIds will not cause any blinking in our UI
        electionAdapter.setHasStableIds(true)
        binding.recyclerUpcomingElections.adapter = electionAdapter

        val savedAdapter = ElectionListAdapter(ElectionListAdapter.ElectionClickListener { savedElection ->
        viewModel.displayElectionDetails(savedElection)
        })

        //HasStableIds will not cause any blinking in our UI
        savedAdapter.setHasStableIds(true)
        binding.recyclerSavedElections.adapter = savedAdapter

        //Done: Refresh adapters when fragment loads
        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer { elections ->
            elections?.apply {
                electionAdapter.submitList(elections)
            }
        })

        viewModel.followedElections.observe(viewLifecycleOwner, Observer { elections ->
            elections?.apply {
                savedAdapter.submitList(elections)
            }
        })

        return binding.root
    }

}
private const val TAG = "ElectionsFragment"