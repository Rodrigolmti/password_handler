package br.com.rodrigolmti.authentication.ui.pin

import br.com.rodrigolmti.core_android.base.BaseViewModel
import javax.inject.Inject

class PinViewModel @Inject constructor(override val viewState: PinViewState) :
    BaseViewModel<PinViewState, PinAction>() {

    override fun dispatchViewAction(viewAction: PinAction) {
    }
}