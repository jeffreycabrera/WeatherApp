package com.example.weatherapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import io.reactivex.rxjava3.disposables.CompositeDisposable


abstract class BaseFragment : Fragment() {

    protected val compositeDisposable = CompositeDisposable()

    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            notFoundErrorLiveData.observe(viewLifecycleOwner, {
                showAlertDialog(getString(R.string.not_found_error_message))
            })

            errorWithMessageLiveData.observe(viewLifecycleOwner, { message ->
                showAlertDialog(message)
            })

            networkErrorLiveData.observe(viewLifecycleOwner, {
                showAlertDialog(getString(R.string.connection_error_message))
            })
        }
    }

    private fun showAlertDialog(
        message: String
    ) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
