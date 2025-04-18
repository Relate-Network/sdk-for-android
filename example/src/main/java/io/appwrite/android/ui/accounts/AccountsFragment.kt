package io.appwrite.android.ui.accounts

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.appwrite.android.R
import io.appwrite.android.databinding.FragmentAccountBinding


class AccountsFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val viewModel: AccountsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_account,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        binding.login.setOnClickListener{
            viewModel.onLogin(
                binding.email.text.toString(),
                binding.password.text.toString(),
                context
                    ?.getSharedPreferences("example", Context.MODE_PRIVATE)
                    ?.getString("fcmToken", null) ?: ""
            )
        }
        binding.signup.setOnClickListener{
            viewModel.onSignup(
                binding.email.text.toString(),
                binding.password.text.toString(),
                binding.name.text.toString()
            )
        }
        binding.getUser.setOnClickListener{
            viewModel.getUser()
        }
        binding.oAuth.setOnClickListener{
            viewModel.oAuthLogin(activity as ComponentActivity)
        }
        binding.logout.setOnClickListener{
            viewModel.logout()
        }

        viewModel.error.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.response.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                binding.responseTV.setText(it)
            }
        }

        viewModel.target.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                context
                    ?.getSharedPreferences("example", Context.MODE_PRIVATE)
                    ?.edit()
                    ?.putString("targetId", it.id)
                    ?.apply()
            }
        }

        return binding.root
    }
}