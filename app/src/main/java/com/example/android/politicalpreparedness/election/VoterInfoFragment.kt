package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.network.models.Election

class VoterInfoFragment : Fragment() {

    private val args: VoterInfoFragmentArgs by navArgs()

    private lateinit var viewModel: VoterInfoViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val election = VoterInfoFragmentArgs.fromBundle(arguments!!).argElection

        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(this, VoterInfoViewModelFactory(activity.application, election)).get(VoterInfoViewModel::class.java)

        //Done: Add binding values
        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */

        binding.election = election

        //Done: Handle loading of URLs
        viewModel.urlIntent.observe(viewLifecycleOwner, Observer {
            it?.let {

                loadUrlIntent(it)
            }
        })


        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks
        viewModel.followedElection.observe(viewLifecycleOwner, Observer { isElectionFollowed ->
            isElectionFollowed?.let {
                if (isElectionFollowed) {
                    binding.followElectionButton.text = getString(R.string.follow_election)
                } else {
                    binding.followElectionButton.text = getString(R.string.unfollow_election)
                }
            }
        })

        setTitle(election.name)
        return binding.root
    }

    // Method to load URL intents
    private fun loadUrlIntent(uri: String?) {
        if (!uri.isNullOrBlank()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
    }

    fun Fragment.setTitle(title: String) {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.title = title
        }
    }

}