package com.ssu.micropower.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ssu.micropower.R
import com.ssu.micropower.databinding.FragmentAuthBinding
import com.ssu.micropower.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment() {

    private lateinit var _binding: FragmentAuthBinding

    private val viewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.apply {
            lifecycleOwner = this@AuthFragment

            viewModel.user.observe(viewLifecycleOwner) {
                activity
                    ?.findNavController(R.id.nav_host_fragment_container)
                    ?.navigate(R.id.action_authFragment_to_mainFragment)
            }

            viewModel.message.observe(viewLifecycleOwner) {
                isLoading = false
                Snackbar.make(container, it, Snackbar.LENGTH_LONG).show()
            }

            editLogin.addTextChangedListener(textValidator)
            editPassword.addTextChangedListener(textValidator)

            btnLogin.setOnClickListener {
                isLoading = true
                viewModel.auth(
                    editLogin.text.toString(),
                    editPassword.text.toString()
                )

                (activity as MainActivity).hideKeyboard()
            }

            isValid = isValid()
        }
        viewModel.auth()
    }

    private val textValidator = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            _binding.isValid = isValid()
        }
    }

    private fun isValid() =
        _binding.editLogin.text.isNotEmpty() && _binding.editPassword.text.isNotEmpty()
}