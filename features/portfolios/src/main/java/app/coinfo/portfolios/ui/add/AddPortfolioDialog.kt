package app.coinfo.portfolios.ui.add

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import app.coinfo.portfolios.R
import app.coinfo.portfolios.databinding.DialogAddPortfolioBinding
import app.coinfo.portfolios.ext.action
import app.coinfo.portfolios.ext.shortSnackBar
import app.coinfo.portfolios.repo.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddPortfolioDialog : DialogFragment() {

    private var _binding: DialogAddPortfolioBinding? = null

    @Inject
    lateinit var repository: Repository

    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val isGranted = permissions.entries.all { permission -> permission.value == true }
            if (isGranted) {
                // IMPROVEMENT: Provide specific mime type "text/csv" instead of "text/*"
                // ISSUE: https://github.com/ChamichApps/Coinfo/issues/6
                fileBrowserLauncher.launch(arrayOf(MIME_TYPE_CSV))
            } else {
                Log.w(TAG, "Notify user about consequences of not granting permission")
                view?.shortSnackBar(getString(R.string.permission_read_external_storage)) {
                    action(getString(R.string.grant_permissions)) { openApplicationPermissionSettings() }
                }
            }
        }

    /**
     * An {@link ActivityResultContract} to prompt the user to open a document, receiving its
     * contents as a {@code file:/http:/content:} {@link Uri}.
     * <p>
     * The input is the mime types to filter by, e.g. {@code image}.
     * <p>
     * This can be extended to override {@link #createIntent} if you wish to pass additional
     * extras to the Intent created by {@code super.createIntent()}.
     */
    private val fileBrowserLauncher = registerForActivityResult(object : ActivityResultContracts.OpenDocument() {
        override fun createIntent(context: Context, input: Array<out String>): Intent {
            return super.createIntent(context, input).apply { addCategory(Intent.CATEGORY_OPENABLE) }
        }
    }) { uri -> readCryptoComAppCsv(uri) }

    /** This property is only valid between onCreateView and onDestroyView. */
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Coinfo_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddPortfolioBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { dismissAllowingStateLoss() }
        binding.cryptoComApp.setOnClickListener {
            if (isReadExternalStoragePermissionGranted()) {
                // IMPROVEMENT: Provide specific mime type "text/csv" instead of "text/*"
                // ISSUE: https://github.com/ChamichApps/Coinfo/issues/6
                fileBrowserLauncher.launch(arrayOf(MIME_TYPE_CSV))
            } else {
                Log.w(TAG, "Read External Storage permission is not granted. Requesting user to grant permission.")
                permissionRequestLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        }
    }

    /**
     * Called when the view previously created by {@link #onCreateView} has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after {@link #onStop()} and before {@link #onDestroy()}.  It is called
     * <em>regardless</em> of whether {@link #onCreateView} returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun isReadExternalStoragePermissionGranted() = checkSelfPermission(
        requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PermissionChecker.PERMISSION_GRANTED

    private fun openApplicationPermissionSettings() {
        startActivity(
            Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts(URI_SCHEME, APPLICATION_ID, null)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

    private fun readCryptoComAppCsv(uri: Uri) {
        val stream = requireContext().contentResolver.openInputStream(uri)
        lifecycleScope.launch { repository.readCryptoComAppCsv(getFilename(uri), stream) }
    }

    private fun getFilename(uri: Uri) = requireContext().contentResolver.query(uri, null, null, null, null)?.use {
        it.moveToFirst()
        return@use try {
            it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Unable to get filename")
            null
        }
    }

    companion object {
        private const val TAG = "PORT/AddPortfolioDialog"

        private const val MIME_TYPE_CSV = "text/*"
        private const val APPLICATION_ID = "app.coinfo"
        private const val URI_SCHEME = "package"
    }
}
