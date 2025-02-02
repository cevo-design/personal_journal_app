package com.example.personaljouurnalapp.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.personaljouurnalapp.R
import com.example.personaljouurnalapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

  private lateinit var loginViewModel: LoginViewModel
  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val username = binding.username
    val password = binding.password
    val loginBtn = binding.loginButton
    val loading = binding.loading
    val signUpBtn = binding.createAcctBtn

    signUpBtn?.setOnClickListener {
      val intent = Intent(this, com.example.personaljouurnalapp.SignUp::class.java)
      startActivity(intent)
      finish()
    }

    loginBtn?.setOnClickListener {
      val intent = Intent(this, com.example.personaljouurnalapp.Dashboard::class.java)
      startActivity(intent)
      finish()
    }


    loginViewModel =
        ViewModelProvider(this, LoginViewModelFactory()).get(LoginViewModel::class.java)

    loginViewModel.loginFormState.observe(
        this@LoginActivity,
        Observer {
          val loginState = it ?: return@Observer

          // disable login button unless both username / password is valid

          if (loginState.usernameError != null) {
            username.error = getString(loginState.usernameError)
          }
          if (loginState.passwordError != null) {
            password.error = getString(loginState.passwordError)
          }
        })

    loginViewModel.loginResult.observe(
        this@LoginActivity,
        Observer {
          val loginResult = it ?: return@Observer

          loading.visibility = View.GONE
          if (loginResult.error != null) {
            showLoginFailed(loginResult.error)
          }
          if (loginResult.success != null) {
            updateUiWithUser(loginResult.success)
          }
          setResult(Activity.RESULT_OK)

          // Complete and destroy login activity once successful
          finish()
        })

    username.afterTextChanged {
      loginViewModel.loginDataChanged(username.text.toString(), password.text.toString())
    }

    password.apply {
      afterTextChanged {
        loginViewModel.loginDataChanged(username.text.toString(), password.text.toString())
      }

      setOnEditorActionListener { _, actionId, _ ->
        when (actionId) {
          EditorInfo.IME_ACTION_DONE ->
              loginViewModel.login(username.text.toString(), password.text.toString())
        }
        false
      }

          }
  }

  private fun updateUiWithUser(model: LoggedInUserView) {
    val welcome = getString(R.string.welcome)
    val displayName = model.displayName
    // TODO : initiate successful logged in experience
    Toast.makeText(applicationContext, "$welcome $displayName", Toast.LENGTH_LONG).show()
  }

  private fun showLoginFailed(@StringRes errorString: Int) {
    Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
  }
}

/** Extension function to simplify setting an afterTextChanged action to EditText components. */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
  this.addTextChangedListener(
      object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
          afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
      })
}
