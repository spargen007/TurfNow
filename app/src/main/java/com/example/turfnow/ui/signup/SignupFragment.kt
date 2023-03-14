package com.example.turfnow.ui.signup
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.turfnow.database.AppDatabase
import com.example.turfnow.database.entity.User
import com.example.turfnow.databinding.FragmentSignupBinding
import com.example.turfnow.repository.UserRepositoryImpl
import com.example.turfnow.result.Response
import com.example.turfnow.validator.EmailValidator
import com.example.turfnow.validator.EmptyValidator
import com.example.turfnow.validator.PasswordValidator
import com.example.turfnow.validator.PhoneValidator
import com.example.turfnow.validator.base.BaseValidator
import com.example.turfnow.validator.base.ValidateResult
import com.google.android.material.snackbar.Snackbar

class SignupFragment : Fragment() {
//    lateinit var viewModel:SignUpViewModel
    private val viewModel: SignUpViewModel by viewModels {
      SignUpViewModelFactory(
          UserRepositoryImpl(AppDatabase.getDatabase(requireContext()).userDao())
        )
    }
    private var _binding:FragmentSignupBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentSignupBinding.inflate(inflater, container, false)

//        viewModel = ViewModelProvider(
//            findNavController().getViewModelStoreOwner(R.id.app_navigation),
//            SignUpViewModelFactory(UserRepositoryImpl(AppDatabase.getDatabase(requireContext()).userDao()))
//        )[SignUpViewModel::class.java]

        binding.signupViewModel=viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var emailValidations = ValidateResult()
        var usernameEmptyValidation = ValidateResult()
        var phoneNumberValidation = ValidateResult()
        var passwordValidations = ValidateResult()

        binding.signupButton.isEnabled=false
        fun signUpButtonStatus(){
            binding.signupButton.isEnabled = emailValidations.isSuccess && usernameEmptyValidation.isSuccess && phoneNumberValidation.isSuccess && passwordValidations.isSuccess
        }

        binding.emailIdText.addTextChangedListener(){
            val email = binding.emailIdText.text.toString()
             emailValidations =
                BaseValidator.validate(EmptyValidator(email), EmailValidator(email))
            binding.inputLayoutEmailId.error =
                if (!emailValidations.isSuccess) getString(emailValidations.message) else null
            signUpButtonStatus()
        }

        binding.nameText.addTextChangedListener {
            val username = binding.nameText.text.toString()
            usernameEmptyValidation = EmptyValidator(username).validate()
            binding.inputLayoutName.error =
                if (!usernameEmptyValidation.isSuccess) getString(usernameEmptyValidation.message) else null
            signUpButtonStatus()
        }

        binding.phoneNumberText.addTextChangedListener {
            val phone = binding.phoneNumberText.text.toString()
             phoneNumberValidation =
                BaseValidator.validate(EmptyValidator(phone), PhoneValidator(phone))
            binding.inputLayoutPhoneNumber.error =
                if (!phoneNumberValidation.isSuccess) getString(phoneNumberValidation.message) else null
            signUpButtonStatus()
        }

        binding.passwordText.addTextChangedListener {
            val password = binding.passwordText.text.toString()
            passwordValidations =
                BaseValidator.validate(EmptyValidator(password), PasswordValidator(password))
            binding.inputLayoutPassword.error =
                if (!passwordValidations.isSuccess) getString(passwordValidations.message) else null
            binding.inputLayoutPassword.errorIconDrawable = null
            signUpButtonStatus()
        }
        binding.signupButton.setOnClickListener {
            binding.apply {
                viewModel.register(
                    User(
                        id = 0,
                        name = this.nameText.text.toString(),
                        email_id = this.emailIdText.text.toString(),
                        phone_number = this.phoneNumberText.text.toString(),
                        password = this.passwordText.text.toString()
                    )
                )
            }
        }

        viewModel.insertUsersDataStatus.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> {
                    binding.signupButton.visibility = View.GONE
                    binding.signupProgressBar.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    binding.signupProgressBar.visibility = View.GONE
                    binding.signupButton.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Sign-UP successful/nLogin Again To Continue", Toast.LENGTH_SHORT)
                        .show()
                    val action1: NavDirections =
                        SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                    findNavController().navigate(action1)
                }
                is Response.Error -> {
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
                    binding.signupProgressBar.visibility = View.GONE
                    binding.signupButton.visibility = View.VISIBLE
                }

                else -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}