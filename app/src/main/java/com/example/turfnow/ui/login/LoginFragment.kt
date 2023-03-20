package com.example.turfnow.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.turfnow.BottomNavigationActivity
import com.example.turfnow.database.entity.User
import com.example.turfnow.databinding.FragmentLoginBinding
import com.example.turfnow.dependency.MyApplication
import com.example.turfnow.result.Response
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(MyApplication(requireContext()).appContainer)
    }
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            if(!binding.emailIdText.text.isNullOrBlank() && !binding.passwordText.text.isNullOrBlank()){
                binding.inputLayoutLoginEmailID.error = null
                binding.inputLayoutLoginPassword.error = null
                viewModel.viewModelScope.launch{
                    val data = viewModel.login(
                        binding.emailIdText.text.toString(),
                        binding.passwordText.text.toString()
                    )
                    onLogin(data)
                }
            }
            else{
                binding.inputLayoutLoginEmailID.error = "Please Enter Email-Id"
                binding.inputLayoutLoginPassword.error = "Please Enter password"
                binding.inputLayoutLoginPassword.errorIconDrawable = null
            }
        }
        binding.signupText.setOnClickListener {
            val action:NavDirections = LoginFragmentDirections.actionLoginFragment2ToSignupFragment2()
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun onLogin(data:Response){
        data.apply {
            when(this) {
                is Response.Success -> {
                    Toast.makeText(requireContext(), this.data.toString(), Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), BottomNavigationActivity::class.java)
                    intent.putExtra("user", (this.data as User).email_id)
                    startActivity(intent)
                }
                is Response.Error -> {
                    Snackbar.make(binding.root, this.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        }
    }
    }
