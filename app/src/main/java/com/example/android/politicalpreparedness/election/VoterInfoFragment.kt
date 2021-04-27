package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {

    private val args: VoterInfoFragmentArgs by navArgs()

    private val viewModel: VoterInfoViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, VoterInfoViewModelFactory(activity.application)).get(VoterInfoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add binding values
        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */
        val election = VoterInfoFragmentArgs.fromBundle(arguments!!).argElection

        viewModel.getElection(election.id)

        viewModel.getVoterInfo(args.argElection.division.country, args.argElection.id)

        //TODO: Handle loading of URLs
        viewModel.urlIntent.observe(viewLifecycleOwner, Observer {
           it?.let {

           loadUrlIntent(it)
           }
        })


        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        return binding.root

    }

    // Method to load URL intents
    private fun loadUrlIntent(uri: String?){
    if(!uri.isNullOrBlank()){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }
    }

}