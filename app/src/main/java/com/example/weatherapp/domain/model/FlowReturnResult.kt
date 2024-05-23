package com.example.weatherapp.domain.model


sealed class FlowReturnResult<out T> {
    class ErrorResult<T>(var errorMsg : Any , var data : T? = null) : FlowReturnResult<T>()

    class PositiveResult<T>(var data : T) : FlowReturnResult<T>()

    class LoadingRelsult<T> : FlowReturnResult<T>()

    class ValidationErrorResult<T>(var msg : Any) : FlowReturnResult<T>()

    object EmptyResult : FlowReturnResult<Nothing>()

    object PaymentOverdue : FlowReturnResult<Nothing>()

    object ServerMaintenance : FlowReturnResult<Nothing>()

    object SessionExpired : FlowReturnResult<Nothing>()

    object NewVersion : FlowReturnResult<Nothing>()

    object NetworkErrorResult : FlowReturnResult<Nothing>()

    object SocketTimeOutResult: FlowReturnResult<Nothing>()

    object IOExceptionResult: FlowReturnResult<Nothing>()

    override fun toString() : String {
        return getFlowReturnResultType(this)
    }
}

fun <T> FlowReturnResult<T>.compare(other : FlowReturnResult<T> , inTestMode : Boolean = false) : Boolean {
    return if (this.javaClass != other.javaClass) false
    else {
        showInLog(this , other , inTestMode)
        when (this) {
            is FlowReturnResult.PositiveResult        -> {
                this.data != null && (other as FlowReturnResult.PositiveResult).data != null && (this as FlowReturnResult.PositiveResult).data!! == other.data
            }

            is FlowReturnResult.ErrorResult           -> {
                this.data == (other as FlowReturnResult.ErrorResult).data && this.errorMsg == other.errorMsg
            }

            is FlowReturnResult.ValidationErrorResult -> {
                this.msg == (other as FlowReturnResult.ValidationErrorResult).msg
            }

            else                                  -> {
                true
            }
        }
    }
}

private fun <T> showInLog(`this` : FlowReturnResult<T> , that : FlowReturnResult<T> , inTestMode : Boolean) {
    if (inTestMode) {
        println("this : ${getFlowReturnResultType(`this`)}")
        println("that : ${getFlowReturnResultType(that)}")
    } else {
        //do nothing
    }
}


fun getFlowReturnResultType(returnResult : FlowReturnResult<*>) : String {
    return when (returnResult) {
        is FlowReturnResult.PositiveResult        -> {
            "PositiveResult(${returnResult.data ?: "no data content"})"
        }

        is FlowReturnResult.ErrorResult           -> {
            "ErrorResult(${returnResult.errorMsg},${returnResult.data ?: "no data content"})"
        }

        is FlowReturnResult.LoadingRelsult        -> {
            "LoadingResult"
        }

        is FlowReturnResult.EmptyResult           -> {
            "EmptyResult"
        }

        is FlowReturnResult.ValidationErrorResult -> {
            "ValidationErrorResult(${returnResult.msg})"
        }

        is FlowReturnResult.NewVersion            -> {
            "NewUpdate"
        }

        is FlowReturnResult.PaymentOverdue        -> {
            "MembershipExpired"
        }

        is FlowReturnResult.SessionExpired        -> {
            "SessionExpired"
        }

        else                                  -> {
            "SomethingWentWrong"
        }
    }
}