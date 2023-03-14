package com.example.turfnow.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.turfnow.BottomNavigationActivity
import com.example.turfnow.database.AppDatabase
import com.example.turfnow.database.entity.User
import com.example.turfnow.databinding.FragmentLoginBinding
import com.example.turfnow.repository.UserRepositoryImpl
import com.example.turfnow.result.Response
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            UserRepositoryImpl(AppDatabase.getDatabase(requireContext()).userDao())
        )
    }
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainActivity = requireActivity() as BottomNavigationActivity
        mainActivity.setBottomNavigationViewVisibility(false)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            if(!binding.emailIdText.text.isNullOrBlank() && !binding.passwordText.text.isNullOrBlank()){
                binding.inputLayoutLoginEmailID.error = null
                binding.inputLayoutLoginPassword.error = null
                viewModel.login(binding.emailIdText.text.toString(),binding.passwordText.text.toString())
                onLogin()
            }
            else{
                binding.inputLayoutLoginEmailID.error = "Please Enter Email-Id"
                binding.inputLayoutLoginPassword.error = "Please Enter password"
                binding.inputLayoutLoginPassword.errorIconDrawable = null
            }
        }
        binding.signupText.setOnClickListener {
            val action:NavDirections = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun onLogin(){
        viewModel.loginUsersDataStatus.observe(viewLifecycleOwner){
            when(it){
                is Response.Loading -> {
                    binding.loginBtn.visibility = View.GONE
                    binding.loginProgressBar.visibility = View.VISIBLE
                }
                is Response.Success ->{
                    binding.loginBtn.visibility = View.VISIBLE
                    binding.loginProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(),it.data.toString(), Toast.LENGTH_SHORT).show()
                    val action:NavDirections = LoginFragmentDirections.actionLoginFragmentToHomeFragment(it.data as User)
                    findNavController().navigate(action)

                }
                is Response.Error -> {
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
                    binding.loginProgressBar.visibility = View.GONE
                    binding.loginBtn.visibility = View.VISIBLE
                }
                else -> {

                }
            }
        }
    }
    }
